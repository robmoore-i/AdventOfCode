/ Read the puzzle input using read0
/ Split each row by the three spaces in the middle using {"   " vs x} each
/ Parse the numbers using "J"$
/ Transpose using flip
/ Sort each of the lists using asc each
/ Find the absolute differences using {abs x[0] - x[1]}
/ Sum the differences using sum
partOne:sum {abs x[0] - x[1]} asc each flip "J"${"   " vs x} each read0 `:puzzle_inputs/puzzle_input_1.txt

/ Read the puzzle input using read0
/ Split each row by the three spaces in the middle using {"   " vs x} each
/ Parse the numbers using "J"$
/ Transpose using flip
/ Find all the elements of the second list which occur in the first list using {x[1] where x[1] in x[0]}
/ Sum the numbers using sum
partTwo:sum {x[1] where x[1] in x[0]} flip "J"${"   " vs x} each read0 `:puzzle_inputs/puzzle_input_1.txt
