* Program to test BLS and BRA
* David Ketler 3394747

	org $0000

br2	LDAA #$04
	LDAB #$04
	cba
	bls  br

	LDAA #$01
	LDAA #$01
	LDAA #$01
	LDAA #$01
br	LDAA #$07
	bra  br2

	WAI

