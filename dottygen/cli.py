from argparse import ArgumentParser
import typing
import time

from benchmark.apigeneration.counter import Counter
from benchmark.codeline.line_counter import LineCounter
from dottygen.automata import efsm, parser as automata_parser
from dottygen.generator import output_generator
from dottygen.utils import logger, scribble, type_declaration_parser, role_parser
from dottygen.generator import DottyGenerator
from dottygen.generator.merger import Merger
from dottygen.generator.err_handle_merger import ErrorDetectMerger
from dottygen.generator.channel_generator import CaseClassGenerator, ChannelGenerator
from dottygen.generator.file_writer import FileReader, RecurseTypeGenerator
from dottygen.generator.output_generator import OutputGenerator

def parse_arguments(args: typing.List[str]) -> typing.Dict:
    """Prepare command line argument parser and return the parsed arguments
    from the specified 'args'."""

    parser = ArgumentParser()

    parser.add_argument('filename',
                        type=str, help='Path to Scribble protocol')

    parser.add_argument('protocol',
                        nargs='+',
                        type=str, help='Name of protocol')

    parser.add_argument('--output',
                         type=str, help='Output directory for generation', default='/home/dev/effpi_sandbox/src/main/scala')

    parser.add_argument('--single', help='output as a single file', action='store_true')

    parser.add_argument('--error', help='detect error', action='store_true')

    parser.add_argument('--unop', help='unoptimised merging', action='store_true')

    parser.add_argument('--async', help='asynchronous communication', action='store_true')

    parser.add_argument('--batch', help='multiple protocols, one program', action='store_true')

    parser.add_argument("--website", nargs='*', default=None)

    parsed_args = parser.parse_args(args)
    return vars(parsed_args)


def main(args: typing.List[str]) -> int:
    """Main entry point, return exit code."""

    parsed_args = parse_arguments(args)

    protocol = parsed_args['protocol']
    output_folder = parsed_args['output']
    scribble_file = parsed_args['filename']
    sing_file = not parsed_args['single']
    err_detect = parsed_args['error']
    website_roles = parsed_args['website']
    asynchronous = parsed_args['async']
    unop = parsed_args['unop']

    if not parsed_args['batch']:
        return generate(sing_file, output_folder, protocol[0], scribble_file, website_roles, err_detect, unop, asynchronous)
    else:
        return batch_gen(sing_file, output_folder, protocol, scribble_file, website_roles, err_detect, unop, asynchronous)

def getEFSMFromFile(counter, scribble_file, protocol, role):
    try:
        message = f'Role {role} : Getting protocol from {scribble_file}'
        with type_declaration_parser.parse(scribble_file) as custom_types:
            start_time = time.time()
            exit_code, output = scribble.get_graph(scribble_file, protocol, role)
            end_time = time.time()
            counter.add_nuscr_time(end_time - start_time)
            if exit_code != 0:
                logger.FAIL(message)
                logger.ERROR(output)
                return None
            logger.SUCCESS(message)
            return output
    except (OSError, ValueError) as error:
        logger.ERROR(error)
        return None

def parseEFSM(counter, efsms, role, output):
    phase = f'Role {role} : Parse endpoint IR from Scribble output'
    try:
        start_time = time.time()
        efsm = automata_parser.from_data(output)
        end_time = time.time()
        counter.add_efsm_time(end_time - start_time)
        efsms[role] = efsm
        logger.SUCCESS(phase)
        return efsms
    except ValueError as error:
        logger.FAIL(phase)
        logger.ERROR(error)
        return None

# What does this even do?
def merger(counter, efsms, unop, err_detect):
    start_time = time.time()
    merger = Merger(efsms, unop)
    if err_detect:
        merger = ErrorDetectMerger(efsms, unop)
    channel_map = merger.merge()
    end_time = time.time()
    counter.add_merge_time(end_time-start_time)
    return channel_map

def generation(counter,
               recurse_generator,
               output_generator,
               host_map,
               channel_list,
               labels,
               protocol,
               efsms,
               roles,
               role):
    counter.set_role(role)
    efsm = efsms[role]
    phase = f'Role {role} : Generating Type and Function from EFSM'
    try:
        other_roles = roles - set(role)
        generator = DottyGenerator(efsm=efsm, protocol=protocol, role=role, other_roles=other_roles,
                                    recurse_generator=recurse_generator, isWebsite=False, host=host_map.get(role, ""))
        type, function, label, channels = generator.build(counter)
        output_generator.add_type(role, type)
        output_generator.add_function(role, function)
        channel_list.append((role, channels))
        labels = labels.union(label)
        logger.SUCCESS(phase)
        return labels
    except (OSError, ValueError) as error:
        logger.FAIL(phase)
        logger.ERROR(error)
        return None

def flatten(xss):
    return [x for xs in xss for x in xs]

def writeOutput(sing_file, output_folder, output_generator, protocols, labels, channel_lists, channel_maps, all_roles):
    phase = f'Writing functions and types into file'
    try:
        # line_counter.add_case_class(labels)
        case_classes = CaseClassGenerator(labels).generate()
        channels_assign = ChannelGenerator(flatten(channel_lists), flatten(channel_maps), False).generate()
        if not sing_file:
            output_generator.single_output(output_folder, case_classes, channels_assign, ''.join(protocols))
        else:
            output_generator.batch_output(output_folder, case_classes, channels_assign, ''.join(protocols), flatten(all_roles), False, {})
        logger.SUCCESS(phase)
    except (OSError, ValueError) as error:
        logger.FAIL(phase)
        logger.ERROR(error)
        return 1
    return 0

