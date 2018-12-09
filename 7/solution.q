input:read0 `:input.txt
g:{`src`dst!`$/:x 5 36} each input

startNodes:distinct g[`src] except g`dst
allNodes:distinct g[`src],g`dst

availableJobs:{[g;completed;workingSet]
  allJobs:startNodes,exec dst from g where src in completed;
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
      -1 raze "[",(string time),"] - Available jobs: ",(", "sv string each js);
      -1 raze "[",(string time),"] - Available workers: ",string availableWorkers;
      nAssigned:min (availableWorkers;count js);
      jobsStarted:{[time;j](j;time+jobCost j)}[time;] each nAssigned#js;
      workingSet:workingSet,jobsStarted;
      availableWorkers:availableWorkers-nAssigned;
      -1 raze "[",(string time),"] - Assigning jobs: "," ; "sv {x[0]," ",x 1}each string jobsStarted;
    ];
    
    // Do work
    time:time+1;
    
    // Check who is done
    finishedWorkIndices:where time>=last each workingSet;
    if[0<count finishedWorkIndices;
      finishedJobs:(first each workingSet) finishedWorkIndices;
      -1 raze "[",(string time),"] - Finished jobs: ",(", "sv string each finishedJobs);
      completed:completed,finishedJobs;
      availableWorkers:availableWorkers+count finishedJobs;
      workingSet:workingSet (til count workingSet) except finishedWorkIndices;
   ];
  ];
  time+1}
  
