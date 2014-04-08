# Treasurer

Treasurer is a REST service for managing project artifacts. Artifacts are files
that are the result of your development processes. JARs, tarballs, images or
whatever else!

**Treasurer doesn't store artifacts, just URLs to them!** Treasurer's intent is
to provide a directory service for finding artifacts using your criteria.

# Installing

See [INSTALL.md](INSTALL.md)

# Documentation

[See the site!](http://gphat.github.io/treasurer/)

# Concepts

A `Project` is a container for `Artifacts`. **Artifacts are versioned by date and
have a URL. Treasurer makes no effort to sort version numbers.** The URL is where
an interested party might go to find the artifact itself.

**Example:** You have a HelloApp at your company. Your CI system runs all the tests
and verifies the latest merge to master works. At the end of it's run the CI
system uploads the resulting tarball (or whatever) to some remote place such as
S3. It also makes a call to Treasurer saying that at the current time there is
now an artifact at `$URL` that represents the latest artifact for HelloApp master.

Later you initiate a deploy to server `dc01-prod-app-0001`. The tools that perform
your deploy use Treasurer to determine _what_ to deploy and — after a successful
deploy — you make a call to Treasurer confirming.

Now other parts of your infrastructure can easily query Treasurer and ask the
following questions:

* What projects are there?: [`/1.0/projects`](http://gphat.github.io/treasurer/examples.html#projects-getAll)
* Where is the latest artifact for HelloApp:master?: [`/1.0/projects/1/artifacts/latest`](http://gphat.github.io/treasurer/examples.html#artifacts-latest)
* When was the last artifact built?: [`/1.0/projects/1/artifacts/latest`](http://gphat.github.io/treasurer/examples.html#artifacts-latest)
* What was git SHA of the latest artifact?: [`/1.0/projects/1/artifacts/latest`](http://gphat.github.io/treasurer/examples.html#artifacts-latest)
* Where is the previous artifact for HelloApp:master, in case I need to roll back?: [`/1.0/projects/1/artifacts?offset=1`](http://gphat.github.io/treasurer/examples.html#artifacts-offset)
* What was the git SHA of the build on a date in the past?: [`/1.0/projects/1/artifacts?date=2014-04-01T20:17:35Z`](http://gphat.github.io/treasurer/examples.html#artifacts-getByDate)
* What git SHA was server dc01-prod-app-0001 running on an arbitrary date?: [`/1.0/project/1/deploys?date=2014-04-01T20:17:35Z&device=dc01-prod-app-0001`](http://gphat.github.io/treasurer/examples.html#deploys-getByDate)

# Requirements

Treasurer is written in [Scala](http://www.scala-lang.org/) using the
[Play Framework](http://www.playframework.com/). It uses
[PostgreSQL](http://www.postgresql.org/) as a store.
