# Time Management

A simple multi-user time management system created to demonstrate a fully-functioning
web-app.

Frontend in Svelte, backend in Kotlin with Ktor and kotlinx.serialization, with persistence
in SQLite via Exposed. Packaged up with Docker.

## Instructions

### Docker

The easiest way to build, assuming you have Docker installed:

* `docker build . -t timemanagement`
* `docker run --rm -p 8080:8080 timemanagement`
* Visit http://localhost:8080 in a web browser

This uses a multi-stage build process for an efficient final image.

### Manual

* Install UI dependencies: `cd ui && npm install`
* Build the UI: `cd ui && npm run build`
* Build the backend: `mvn package`
* Run the app: `java -jar target/time-management-1.0-SNAPSHOT-jar-with-dependencies.jar`
* Visit http://localhost:8080 in a web browser

To run tests, run `mvn test`.

## Notes

Upon login, users have ADMIN permission by default. This is for demonstration purposes only.

Security is handled by a session ID in a HttpOnly cookie, with passwords hashed with PBKDF2
and salted. The salted can be overridden with a PASSWORD_HASH environment variable.
There is no requirement for password complexity. 


## TODO

Fixes required:

- Populate database with default users, instead of having admins by default.
- Regular users don't have the colouring feature implemented.
- For admins, there's missing the green colouring for entries.
- No pagination nor lazy loading implemented.
- Managers have access to the user's entries but not to the user's profiles.
- When admin deletes himself, he can still use the app.