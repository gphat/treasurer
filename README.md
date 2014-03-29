Treasurer is a service for managing project artifacts. Artifacts are files that
are the result of your development processes. JARs, tarballs, images or whatever
else!

*Treasurer doesn't store artifacts, just pointers to them!* Treasurer's intent is
to provide a directory service for finding artifacts using your criteria.

## Concepts

A *Project* is a container for *Artifacts*. Artifacts are versioned by date and
have a URL. The URL is where an interested party might go to find the artifact
itself.

Example: You have a HelloApp at your company. Your CI system runs all the tests
and verifies the latest merge to master works. At the end of it's run the CI
system uploads the resulting tarball (or whatever) to some remote place such as
S3. It also makes a call to Treasurer saying that at the current time there is
now an artifact at URL n that represents the latest artifact for HelloApp master.

Later you initiate a deploy to server `dc01-prod-app-0001`. The tools that perform
your deploy use Treasurer to determine _what_ to deploy and — after a successful
deploy — you make a call to Treasurer confirming.

Now other parts of your infrastructure can easily query Treasurer and ask the
following questions:

* Where is the latest artifact for HelloApp:master?
* Where is the previous artifact for HelloApp:master, in case I need to roll back.
* When was the last artifact built?
* What was git SHA of the latest artifact?
* What was the git SHA of the current build as of an arbitrary date in the past?
* What git SHA was server dc01-prod-app-0001 running on an arbitrary date?
