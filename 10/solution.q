input:read0 `:input.txt
start:{`x`y`dx`dy!"J"$/:trim each raze","vs/:0 -1 _' 10_/:"> " vs x} each input
movePoints:{update x:x+dx,y+dy from x}
bounds:{exec maxx:max x,minx:min x,maxy:max y,miny: min y from x}
positivise:{update x:x+abs min x,y:y+abs min y from x}
dimensions:{exec width:max x,height:max y from x}
blankCanvas:{[width;height]height#enlist width#"."}
coordinates:{flip value exec x,y from x}
makeCanvas:{[t]flip .[;;:;"#"]/[blankCanvas . 1 + reverse value dimensions t;coordinates t]}
findSmallDimensionIterations:{[n;start]
    iterations:n movePoints\ start;
    iterationDimensions:{prd dimensions positivise x} each iterations;

    // 48 was discovered by inspecting the top 50 outputs and seeing the message
    // in the second to last one (index 48).
    messageIteration:iasc[iterationDimensions] 48;
    (messageIteration;positivise iterations messageIteration)}
printCanvas:{-1 ("\n" sv makeCanvas x),"\n"}

`answerp1`answerPoints set' findSmallDimensionIterations[25000;start];
answerp2:264_/:244_ makeCanvas answerPoints

-1 "The answer to part 1 is ",string answerp1;
-1 "The answer to part 2 is...";
-1 answerp2;

exit 0
