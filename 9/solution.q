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

// Pre: c+1 is a multiple of 23
place23:{[l;c;i;s;p]
  nextMarble:c+1;
  removedMarble:l[i-7];
  addedScore:nextMarble+removedMarble;
  0N}

placeStandard:{[l;c;i;s;p]
  nextMarble:c+1;

  // The next marble is one place counter-clockwise from the 0 marble
  if[i=-2+count l; :(l,nextMarble;nextMarble;i+2;s;(p+1)mod count s)];
  
  // The current marble is one place counter-clockwise from the 0 marble
  if[i=-1+count l; :(l[0],nextMarble,1_l;nextMarble;1;s;(p+1)mod count s)];
  
  nextI:i+2;
  ((nextI#l),nextMarble,nextI _ l;nextMarble;nextI;s;(p+1)mod count s)}

// Play the marble game for (r) rounds with (p) players.
play:{[r;p]
  s:p#0;
  (r-2) .[place;]/ place[0 1;1;1;s;1]}
