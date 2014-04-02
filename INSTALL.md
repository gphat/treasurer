# Installing

## Create A Database User & Database

Create a user called `treasurer`:

  CREATE USER treasurer;

Create a database called `treasurer`.

  CREATE DATABASE treasurer OWNER treasurer ENCODING 'utf8';

## Setting Up The App

Create a file `conf\application-local.conf` and adjust the following settings:

```
db.default.driver=org.postgresql.Driver
db.default.url="jdbc:postgresql://localhost/treasurer"
db.default.user=treasurer
db.default.password=""
```

### Running The App

Start the play REPL:

```bash
play
```

Run the app!

```
[treasurer] $ run

--- (Running the application from SBT, auto-reloading is enabled) ---

[info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

(Server started, use Ctrl+D to stop and go back to the console...)
```

Visit http://localhost:9000 and you'll see an "ok"
