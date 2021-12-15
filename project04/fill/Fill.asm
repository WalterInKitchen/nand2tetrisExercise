(restart)

@pixel
M=0

(loop)
@pixel
D=M
@8192   //the max pixel size(256x512/16)
D=D-A
@restart    
D;JEQ    //if pixel > upperBound;restart

@fillColor //set the back point of the branch
D=A
@lr
M=D

@color
M=-1
@24576              //the keyboard mapped address
D=M
@setColorZero
D;JEQ

(fillColor)

@pixel
D=M
@16384
D=D+A       // the pixel to fill
@nextPixel
M=D

@color
D=M

@nextPixel
A=M
M=D

@pixel
M=M+1

@loop
0;JMP

(setColorZero)
@color
M=0
@lr
A=M
0;JMP