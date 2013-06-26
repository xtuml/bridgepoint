#!/usr/bin/perl
$count = 0;
$hex = "[A-Za-z0-9]";
open(MAP, @ARGV[0]);
while ( <MAP> ) {
  if (/($hex{8}-$hex{4}-$hex{4}-$hex{4}-$hex{12}):($hex{8}-$hex{4}-$hex{4}-$hex{4}-$hex{12})/) {
    $src[$count] = $1;
    $dst[$count] = $2;
    $count++;
  }
}
close(MAP);
printf "Entries: %i\n" , $count;
recurseDirs(@ARGV[1]);

sub recurseDirs {
  my($count, $file, @files);
  opendir(DIR, $_[0]) || die "Could not open directory $_[0] $!\n";
  $count = 0;
  while ($file = readdir(DIR)) {
    if ($file ne "." && $file ne "..") {
      $_ = "$file";
      if (/CVS/ && $' eq "") {
        # don't descend the CVS folder tree
      }
      else {
        if (!m/\.oal/ && !m/\.dsc/) {
  	      $files[$count] = $file;
  	      $count++;
  	    }
      }
    }
  }
  closedir(DIR);
  foreach $file (@files) {
  	$_ = $file;
    if (m#[a-zA-Z0-9 _\\\ \/]*.xtuml#) {
      if ($' eq "") {
        print "Scanning: $_[0]\\".$file."\n";
        doMap("$_[0]\\".$file);
      }
    }
    else {
      recurseDirs("$_[0]\\".$file);
    }
  }
}

sub doMap {
  open(MODEL, $_[0]) || die "Could not open unchanged source file: $!";
  open(RESULT, ">".$_[0].".cnv") || die "Could not open working file: $!";
  while ( <MODEL> ) {
    $lineno++;
    if (/"($hex{8}-$hex{4}-$hex{4}-$hex{4}-$hex{12})"/) {
      for ($i = 0; $i < $count; $i++) {
        if ($1 eq $src[$i]) {
          s/"$hex{8}-$hex{4}-$hex{4}-$hex{4}-$hex{12}"/"$dst[$i]"/;
          last;
        }
      }
    }
    print RESULT $_;
  }
  close(MODEL);
  close (RESULT);
  if (equals($_[0], $_[0].".cnv")) {
    unlink ($_[0].".cnv") || die "Could not delete unchanged working file: $!";
  }
  else {
    print "Updated : $_[0]\n";
    unlink ($_[0]) || die "Could not delete changed source file: $!";
    rename ($_[0].".cnv", $_[0]) || die "Could not rename changed working file: $!";
  }
}

sub equals {
  open (LEFT, $_[0]);
  open (RIGHT, $_[1]);
  $result = 1;
  while (<LEFT>) {
    $r = <RIGHT>;
    if ($_ ne $r) {
      $result = 0;
    }
  }
  close(LEFT);
  close(RIGHT);
  return $result;
}