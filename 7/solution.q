input:read0 `:input.txt

// The dependency graph for the sleigh build steps
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

// Completes all the jobs in (g) using (n) workers.
complete:{[g;n]
  availableWorkers:n;
  workingSet:();
  completed:();
  time:0;
  while[count[allNodes]>count completed;
    // Assign work
    js:availableJobs[g;completed;workingSet];
    if[and[availableWorkers>0;0<count js:availableJobs[g;completed;workingSet]];
      // The number of available jobs which can be assiged
      nAssigned:min (availableWorkers;count js);

      // Start as many jobs as can be assigned
      jobsStarted:{[time;j](j;time+jobCost j)}[time;] each nAssigned#js;

      // Add the started jobs to the current working set
      workingSet:workingSet,jobsStarted;

      // Assigned workers are no longer available
      availableWorkers:availableWorkers-nAssigned;
    ];

    // Do work and roll the clock forward
    time:time+1;

    // Check who is done
    finishedWorkIndices:where time>=last each workingSet;
    if[0<count finishedWorkIndices;
      // Get the jobs which have been completed
      finishedJobs:(first each workingSet) finishedWorkIndices;

      // Add them to the list of completed jobs
      completed:completed,finishedJobs;

      // Those workers who have just completed a job are now available
      availableWorkers:availableWorkers+count finishedJobs;

      // Completed jobs are no longer in the working set.
      workingSet:workingSet (til count workingSet) except finishedWorkIndices;
   ];
  ];
  time}

answerp2:complete[g;5]

-1 "The answer to part 2 is ",string answerp2;
