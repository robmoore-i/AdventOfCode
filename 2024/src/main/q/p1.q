/ Read the puzzle input using read0
/ Split each row by the three spaces in the middle using {"   " vs x} each
/ Parse the numbers using "J"$
/ Sort each of the lists, which first need to be transposed, using asc each flip
/ Find the absolute differences using {abs x[0] - x[1]}
/ Sum the differences using sum
sum {abs x[0] - x[1]} asc each flip "J"${"   " vs x} each read0 `:puzzle_inputs/puzzle_input_1.txt
