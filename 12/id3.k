\d .id3

log2:{1.442695*log x}
// Get the entropy of a (d)ataset with respect to a (c)lassification column.
E:{[c;d]+/-:'{p*log2 p:y%x}[#d;]'sum'd[c]=/:?d[c]}

project:{[d;a;v]?[d;,(=;a;,v);0b;()]}
// Get the information gain for an (a)ttribute of a (d)ataset with respect
// to a (c)lassification column.
IG:{[c;d;a]E[c;d]-+/{[c;n;dv]E[c;dv]*#:[dv]%n}[c;#d;]'project[d;a;]'?d[a]}

// Gets an element of y corresponding to the max element of x.
atargmax:{*y@&:x=max x}

// For (d)ataset, returns the (f)eature column with the maximal information
// gain, regarding the given (c)lassification column.
nodeattr:{[c;f;d]atargmax[IG[c;d;]'f;f]}

// For the given (d)ataset, a table, use the (f)eature columns to build a
// decision tree for the given (c)lassification column.
build:{[c;f;d]$[1=#?d[c];*:d[c];buildrecurse[c;f;d]]}
buildrecurse:{[c;f;d]
  A:nodeattr[c;f;d];
  branch:{[c;f;A;d;v]build[c;.q.except[f;A];project[d;A;v]]}[c;f;A;d;];
  m:v!branch'v:?d[A];
  `attribute`mapping!(A;m)}

// Classify a (f)eature dictionary using a built decision (t)ree.
classify:{[t;f]
  branch:f t[`attribute];
  child:t[`mapping] branch;
  $[99h=@:child;classify[child;f];child]}
