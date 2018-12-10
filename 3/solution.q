input:read0 `:input.txt

// Extract numerical information from a claim line
parseClaim:{[claim]{`id`x`y`w`h!"J"$/:(enlist 1_first x),raze ",x"vs'-1 0_'-2# x} " "vs claim}

// Co-ordinates of squares claimed by a particular paresd claim
claimedSquares:{(x`x;x`y)+/:(til x`w) cross til x`h}

// Table of claims information from the list of claim lines
claimsTable:parseClaim each input

// Count the number of claims on each square, represented as a pair of coordinates
perSquareClaimCounts:count each group raze claimedSquares each claimsTable

// Answer to part 1 is the number of squares which have more than 1 claim.
answerp1:count where 1<perSquareClaimCounts

// For a set of per square claim counts and a particular claim, returns 0 if the claim
// is contested, or the claim id if it isn't.
checkIfUncontested:{[pscc;c] $[count[claims]=sum pscc@claims:claimedSquares c;c`id;0]}

// The answer to part 2 is the id of the only claim which is entirely uncontested.
// answerp2:first r where 0<r:checkIfUncontested[perSquareClaimCounts;] each claimsTable

-1 "The answer to part 1 is ",string answerp1;
// -1 "The answer to part 2 is ",string answerp2;

// exit 0
