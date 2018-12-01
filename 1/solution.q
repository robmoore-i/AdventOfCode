// (input) contains the raw lines of text from the input
input:read0 `:input.txt

// (readings) contains the number forms of each of the frequency change
// readings
readings:"J"$/:input

// The final frequency reading is the sum of the frequency change readings
answerp1:sum readings

// (frqs) contains the frequency values (sum of frequency changes) at every
// point where once the list is exhausted we take readings from the start
// again. We require 140 repetitions until a repeated frequency value exposes
// itself, as discovered by comparing the `count` and `count distinct` values
// for the razed, repeated lists where the number of list repetitions is
// varied.
k)frqs:+\,/140#enlist readings

// (a) is a 2-list. a[0] is a list of frequencies which have been seen more
// than once. a[1] is a bitvector where a[1][i] is 1b if the frequency i has
// been seen before.
// (f) takes an instance of (a) and a frequency value, (frq), and if (frq) has
// been seen, that is, if a[1][frq] is 1b, then it is added to a[0].
// Otherwise, a[1][frq] is set to 1b, since it has now been seen. (f) returns
// an updated instance of (a).
f:{[a;frq]
  if[1b=a[1;frq]; :@[a;0;,;frq]];
  .[a;(1;frq);:;1b]}

// (g) folds (f) over (frqs), which results in an instance of (a) such that
// all the frequency values which are seen more than once appear in a[0].
// Some values in (frqs) will be negative, which means they can't appear in
// a bitvector, which is of course indexed by positive numbers. To fix this,
// the values in (frqs) are offset such that the most negative frequency
// corresponds to the 0 index. The offset value is therefore `neg min x`,
// and all values in x are offset by this amount, which is (nonneg). The
// initial bitvector for a[1] is created as a list of 0s as long as 1 + the
// maximum value in (frqs), so that `a[1][max frqs]` exists and doesnt cause
// an index error. This is therefore `1+max nonneg`. Then we invoke the fold
// and collect the final instance of (a), unapply the offset to get the
// original values for the frequencies seen more than once, and return that
// list.
g:{
  nonneg:x+offset:neg min x;
  finalA:f/[(();(1+max nonneg)#0b);nonneg];
  first @[finalA;0;-;offset]}

// The first frequency to be seen more than once will be the first value in
// the list of frequency values returned by `g frqs`.
answerp2:first g frqs

-1 "Answer to part 1 is ",string answerp1;
-1 "Answer to part 2 is ",string answerp2;

exit 0
