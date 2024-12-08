input:read0 `:input.txt
observations:`timestamp xasc {`timestamp`info!("Z"$ 1 16 sublist x;19_x)} each input

midnightObservations:{[date]
  range:("z"$date) + (neg 01:00:00.000t;01:00:00.000);
  select from observations where timestamp within range}

zMinute:{"j"$("j"$`time$x)%60000}

observationGuardId:{[midnightObservation]"J"$-13_7_midnightObservation[0]`info}
parseObservations:{[midnightObservation]
  guardId:observationGuardId midnightObservation;
  `guardId`awakeMins`asleepMins!guardId,sum {$[1=count x;x,0;x]} each 2 cut deltas[0;] (zMinute each 1_midnightObservation`timestamp),60}

observedNights:1_distinct "d"$observations`timestamp
shiftObservations:{parseObservations midnightObservations x} each observedNights
guardlyShiftObservations:select sum awakeMins,sum asleepMins by guardId from shiftObservations
sleepiestGuard:first exec guardId from guardlyShiftObservations where asleepMins=max asleepMins

asleepMinutes:{[midnightObservation]
  raze {{x[0]+til x[1]-x 0} {$[1=count x;x,60;x]} x} each 2 cut (zMinute each 1_midnightObservation`timestamp)}

sleepiestGuardShiftNights:exec "d"$timestamp from observations where sleepiestGuard={"J"$-13_7_x} each info
sleepiestGuardShiftObservations:midnightObservations each sleepiestGuardShiftNights
sleepiestGuardAllAsleepMinutes:raze asleepMinutes each sleepiestGuardShiftObservations
sleepiestMinutes:{[allAsleepMinutes]{`sleepiestMinutes`timesAsleep!(where x=m;m:max x)} count each group allAsleepMinutes}
sleepiestGuardSleepiestMinutes:sleepiestMinutes[sleepiestGuardAllAsleepMinutes]`sleepiestMinutes
answerp1:rand sleepiestGuardSleepiestMinutes * sleepiestGuard

observedSleeps:{`guardId`asleepMins!(observationGuardId x;asleepMinutes x)} each midnightObservations each observedNights
guardlyAllObservedSleeps:select raze asleepMins by guardId from observedSleeps
guardlySleepiestMinutes:{`guardId`sleepiestMinutes`timesAsleep!(x[`guardId];x[`s;`sleepiestMinutes];x[`s;`timesAsleep])} each select guardId,s:sleepiestMinutes each asleepMins from guardlyAllObservedSleeps
sleepyGuardsSleepiestMinutes:select from guardlySleepiestMinutes where -7h=type each timesAsleep
answerp2:first exec guardId * rand sleepiestMinutes from sleepyGuardsSleepiestMinutes where timesAsleep=max timesAsleep

-1 "The answer to part 1 is ",string answerp1;
-1 "The answer to part 2 is ",string answerp2;

exit 0
