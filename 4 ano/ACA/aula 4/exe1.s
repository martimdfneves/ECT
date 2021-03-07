		.data
	
values:		.word 4, 3, 1, 2, 8, 5, 7, 6, 9, 10		;int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
numElems:	.word 10					;uint numElems = 10;

		.text
		.global main

main:		
		addi r1, r0, numElems				;r1 = add(numElems)
		nop						; Solve data hazzard
		nop						; 
		lw r1, 0(r1)					;r1 = val(numElems)
		
		nop						; Solve data hazzard
		nop						;  
		subi r2, r1, 1					;r2 = val(numElems - 1)
			
		add r3, r0, r0					;r3 = val(i) = 0
								
		addi r6, r0, values				;r6 = add(values[0]) = add(values)

								;int i, j;
								;int temp;

loop1:		slt r10, r3, r2					;for (i = 0; i < numElems - 1 ; i++) 
		nop
		nop
		nop
		beqz r10, endLoop1				;{	
								;
		addi r4, r3, 1					; 	j = i + 1

loop2:		slt r10, r4, r1					;	for (j = i+1; j < numElems; j++) 
		beqz r10, endLoop2				;	{
								;
		 						;		
		slli r8, r4, 2					;		r8 = add(values[j])
		addi r8, r0, values				;
								;
		lw r7, 0(r6)					;		r7 = val(values[i])
		lw r9, 0(r8)					;		r9 = val(values[j])
								;
if:		sgt r10, r9, r7					;		if (values[j] > values[i]) 
		beqz r10, endIf					;		{
								;
		add r5, r0, r7					;			temp = values[i]
								;
		add r7, r0, r9					;			values[i] = values[j]
		sw 0(r6), r7 					;			save value[i] on memory
								;
		add r9, r0, r5					;			values[j] = temp
		sw 0(r8), r9 					;			save value[j] on memory
								;
endIf:								;		}
								;
		addi r4, r4, 1					;		j++
		j loop2						;
endLoop2:							;	}	
								;
								;		r6 = add(values[i])
		addi r6, r6, 4					;		i++
		j loop1						;
								;
endLoop1:							;}


		trap 0