// Computes sum=1+....+100

@i
M=1
@sum
M=0

(loop)
@i
D=M
@sum
M=D+M

@i
M=M+1
D=M
@100
D=D-A
@loop
D;JLE

(end)
@end
0;JMP