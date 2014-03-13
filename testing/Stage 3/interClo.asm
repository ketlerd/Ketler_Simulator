input	EQU	#$FF89
irq	EQU	$FF90
tail	EQU	$C000
offset 	EQU	$C001
tailS	EQU	$CB00
posT	EQU	$CB01
length	EQU	$CB04
store	EQU	$DD06

	ORG	$FF3A
	
	PSHA
	LDAA	input
	STAA	store
	PULA
	RTS
	
