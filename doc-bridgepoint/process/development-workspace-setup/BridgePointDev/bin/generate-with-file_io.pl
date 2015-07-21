#!perl
#=============================================================================
# This script sets up the necessary directories ahead of calling the Windows
# version of the generator launching script.
#=============================================================================
use File::Basename;

$r = "$ENV{'PT_HOME_DRIVE'}$ENV{'PT_HOME'}";
$r =~ s/"//g;
$p = $ENV{'COMSPEC'};
$p =~ s/\\/\//g;
$shell = basename($p);
$cmd = "$r/bin/xtumlmc_gen_erate.cmd";
$code_gen = "code_generation";
if ( -f $cmd ) {
  my $workingdir = shift(@ARGV);
  chdir("gen");
  if ( ! -d $code_gen ) {
    mkdir($code_gen);
  }
  chdir($code_gen);
  if ( ! -d $workingdir ) {
    mkdir($workingdir);
  }
  chdir($workingdir);
  my @newargs;
  foreach $argnum (0 .. $#ARGV) {
    push @newargs, $ARGV[$argnum];
  }
  system( "$shell /c $cmd @newargs 2>&1" );
} else {
  die "Could not find and execute $cmd.\n";
}
