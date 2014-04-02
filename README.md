# Treasurer

Treasurer is a service for managing project artifacts. Artifacts are files that
are the result of your development processes. JARs, tarballs, images or whatever
else!

**Treasurer doesn't store artifacts, just URLs to them!** Treasurer's intent is
to provide a directory service for finding artifacts using your criteria.

# Concepts

A `Project` is a container for `Artifacts`. **Artifacts are versioned by date and
have a URL. Treasurer makes no effort to sort version numbers. The URL is where
an interested party might go to find the artifact itself.

**Example:** You have a HelloApp at your company. Your CI system runs all the tests
and verifies the latest merge to master works. At the end of it's run the CI
system uploads the resulting tarball (or whatever) to some remote place such as
S3. It also makes a call to Treasurer saying that at the current time there is
now an artifact at `$URL` that represents the latest artifact for HelloApp master.

**Note:** Deploys are not done yet.

Later you initiate a deploy to server `dc01-prod-app-0001`. The tools that perform
your deploy use Treasurer to determine _what_ to deploy and — after a successful
deploy — you make a call to Treasurer confirming.

Now other parts of your infrastructure can easily query Treasurer and ask the
following questions:

* Where is the latest artifact for HelloApp:master?: `/1.0/project/n/latest`
* When was the last artifact built?: `/1.0/project/n/latest`
* What was git SHA of the latest artifact?: `/1.0/project/n/latest`
* Where is the previous artifact for HelloApp:master, in case I need to roll back?: `/1.0/projects/1/artifacts?offset=1`
* What was the git SHA of the current build as of an arbitrary date in the past?: `/1.0/projects/1/artifacts?date=2014-04-01T20:17:35Z`
* What git SHA was server dc01-prod-app-0001 running on an arbitrary date?: (TBD)

# Examples

## Create a Project

```bash
curl -H "Content-type: application/json" -X POST http://localhost:9000/1.0/projects -d '{"name":"treasurer"}'
```

## Get All Projects

```bash
curl -X GET http://localhost:9000/1.0/projects
```

## Get a Project

```bash
curl -X GET http://localhost:9000/1.0/projects/1
```

## Delete a Project

```bash
curl -X DELETE http://localhost:9000/1.0/projects/1
```

## Create An Artifact for a Project

You've just finished 1.0.0 of your project. Make an entry!

```bash
curl -H "Content-type: application/json" -X POST http://localhost:9000/1.0/projects/1/artifacts -d '{"id":"7217c408", "version":"1.0.0", "url":"http://www.example.com/treasurer-1.0.0.zip"}'
```

After releasing you realize there were a couple bugs. You fix them up and then
make a new release!

```bash
curl -H "Content-type: application/json" -X POST http://localhost:9000/1.0/projects/1/artifacts -d '{"id":"7217c409", "version":"1.0.1", "url":"http://www.example.com/treasurer-1.0.1.zip"}'
```

## Get All Artifacts for a Project

```bash
curl -X GET http://localhost:9000/1.0/projects/1/artifacts
```

## Get Previous Artifacts for a Project (offset from current)

```bash
curl -X GET http://localhost:9000/1.0/projects/1/artifacts?offset=1
```

## Get the Latest Artifact for a Project

```bash
curl -X GET http://localhost:9000/1.0/projects/1/latest
```

## Get a Specific Artifact

```bash
curl -X GET http://localhost:9000/1.0/projects/1/artifacts/7217c408
```

## Get the Artifact Before the Current Artifact

```bash
curl -X GET "http://localhost:9000/1.0/projects/1/artifacts?offset=1"
```

## Get the Latest Artifact for a Given Date

Note that this expects ISO8601 dates.

```bash
curl -X GET "http://localhost:9000/1.0/projects/1/artifacts?date=2014-04-02T08:14:16Z"
```

## Delete an Artifact

```bash
curl -X DELETE http://localhost:9000/1.0/projects/1/artifacts/7217c408
```
