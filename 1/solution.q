input:read0 `:input.txt
readings:"J"$/:input
answerp1:sum readings
k)frq:+\,/140#enlist readings
f:{[accANDseenValues;frq]
  if[1b=accANDseenValues[1;frq]; :@[accANDseenValues;0;,;frq]];
  .[accANDseenValues;(1;frq);:;1b]}
g:{
  nonneg:x+offset:neg min x;
  (first f/[(();(1+max nonneg)#0b);nonneg] 0)-offset}
answerp2:g frq

-1 "Answer to part 1 is ",string answerp1;
-1 "Answer to part 2 is ",string answerp2;

exit 0
