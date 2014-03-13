	ORG	$0000
	LDAA	#$4	(the exponent)
	STAA	$F0

	LDAA	#$1
	STAA	$21

loop	ASL	$21
	DEC	$F0
	LDAA	$F0
	CMPA	#$0
	BN	loop

	WAI