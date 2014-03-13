* Program to test BHI and BRA
* David Ketler 3394947
	org $0000

br2	LDAA #$01
	LDAB #$04
	cba
	bhi  br

	LDAA #$01
	LDAA #$01
	LDAA #$01
	LDAA #$01
br	LDAA #$07
	bra  br2

	WAI
	
