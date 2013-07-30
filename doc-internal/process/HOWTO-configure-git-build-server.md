Configuring build server
-------------------------
- add local user build with administrator rights
- add firefox
- add cygwin (with subversion, ssh, perl, smtp, ca-certificates, libsasl2, git, zip, unzip)
- mkdir c:\builds
- mkdir c:\cygwin\git\xtuml
- mkdir c:\temp
- mkdir c:\temp_linux
- mkdir c:\utilities
- Install JDK 6
  - Set JAVA_HOME env var to the JDK folder
  - Add <JDK path>/bin to the PATH env var
- Set up ssh key
  - run ssh-keygen on new server as user build
  - put public key from build server into ~/.ssh/authorized_keys on tucson for user build
- In cygwin, set up /etc/ssmtp/ssmtp.conf
```
root=postmaster
mailhub=mail.mentor.com
rewriteDomain=<machine name>
hostname=<machine name>
```
- Manually set up files in c:\builds that drive the rest of the process
  - copy run_*_build_git.bat out of git into c:\builds\
  - copy init_git_repositories.sh out of git into c:\builds\
- Set up scheduled task for batch script(s)

