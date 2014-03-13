getchar	EQU	$FF67
array	EQU	$D2

	org 	$0000
	
	LDX 	#$D2
	
start	LDAB	#$1
	STX	$99
	JSR	getchar
	LDX	$99

	TAB	
	SUBA	#$1
	BEQ	up

	TBA
	SUBA	#$2
	BEQ	down
	
	TBA
	SUBA	#$3
	BEQ	left	
	
	TBA
	SUBA	#$4
	BEQ	right

right	INX
	LDAB	#$1
	STAB	$00,X
	BRA	start

left	DEX
	LDAB	#$1
	STAB	$00,X
	BRA	start

up	LDAA	#$31
	DEX
	DECA
	BEQ	up
	LDAB	#$1
	STAB	$00,X
	BRA	start

down	LDAA	#$31
	INX
	DECA
	BEQ	down
	LDAB	#$1
	STAB	$00,X
	BRA	start

	WAI

