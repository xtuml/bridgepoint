#!/usr/bin/perl
while ( <> ) {
  s/"([A-Za-z0-9]{8}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{12})"/'$1'/g;
  print $_;
}
