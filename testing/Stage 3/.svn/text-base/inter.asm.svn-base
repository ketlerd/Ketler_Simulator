input	EQU	#$FF89
irq	EQU	$FF90
tail	EQU	$C000
offset 	EQU	$C001
tailS	EQU	$CB00
posT	EQU	$CB01
length	EQU	$CB04

	ORG	$FF3A
	PSHA
	PSHB

start	LDAA	#$7
	LDAB	irq

	CBA
	BEQ	keypad

keypad	LDX	tailS
	LDAA	input
	STAA	$00,X

inc	INX
	INC	length
	STX	tailS

checkT	LDAB	posT
	SUBB	#$8
	BEQ	resetT
	PULA
	PULB
	RTS

resetT	LDX	#$C000
	STX	tailS
	PULA
	PULB
	RTS	
