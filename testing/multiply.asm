* Program to multiply two numbers
* David Ketler 3394947

count	equ		$20
result	equ		$21
b	equ		$22
c	equ		$23
	ORG		$0000

	LDAA 	#$05 (operand)
	STAA	c
	LDAB 	#$06 (operand)
	STAB	count
	LDAA	#$0
	STAA	result

loop	LDAA 	result
	ADDA	c	
	STAA 	result
	LDAA	count
	CMPA	#$1
	DEC		count
	BNE		loop

	LDAA	result	(load ACCA with the final result)
	WAI