def batch_gen(sing_file, output_folder, protocols, scribble_file, website, err_detect, unop, asynchronous, counter=Counter(), line_counter=LineCounter()):
    labels = set()
    channel_lists = [[] for p in protocols]
    channel_maps = [[] for p in protocols]
    efsmss = [{} for p in protocols]
    all_roles = [role_parser.parse(scribble_file, protocol) for protocol in protocols]

    recurse_generator = RecurseTypeGenerator()
    recurse_generator.setup()

    output_generator = OutputGenerator()
    if not website is None:
        logger.ERROR("Website generation not supported in batch mode.")
        return 1

    for i in range(len(protocols)):
        print ("protocol",protocols[i], all_roles[i])
        for role in all_roles[i]:
            counter.set_role(role)
            r = getEFSMFromFile(counter, scribble_file, protocols[i], role)
            if r is None:
                return 1
            r = parseEFSM(counter, efsmss[i], role, r)
            if r is None:
                return 1
        channel_maps[i] = merger(counter, efsmss[i], unop, err_detect)
        for role in all_roles[i]:
            r = generation(counter, recurse_generator, output_generator, {}, channel_lists[i], labels, protocols[i], efsmss[i], all_roles[i], role)
            if r is None:
                return 1
            else:
                labels = r
    writeOutput(sing_file, output_folder, output_generator, protocols, labels, channel_lists, channel_maps, all_roles)

           

def generate(sing_file, output_folder, protocol, scribble_file, website, err_detect, unop, asynchronous, counter=Counter(), line_counter=LineCounter()):
    labels = set()
    channel_list = []
    efsms = {}
    all_roles = role_parser.parse(scribble_file, protocol)
    hosts =["8080", "8888", "3000", "5000"]

    recurse_generator = RecurseTypeGenerator()
    recurse_generator.setup()

    output_generator = OutputGenerator()
    isWebsite = not website is None
    if isWebsite:
        assert (all(role in all_roles for role in website))
        if len(website) == 0:
            website = all_roles
    else:
        website = []

    host_map = {}
    i = 0
    for role in website:
        host_map[role] = hosts[i]
        i += 1

    assert(not isWebsite or sing_file)

    for role in all_roles:
        counter.set_role(role)
        try:
            message = f'Role {role} : Getting protocol from {scribble_file}'
            with type_declaration_parser.parse(scribble_file) as custom_types:
                start_time = time.time()
                exit_code, output = scribble.get_graph(scribble_file, protocol, role)
                end_time = time.time()
                counter.add_nuscr_time(end_time - start_time)
                if exit_code != 0:
                    logger.FAIL(message)
                    logger.ERROR(output)
                    return exit_code
                logger.SUCCESS(message)
        except (OSError, ValueError) as error:
            logger.ERROR(error)
            return 1

        phase = f'Role {role} : Parse endpoint IR from Scribble output'
        try:
            start_time = time.time()
            efsm = automata_parser.from_data(output)
            end_time = time.time()
            counter.add_efsm_time(end_time - start_time)
            efsms[role] = efsm
            logger.SUCCESS(phase)
        except ValueError as error:
            logger.FAIL(phase)
            logger.ERROR(error)
            return 1

    start_time = time.time()
    merger = Merger(efsms, unop)
    if err_detect:
        merger = ErrorDetectMerger(efsms, unop)
    channel_map = merger.merge()
    end_time = time.time()
    counter.add_merge_time(end_time-start_time)

    for role in all_roles:
        counter.set_role(role)
        efsm = efsms[role]
        phase = f'Role {role} : Generating Type and Function from EFSM'
        try:
            other_roles = all_roles - set(role)
            generator = DottyGenerator(efsm=efsm, protocol=protocol, role=role, other_roles=other_roles,
                                       recurse_generator=recurse_generator, isWebsite=(isWebsite and role in website), host=host_map.get(role, ""))
            type, function, label, channels = generator.build(counter)
            output_generator.add_type(role, type)
            output_generator.add_function(role, function)
            channel_list.append((role, channels))
            labels = labels.union(label)
            logger.SUCCESS(phase)
        except (OSError, ValueError) as error:
            logger.FAIL(phase)
            logger.ERROR(error)
            return 1
    phase = f'Writing functions and types into file'
    print("No. Types:", len(output_generator._effpi_types))
    print("No. Labels:", len(labels))
    try:
        # print(counter.get_merge_time())
        # print(counter.get_class_time())
        # print(counter.get_efsm_time())
        # print(counter.get_type_time())
        # print(counter.get_function_time())
        print("get_nuscr_time",counter.get_nuscr_time())
        print("w/ nuScr: ",counter.get_merge_time() + counter.get_class_time() + counter.get_efsm_time() + counter.get_type_time() + counter.get_function_time() + counter.get_nuscr_time())
        print("w/o nuScr: ",counter.get_merge_time() + counter.get_class_time() + counter.get_efsm_time() + counter.get_type_time() + counter.get_function_time())
        line_counter.add_case_class(labels)
        case_classes = CaseClassGenerator(labels).generate()
        channels_assign = ChannelGenerator(channel_list, channel_map, asynchronous or err_detect).generate()
        if not sing_file:
            output_generator.single_output(output_folder, case_classes, channels_assign, protocol)
        else:
            output_generator.batch_output(output_folder, case_classes, channels_assign, protocol, all_roles, isWebsite, host_map)
        logger.SUCCESS(phase)
    except (OSError, ValueError) as error:
        logger.FAIL(phase)
        logger.ERROR(error)
        return 1
    return 0
