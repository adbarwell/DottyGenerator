#!/bin/bash

for f in SH PingCrash PingPongCrash SimpleBroadcastAll SimpleConsensus SimpleBroadcast NoPingCrash NoPingPongCrash NoSimpleBroadcastAll NoSimpleConsensus NoSimpleBroadcast; do
	echo $f
	echo ""
	for i in $(seq 1 10); do
		dottygen --output output/src/main/scala/ protocls/$f.nuscr $f
	 	echo ""
	done
done

# SH 
