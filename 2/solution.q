input:read0 `:input.txt

// For a single box id, we can count the letter frequency by doing
// `count each group x`. From here, we check for frequencies of 2s and 3s
// using `2 3 in/:` and because we only care about if there are any, rather
// than the number of them, we use `any`. The result is a boolean 2-list with
// if there are any 2 or 3-frequency letters respectively in the box id.
// We flip to get a 2-list where the first is a bitvector of box ids which
// have 2 or 3-frequency letters respectively. We `sum each` list to get the
// numbers of 2 and 3-frequency letter-containing box ids. `prd` gives us the
// final answer, the product of these two values.
answerp1:prd sum each flip {any 2 3 in/: count each group x} each input

// (crossed) is a list of 2-lists containing  every pairing of box ids. We
// apply `{1=sum x[0]<>x 1} each` to this, which returns a bitvector of
// (crossed) which is true where the number of differences between the two
// box id strings is 1. `crossed where` then gets the value of crossed at
// this point. There will be two (crossed) values satisfying this, due to our
// inconsiderate use of `cross`, i.e. we will have (boxid1;boxid2) and
// (boxid2;boxid1) in this list - so we do `first`. Finally, we get the common
// part of the string by doing `{x[0] where x[0]=x[1]}` which gets the first
// box id where the two box ids are equal. This is answer.
crossed:" "vs/:input cross " ",/:input
answerp2:{x[0] where x[0]=x[1]} first crossed where {1=sum x[0]<>x 1} each crossed:" "vs/:input cross " ",/:input

-1 "Answer to part 1 is ",string answerp1;
-1 "Answer to part 2 is ",answerp2;

exit 0
