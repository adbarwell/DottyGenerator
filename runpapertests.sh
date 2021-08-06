#!/bin/bash

for f in TwoBuyer Calc Ticket TravelAgency Fib Negotiate OnlineWallet Http Battleships Bank; do
	echo $f
	echo ""
	for i in $(seq 1 10); do
		dottygen --output output/src/main/scala/ protocls/$f.scr $f
	 	echo ""
	done
done

# SH 
