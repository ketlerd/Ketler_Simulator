	ORG 	$0000	(Test AND OR EOR Extended)
	ADDA	#$20
	ADDB	#$30
	STAA	$0150
	ANDB	$0150
	STAB	$0151
	ANDA	$0100	
	ADDA	#$10
	ADDB	#$40
	ORAA	$0150
	ORAB	$0150
	EORA	$0151
	EORB	$0151