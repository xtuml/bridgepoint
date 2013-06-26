# Perl "library" for chopping up commit messages.
#
# (C) Tony Garnock-Jones, 2000.
# Released under the Perl "Artistic" License:
# http://www.perl.com/language/misc/Artistic.html
#
# $Id: logmsg-parsing.pl,v 1.1 2007/02/02 20:38:59 tlondon Exp $

###########################################################################
# Configuration

$transaction_duration = 600;			# seconds.
$bugzilla_path = "/opt/bugzilla-2.16.4";

@valid_cvs_roots = (
		    "/arch1/products/tiger/repository",	# for "cvs tag" etc.
		    "/arches/arch3/products/tiger/repository",
		    "/arch3/products/tiger/repository"
);

@jobid_re = (
	     "Job:",
	     "job:",
	     "Bug:",
	     "bug:"
);

@nonlogged_branches = (
	      "McC_Sandbox",
	      "WGT",
	      "GBR_Sandbox",
	      "DEF_sandbox",
	      "tlondon_sandbox",
	      "BAA_Sandbox",
	      "LHE_Sandbox",
	      "SID_Sandbox",
	      "FAM_Sandbox"
);

###########################################################################
# Subroutines

# Exit the script with non-zero exit status.
sub diePrinting($) {
    ($errorText) = @_;
    print STDERR "$errorText\n";
    print STDOUT "-1";
    exit(1);
}

# Read in the list of general jobs from the database.
sub readGeneralJobs() {
    my $query = $::db->query("select jobname from general_jobs where active = 1");
    my @row;

    %general_jobs = ();
    while ((@row = $query->fetchrow())) {
	my $jobname = shift @row;
	#print "General jobname ***$jobname***\n";
	$general_jobs{$jobname} = 1;
    }
    #print "Done.\n";
}

# Returns true if the job ID passed in is a "general" job - that is, a
# keyword which exempts the commit from being logged against a bug
# number.
sub isGeneralJob($) {
    my ($jobid) = @_;

    if (exists $general_jobs{$jobid}) {
	# It's a general job. It's OK to commit against these.
	return 1;
    } else {
	# It's not a general job. Treat it like a Bugzilla bug number.
	return 0;
    }
}

# Split up a "Job:" line into an array of job IDs.
sub splitJobLine($) {
    my($jobline) = @_;
    my($found_pattern) = 0;

    foreach my $jobid_pattern (@jobid_re) {
	$jobline =~ s/^$jobid_pattern// || next;
	$found_pattern = 1;
	last;
    }

    if ($found_pattern == 1) {
	my @jobids = split(/\s+/, $jobline);
	my @result = ();
	for my $jobid (@jobids) {
	    $jobid || next;
	    push @result, $jobid;
	}
	return (1, @result);
    } else {
	return (0, ());
    }
}

sub sortJobIDs(\@) {
    my ($aref) = @_;
    my @gens = ();
    my @bugs = ();

    for my $id (@$aref) {
	if (isGeneralJob($id)) {
	    push @gens, $id;
	} else {
	    push @bugs, $id;
	}
    }

    @gens = sort @gens;
    @bugs = sort {$a <=> $b} @bugs;
    push @gens, @bugs;

    return join(" ", @gens);
}

sub stripCVSRoot($) {
    my ($path) = @_;

    foreach my $root (@valid_cvs_roots) {
	if ($path =~ m|^${root}/(.*)|) {
	    return $1;
	}
    }

    diePrinting("Invalid repository path - $path");
}

###########################################################################
# Initialization

$db = Mysql->Connect("localhost", "vc", "vc", "bug")
    || diePrinting("Could not connect to VC database");
readGeneralJobs();

1;
