'f t'=:|:(5 36&{&>)"0 cutLF CR-.~fread'input.txt' NB. f-from, t-to

echo 3 : 0 ~.f{~I.0=f e.t NB. candidates, start with those in f, not in t
  N=.5     NB. max number of workers
  c=.0     NB. clock, time elapsed, to be returned
  w=.0 2$0 NB. workers - pairs (time left, a.i.job)
  r=.''    NB. result of part1, now it's what's done
  while. 0<#y do. NB. one step analyzes/rebuilds y, appends to r, counts c
    NB. find a new candidate
    k=. 1 i.~([:*./r e.~f{~[:I.t=])"0 y=. /:~ y
    if. k<#y do. NB. available candidates - some of them are ready, so k is ok
      u=. k{y NB. that is job u must be started next (u - use it)
              NB. and it'll take 60+_64+a.i.u seconds
      if. N>#w do. NB. if there are free workers
        w=.w, (60&+,])_64+a.i.u NB. add job
        y=. y-.u  NB. remove u from candidates
        if. 0<#y do. continue. end. NB. if more candidates, try to add new jobs
        NB. no more candidates
      end.
      NB. no more free workers or no more candidates -- resume the clock
    end.
    w=. /:~w  NB. sort the workers, with shortest time first
    z=. {.{.w NB. next event - in this time
    c=. c+z   NB. total time
    w=. w -"1 z,0 NB. decrease times in workers
    u=. a.{~64+{:{.w NB. job is done - move it to result, find its dependants
    r=. r,u
    w=. }.w
    y=. ~. y, t{~I.u=f NB. add new canditates - those who depend on u
  end.
  c
)

exit 0
