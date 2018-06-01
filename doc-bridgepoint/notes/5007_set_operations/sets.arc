.create object instance foo1 of A_CLASS
.assign foo1.name = "foo1"
.assign foo1.num = 1
.create object instance foo2 of A_CLASS
.assign foo2.name = "foo2"
.assign foo2.num = 2
.create object instance foo3 of A_CLASS
.assign foo3.name = "foo3"
.assign foo3.num = 3
.create object instance foo4 of A_CLASS
.assign foo4.name = "foo4"
.assign foo4.num = 4
.// test sets
.//
.// test '+'
.print "Testing inst_ref + inst_ref"
.assign all_the_foos = foo1 + foo2
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo1
.// foo2
.//
.//.print "Testing inst_ref + inst_ref_set"
.//.select many foos from instances of A_CLASS where ( selected.num <= 2 )
.//.assign all_the_foos = foo3 + foos
.//.for each foo in all_the_foos
.//  .print "${foo.name}"
.//.end for
.// expected results:
.// foo1
.// foo2
.// foo3
.//
.//.print "Testing inst_ref_set + inst_ref"
.//.select many foos from instances of A_CLASS where ( selected.num <= 2 )
.//.assign all_the_foos = foos + foo3
.//.for each foo in all_the_foos
.//  .print "${foo.name}"
.//.end for
.// expected results:
.// foo1
.// foo2
.// foo3
.//
.//.print "Testing inst_ref_set + inst_ref_set"
.//.select many foos1 from instances of A_CLASS where ( selected.num <= 2 )
.//.select many foos2 from instances of A_CLASS where ( selected.num >= 3 )
.//.assign all_the_foos = foos1 + foos2
.//.for each foo in all_the_foos
.//  .print "${foo.name}"
.//.end for
.// expected results:
.// foo1
.// foo2
.// foo3
.// foo4
.//
.// test '-'
.print "Testing inst_ref - inst_ref"
.assign all_the_foos = foo1 - foo1
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.//
.//.print "Testing inst_ref - inst_ref_set"
.//.select many foos from instances of A_CLASS where ( selected.num <= 2 )
.//.assign all_the_foos = foo2 - foos
.//.for each foo in all_the_foos
.//  .print "${foo.name}"
.//.end for
.// expected results:
.//
.print "Testing inst_ref_set - inst_ref"
.select many foos from instances of A_CLASS where ( selected.num <= 2 )
.assign all_the_foos = foos - foo2
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo1
.//
.print "Testing inst_ref_set - inst_ref_set"
.select many foos1 from instances of A_CLASS
.select many foos2 from instances of A_CLASS where ( selected.num >= 3 )
.assign all_the_foos = foos1 - foos2
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo1
.// foo2
.//
.// test 'or'
.//.print "Testing inst_ref or inst_ref"
.//.assign all_the_foos = foo1 or foo2
.//.for each foo in all_the_foos
.//  .print "${foo.name}"
.//.end for
.// expected results:
.// foo1
.// foo2
.//
.//.print "Testing inst_ref or inst_ref_set"
.//.select many foos from instances of A_CLASS where ( selected.num <= 2 )
.//.assign all_the_foos = foo3 or foos
.//.for each foo in all_the_foos
.//  .print "${foo.name}"
.//.end for
.// expected results:
.// foo1
.// foo2
.// foo3
.//
.//.print "Testing inst_ref_set or inst_ref"
.//.select many foos from instances of A_CLASS where ( selected.num <= 2 )
.//.assign all_the_foos = foos or foo3
.//.for each foo in all_the_foos
.//  .print "${foo.name}"
.//.end for
.// expected results:
.// foo1
.// foo2
.// foo3
.//
.print "Testing inst_ref_set or inst_ref_set"
.select many foos1 from instances of A_CLASS where ( selected.num <= 2 )
.select many foos2 from instances of A_CLASS where ( selected.num >= 3 )
.assign all_the_foos = foos1 or foos2
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo1
.// foo2
.// foo3
.// foo4
.//
.// test 'and'
.//.print "Testing inst_ref and inst_ref"
.//.assign all_the_foos = foo1 and foo2
.//.for each foo in all_the_foos
.//  .print "${foo.name}"
.//.end for
.// expected results:
.//
.print "Testing inst_ref and inst_ref_set"
.select many foos from instances of A_CLASS where ( selected.num <= 2 )
.assign all_the_foos = foo2 and foos
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo2
.//
.print "Testing inst_ref_set and inst_ref"
.select many foos from instances of A_CLASS where ( selected.num <= 2 )
.assign all_the_foos = foos and foo2
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo2
.//
.print "Testing inst_ref_set and inst_ref_set"
.select many foos1 from instances of A_CLASS where ( selected.num <= 3 )
.select many foos2 from instances of A_CLASS where ( selected.num >= 3 )
.assign all_the_foos = foos1 and foos2
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo3
.//
.// test '|'
.print "Testing inst_ref | inst_ref"
.assign all_the_foos = foo1 | foo2
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo1
.// foo2
.//
.print "Testing inst_ref | inst_ref_set"
.select many foos from instances of A_CLASS where ( selected.num <= 2 )
.assign all_the_foos = foo3 | foos
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo1
.// foo2
.// foo3
.//
.print "Testing inst_ref_set | inst_ref"
.select many foos from instances of A_CLASS where ( selected.num <= 2 )
.assign all_the_foos = foos | foo3
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo1
.// foo2
.// foo3
.//
.print "Testing inst_ref_set | inst_ref_set"
.select many foos1 from instances of A_CLASS where ( selected.num <= 2 )
.select many foos2 from instances of A_CLASS where ( selected.num >= 3 )
.assign all_the_foos = foos1 | foos2
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo1
.// foo2
.// foo3
.// foo4
.//
.// test '&'
.print "Testing inst_ref & inst_ref"
.assign all_the_foos = foo1 & foo2
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.//
.print "Testing inst_ref & inst_ref_set"
.select many foos from instances of A_CLASS where ( selected.num <= 2 )
.assign all_the_foos = foo2 & foos
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo2
.//
.print "Testing inst_ref_set & inst_ref"
.select many foos from instances of A_CLASS where ( selected.num <= 2 )
.assign all_the_foos = foos & foo2
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo2
.//
.print "Testing inst_ref_set & inst_ref_set"
.select many foos1 from instances of A_CLASS where ( selected.num <= 3 )
.select many foos2 from instances of A_CLASS where ( selected.num >= 3 )
.assign all_the_foos = foos1 & foos2
.for each foo in all_the_foos
  .print "${foo.name}"
.end for
.// expected results:
.// foo3
.//
