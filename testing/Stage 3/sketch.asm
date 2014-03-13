key	EQU	$FF89
size	EQU	$FF88
x	EQU	$FF87
getchar	EQU	$FF67

	ORG	$0000

	LDX	#$C000
	LDAA	#$80
	STAA	x
	LDAA	#$4
	STAA	size

start	JSR	getchar
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

up	LDAA	#$0
	STAA	key
	LDAA	size
	DECA
uplp	INX
	DECA
	BNE	uplp
	LDAA	x
	ORA	$00,X
	STAA	$00,X
	BRA	start

down	LDAA	#$0
	STAA	key
	LDAA	size
	DECA
dwnlp	DEX
	DECA
	BNE	dwnlp
	LDAA	x
	ORA	$00,X
	STAA	$00,X
	BRA	start

left	LDAA	#$0
	STAA	key
	LSL	x
	LDAA	x
	ORA	$00,X
	STAA	$00,X
	BRA	start

right	LDAA	#$0
	STAA	key
	LSR	x
	LDAA	x
	ORA	$00,X
	STAA	$00,X
	BRA	start

