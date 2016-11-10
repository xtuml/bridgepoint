# HOWTO Setup Two-factor Authentication with Github

## Instructions

### Setup 2FA on Github account

First, you must set up 2FA on your account.

* Go to Settings -> Security -> Two-Factor Authentication
* You are given the option to set up with SMS or an authentication app (I chose SMS, and it works great)
* Download the recovery codes just in case you do not have your phone handy

There you go! Now whenever you sign out or login on a different device/browser,
Github will require your password along with a security code.

### Setup Github remotes for your local git repositories

#### SSH

If you are using SSH to clone/push/pull to remotes on Github, you do not have to change anything
as your identity is already verified by your public key on your account.

If you are not using SSH for remotes and would like to start, follow this guide on the Github help
pages: [Generating an SSH key](https://help.github.com/articles/generating-an-ssh-key/)

#### HTTPS

To use HTTPS, you must take extra steps to setup using OAuth tokens.

* Remove any password caching you have in place (I had my Github credentials saved in my OSX key-chain
    to avoid typing in my username/password every time)
* Set up a personal access token. You can do this by following the guide [here](https://help.github.com/articles/creating-an-access-token-for-command-line-use/)
* You can use this token instead of your password when git prompts for it on a push
* If you wish to cache your OAuth token to avoid typing it in every time, you can follow this guide:
    [Caching your Github password in git](https://help.github.com/articles/caching-your-github-password-in-git/)

I have been using SSH for a long time, because I find encryption and authentication using my public key to
be more convenient than passwords with HTTPS, however, there are some situations in which I find it necessary
to use HTTPS (e.g. working behind a network that only allows communication on ports 80 and 443).

#### EGit

If you are using EGit within Eclipse to work with git, all of the above still applies within EGit. If you
are using HTTPS with 2FA, you must create a personal access token as described above and use it in place
of your Github password.

If you prefer to switch from HTTPS to SSH for working with Github remotes, you can do the following:

* From the Git perspective, right click on a repository and click "Properties"
* Scroll down to the "remote" section
* Go to the repository on Github.com and copy the SSH clone URL
* Replace the URL field in the properties with the SSH clone URL from Github
* Click "Apply"
