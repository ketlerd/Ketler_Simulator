head	equ	$C000
headS	equ	$CB02
tail	equ	$C008
tailS	equ	$CB00
start	equ	$C001
end	equ	$C000    
posH	equ	$CB03
length	equ	$CB04

	ORG	$FF67

	PSHA	
	PSHB
	LDAA	length
	BEQ	inLoop

retChar	LDX	headS
	LDAA	$0,X
	INX
	STX	headS
	DEC	length

checkH	LDAB	posH
	SUBB	#$8
	BEQ	resetH
	PULA
	PULB
	RTS

inLoop	WAI
	BRA	retChar

resetH	LDX	#$C000
	STX	headS	
	PULA
	PULB
	RTS	
