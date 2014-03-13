* small program to test set and clear carry
* and related branches
* David Ketler 3394947
	org $0000
c1	SEC
	BCS c2






c2	CLC
	BCC c1

	WAI	
