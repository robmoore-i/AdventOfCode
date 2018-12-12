\l id3.k

input:read0 `:input.txt
initialState:15_first input
rules:2_input
features:`L1`L2`C`R1`R2
featureTable:(`result,features) xcols {(features,`result)!`$/:x 0 1 2 3 4 9} each rules
decisionTree:.id3.build[first cols featureTable;1_cols featureTable;featureTable]
classify:{[decisionTree;pots].id3.classify[decisionTree;features!`$/:pots]}
test:{all x[0]=x[1]} flip {classify[decisionTree;5#x],`$last x} each rules
