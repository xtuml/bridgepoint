#!/usr/bin/perl
while ( <> ) {
  s/\${#@\!:[A-Za-z0-9]*:[A-Fa-f0-9,-]*[:]?[A-Fa-f0-9,-]*:([A-Za-z0-9_' ]*)}/$1/g;
  print $_;
}
