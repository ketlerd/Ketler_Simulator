* 6800 ASM Bubblesort
* Not the most optimized code, but it works
* David Ketler
* 3394947
* k, so it's not working, likely the assembly is wrong :(

i	equ	$F0
j	equ	$F1

	ORG 	$0000
	
	LDAA 	#$5
	STAA	i	
	LDAB 	#$0
	STAB	j
	LDX  	#$F4

	LDAA	#$4
	STAA	0,X
	LDAA	#$C
	STAA	1,X
	LDAA	#$8
	STAA	2,X
	LDAA	#$1
	STAA	3,X
	LDAA	#$B
	STAA	4,X

* Outer sort loop
loop1	LDAA 	#$F4
	STAA	j	
	LDAA	#$0
	CMPA	i
	BEQ	end

* Inner sort loop
loop2	LDAA	j
	LDAB	j+1
	CBA
	BLS	swap

cont	INC	j
	LDAA	#$04
	CMPA	#j
	DEC	i	(dec outer loop)
	BEQ	loop1
	BRA	loop2
		
swap	LDAA	j
	INC	j
	LDAB	j
	STAB	j
	DEC	j
	STAA	j
	BRA	cont
	
end	WAI

	
