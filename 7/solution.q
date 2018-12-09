input:read0 `:input.txt
g:{`src`dst!`$/:x 5 36} each input

startNodes:distinct g[`src] except g`dst
allNodes:distinct g[`src],g`dst

// Returns 1b if x is a subset of y
subset:{all x in\: y}

// Returns 1b if (node)'s dependencies in (g) are satisfied by (completed)
k)dependenciesSatisfied:{[g;completed;node]subset[?[g;,(=;`dst;,node);0b;(,`src)!,`src]`src;completed]}

// Returns a list of jobs which can be started immediately.
availableJobs:{[g;completed;workingSet]
  allJobs:startNodes,allNodes where dependenciesSatisfied[g;completed;] each allNodes;
  excludedJobs:completed,raze first each workingSet;
  asc distinct allJobs except excludedJobs}

jobCost:{-4+"j"$first string x}

complete:{[g;n]
  availableWorkers:n;
  workingSet:();
  completed:();
  time:0;
  while[count[allNodes]>count completed;
    // Assign work
    js:availableJobs[g;completed;workingSet];
    if[and[availableWorkers>0;0<count js:availableJobs[g;completed;workingSet]];
      nAssigned:min (availableWorkers;count js);
      jobsStarted:{[time;j](j;time+jobCost j)}[time;] each nAssigned#js;
      workingSet:workingSet,jobsStarted;
      availableWorkers:availableWorkers-nAssigned;
    ];
    
    // Do work
    time:time+1;
    
    // Check who is done
    finishedWorkIndices:where time>=last each workingSet;
    if[0<count finishedWorkIndices;
      finishedJobs:(first each workingSet) finishedWorkIndices;
      completed:completed,finishedJobs;
      availableWorkers:availableWorkers+count finishedJobs;
      workingSet:workingSet (til count workingSet) except finishedWorkIndices;
   ];
  ];
  time}
  
answerp2:complete[g;5]

-1 "The answer to part 2 is ",string answerp2;
