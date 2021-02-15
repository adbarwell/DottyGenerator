// EFSM.ts

// ======
// States
// ======

export enum SendState {
    S13 = 'S13', S17 = 'S17', S18 = 'S18', S15 = 'S15', S19 = 'S19',
};

export enum ReceiveState {
    S16 = 'S16', S11 = 'S11', S14 = 'S14',
};

export enum TerminalState {
    S12 = 'S12',
};

export type State = ReceiveState | SendState | TerminalState;

// ===========
// Type Guards
// ===========

export function isReceiveState(state: State): state is ReceiveState {
    return (Object.values(ReceiveState) as Array<State>).includes(state)
}

export function isSendState(state: State): state is SendState {
    return (Object.values(SendState) as Array<State>).includes(state)
}

export function isTerminalState(state: State): state is TerminalState {
    return (Object.values(TerminalState) as Array<State>).includes(state)
}