\c 15 70
input:read0 `:input.txt
coordinatesTable:("JJ"; enlist ",") 0: (enlist "x,y"),input
bounds:first select maxx:max x,minx:min x,maxy:max y,miny:min y from coordinatesTable
coordinates:flip coordinatesTable`x`y

{key[x]set'value x} bounds;

// x is distance from left
// y is distance from top

// Points we must consider: (minx - 1, miny - 1) to (maxx + 1, maxy + 1)
// (minx - 1 + i, miny - 1 + j) for all i < (3 + maxx - minx), j < (3 + maxy - miny)
consideredPoints:(minx-1;miny-1) +/: (til 3+maxx-minx) cross til 3+maxy-miny

// Manhattan distance of (a, b) from (c, d) :== |a-b| + |c-d| :== q)sum abs x-y
manhattanDistance:{sum abs x-y}

// Closest marked coordinate from a point as a 2-list
nearestCoordinate:{coordinates where ds=min ds:manhattanDistance/:[x;coordinates]}

k)isEdgePoint:{|/x in'((minx;maxx);(miny;maxy))}

// If x is an edge point, return null, else return the marked coordinate 
// which is closest to it.
markedCoordinateOfArea:{
  nc:nearestCoordinate x;
  $[isEdgePoint x;(nc;0b);(nc;1b)]}

areaPoints:markedCoordinateOfArea each consideredPoints
areaPointsTable:flip `markedCoordinate`finite!flip areaPoints
infiniteAreaOwners:distinct raze exec markedCoordinate from areaPointsTable where not finite
finiteAreaOwners:coordinates except infiniteAreaOwners

finiteAreaPoints:exec markedCoordinate from areaPointsTable where markedCoordinate in enlist each finiteAreaOwners

// The answer to part 1 is the number of points contained in the largest finite area 
answerp1:max count each group finiteAreaPoints

distanceToAllCoordinates:{sum manhattanDistance/:[x;coordinates]}
withinSafeRegion:{10000>distanceToAllCoordinates x}

answerp2:sum withinSafeRegion each consideredPoints

-1 "The answer to part 1 is ",string answerp1;
-1 "The answer to part 2 is ",string answerp2;

exit 0