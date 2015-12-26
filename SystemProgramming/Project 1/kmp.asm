;System Programming - Project 1
;Group: G15
;Duran Eren				
;Cagatay Erdiz			
;Ahmet Gozuberk
;13.10.2015

extern printf			;to call printf

segment .data            	;initialized data definitions
msg db "Prefix Table:",10,0	;screen output
len dd 0			;len variable
i dd 1				;i index
j dd 1				;j index
intf db   "%d ", 0		;output format of prefix table
lps times 200 db 0		;static slot for prefix table
counter dd 0			;counter for printing prefix table
N dd 0				;length of text
M dd 0				;length of searched string

segment .text             ;the start of a group of instructions to be assembled
global kmp             	  ;entry label for the program

kmp:
	push ebp
    	mov ebp,esp
    
    	mov edx,[ebp+8]	   		;char *text
    	mov ebx, [ebp+12]	   	;char *searched_string
    	xor eax,eax			;set eax to zero
    
strlenM:
	mov cl,[ebx+eax]		;cl is used for char comparison(1 byte)
	cmp cl,0x0			;check for end of string
	je cont1			
	inc eax				;increment counter
	jmp strlenM
cont1:
	mov dword[M], eax		;M value is stored
	xor eax,eax			;set eax to zero
strlenN:
	mov cl,[edx+eax]		;cl is used for char comparison(1 byte)
	cmp cl,0x0			;check for end of string
	je cont2
	inc eax				;increment counter
	jmp strlenN
cont2:
	mov dword [N], eax		;N value is stored
	mov dword[lps], dword 0		;first value of prefix table is always zero	

loop1:	mov eax, dword[i]		; i->eax
	mov edx, dword[M]		; M->edx
	cmp eax,edx			; check i<M
	je end1	
	mov edx, dword[len]		; len->edx
	mov cl, [ebx+eax]		; cl used for char comparison
	mov ch, [ebx+edx]		; ch used for char comparison
	cmp cl,ch			; pat[i] == pat[len]
	jne else1
	inc edx				;increment edx
	mov dword[lps+4*eax], edx	;beacuse of 4 bytes of cells, eax multiplied by 4
	mov dword[len], edx		;store new value of len
	inc eax				;increment eax
	mov dword[i], eax		;store new value of i 
	jmp loop1
else1:
	cmp dword[len], dword 0		;len != 0
	jne else2
	mov [lps+4*eax], dword 0	;lps[i] = 0;
	inc eax				;increment eax
	mov dword[i], eax		;store new value of i
	jmp loop1
else2:
	mov ecx, dword[lps+4*edx-4]	;ecx = lps[len-1];
	mov dword[len], ecx		;store new value of len
	jmp loop1
end1:	
	push msg			;"Prefix Table:" is pushed onto stack
	call printf
	add esp,4			;esp is updated
loop2:					;this loop will print prefix table content
	mov ecx,dword[counter]		;counter is moved to ecx
	cmp ecx,dword[M]		;M and counter are compared
	je kmp_search			;print operation completed
	push dword [lps+4*ecx]		;content of current index of lps is pushed
   	push intf			;format is pushed
    	call printf
    	add  esp, 8			;esp is updated
	inc dword[counter]		;counter++
	jmp loop2
kmp_search:	
    	mov dword[i], dword 0		;i is cleared
    	mov dword[j], dword 0		;j is cleared
loop3:
	mov eax,dword[i]		;i->eax
	mov ecx,dword[j]		;j->ecx
	cmp eax,dword[N]		;i<N compare till end of text
	jge not_found			
	mov ebx, [ebp+12] 		;searched string 
	mov dl,[ebx+ecx]		;current char of searched string
	mov ebx,[ebp+8]			;text
	mov dh,[ebx+eax]		;current char of text
	cmp dl,dh			;pat[j] == txt[i]
	jne found_check
	inc dword[i]			;i++
	inc dword[j]			;j++

found_check:
	mov ecx,dword[j]		;j->ecx
	cmp ecx,dword[M]		;j == M
	je found
	mov eax,dword[i]		;i->eax
	cmp eax,dword[N]		;i < N 
	jge loop3			
	mov ebx, [ebp+12] 		;searched string
	mov dl,[ebx+ecx]		;current char of searched string
	mov ebx,[ebp+8]			;text
	mov dh,[ebx+eax]		;current char of text
	cmp dl,dh			;pat[j] != txt[i]
	je loop3
	cmp dword[j],dword 0		;j != 0
	je inc_i
	mov ecx,dword[j]		;j->ecx
	mov eax,dword[lps+4*ecx-4]	;lps[j-1]->eax;
	mov dword[j],eax		;eax->j
	jmp loop3
	
inc_i:	;
	inc dword[i]			;i++
	jmp loop3

found:
	mov eax,dword[i]		
	sub eax,dword[j]		;set return value(eax) as i-j
	jmp finish
	
not_found:
	mov eax,dword -1		;if there is no occurence, return -1
	
finish:
	mov esp, ebp 
	pop ebp
	ret
	

