# SAAP Behind The Rescue API
For coordinating adoption events!
## Setup
Create the file `application/src/main/resources/application-local.yaml`
and add the following content,
replacing `<petango-auth-key>` with the actual auth key
([email me](mailto:me@timmcca.be) if you need it):

    behind-the-rescue:
        petango:
            auth-key: <petango auth key>

You can also configure a PostgreSQL database connection
so your data persists between restarts.
To do so, create the database `behind_the_rescue`
and add this to `application-local.yaml`:

    spring:
        datasource:
            url: jdbc:postgresql://localhost/behind_the_rescue
            username: postgres
            password: postgres
            driver-class-name: org.postgresql.Driver
    jpa:
        hibernate:
            ddl-auto: update



## Usage

Go to `http://localhost:8080/swagger-ui.html`.