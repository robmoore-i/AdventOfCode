nPlayers:439
lastMarblePoints:71307

// Given a marble circle, (l), the value of the (c)urrent marble and its
// (i)ndex within the circle, as well as the (s)cores list and the current
// (p)layer.
// Return a triple of the new marble circle, the value of the new current
// marble, its index in the circle, the updated scores list and the next
// player.
place:{[l;c;i;s;p]
  $[0=(c+1)mod 23;
    place23[l;c;i;s;p];
    placeStandard[l;c;i;s;p]]}

nextPlayer:{[p;s](p+1)mod count s}

// Pre: c+1 is a multiple of 23
place23:{[l;c;i;s;p]
  removedMarbleIndex:(i-7) mod count l;
  removedMarble:l[removedMarbleIndex];
  addedScore:(c+1)+removedMarble;
  s[p]:s[p]+addedScore;
  droppedL:(removedMarbleIndex#l),(removedMarbleIndex+1) _ l;
  placeStandard[droppedL;c+1;removedMarbleIndex;s;nextPlayer[p;s]]}

placeStandard:{[l;c;i;s;p]
  nextMarble:c+1;
  $[i=-1+count l;
    (l[0],nextMarble,1_l;nextMarble;1;s;nextPlayer[p;s]);
    ((nextI#l),nextMarble,nextI _ l;nextMarble;nextI:(i+2) mod 1+count l;s;nextPlayer[p;s])]}

// Play the marble game for (r) rounds with (p) players.
play:{[r;p]
  s:p#0;
  rPrime:r-floor r%23;
  (rPrime-2) .[place;]/ place[0 1;1;1;s;1]}

// Given a game of (p) players and the (lastMarble)'s value
// Return the highest score.
highScore:{[p;lastMarble]
  g:play[lastMarble;p];
  max g 3}

answerp1:highScore[nPlayers;lastMarblePoints]

-1 "The answer to part 1 is ",string answerp1;
-1 "The answer to part 2 is not in Q because I can't make a performant solution using only arrays (So I made a python solution using linked lists)"

exit 0
