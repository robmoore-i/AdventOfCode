input:first read0 `:input.txt

// The difference between the int codes for characters which are the 
// capital/lowercase version of each other. The value is 32.
capDiff:last deltas "j"$"Zz"

// Finds the indices at which units will react and be destroyed
k)destroyedUnitIndices:{
  -1+&:|':{(capDiff=*:a) & ~:a[0]=(a:x@0 1+y)1}[letterDiffs;]'!#letterDiffs:abs'-':"j"$x}

// Shrinks a polymer by finding the destroyed indices and removing them
react:{x@(til count x) except destroyedUnitIndices x}

// Reacts a polymer until it is stable and returns the final version of it
k)completeReaction:{reactions:react\ x;last reactions}

// The answer to part 1 is the length of the fully reacted form of the
// input polymer.
// answerp1:count completeReaction input

alphabet:"c"${x[0]+til 1+x[1]-x 0}"j"$"az"
removeUnitType:{x except (lower y;upper y)}

// For every unit type, get the polymer with those units pruned.
prunedPolymers:{`removedUnit`polymerLength`polymer!(x;count p;p:removeUnitType[input;x])} each alphabet

countReactedPrunedPolymer:{
  -1 "Completing reaction for polymer without unit ",x`removedUnit;
  count completeReaction x`polymer}

// The answer to part 2 is the length of the shortest pruned polymer
// answerp2:min countReactedPrunedPolymer each prunedPolymers

// -1 "The answer to part 1 is ",string answerp1;
// -1 "The answer to part 2 is ",string answerp2;
