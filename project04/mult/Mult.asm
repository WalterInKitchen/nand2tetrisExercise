// Computes result=R0*R1

@i
M=0
@R2
M=0

@R0
D=M
@end
D;JEQ

@R1
D=M
@end
D;JEQ

(loop)
@R1
D=M
@R2
M=D+M           //R2=R2+R1

@i
M=M+1           //i=i+1

@R0
D=M
@i
D=D-M
@loop
D;JNE           //if i<R0,continue

(end)
@end
0;JMP