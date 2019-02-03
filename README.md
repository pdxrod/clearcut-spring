# clearcut-spring

### This is what clearcut would look like with Spring

[Clearcut](https://github.com/pdxrod/clearcut) is a dependency-injection framework I wrote to avoid
Spring XML configuration files. But subsequently, Spring has evolved, and no longer needs them.

Clearcut-spring is combination of

[Building a Hypermedia-Driven RESTful Web Service](https://spring.io/guides/gs/rest-hateoas/)

[Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

and

[Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)

from samples on the [spring.io](https://spring.io/) website.

You need MySQL. Get it from [dev.mysql.com/downloads/](https://dev.mysql.com/downloads/) if you don't have it.

Run the following command from the command-line

`mysql -u root < src/main/resources/schema-all.sql`

(You don't have to give root as the user, and you may need a password, in which case add `-p`

`mysql -u USERNAME -p < src/main/resources/schema-all.sql`

and MySQL will ask you for a password).

You need the Java JDK. See https://java.com/en/download/faq/develop.xml.

You also need to have installed Gradle from https://gradle.org/ -

  `which gradle`

  (e.g.) /usr/local/bin/gradle

  `gradle -v`

  Gradle 4.6 - or above

In this folder,

`./gradlew bootRun`

In the middle of a lot of logging messages, you should see
`Converting (firstName: Justin, lastName: Penrose-Smythe) into (firstName: JUSTIN, lastName: PENROSE-SMYTHE)`
and so on - the 'name processor' just makes names upper case and inserts them into a database.

Then navigate to http://localhost:8080/greeting?name=Justin in a browser, and you
should see some JSON like this:

    `{"content":"Hello, Jim!","_links":{"self":{"href":"http://localhost:8080/greeting?name=Justin"}}}`

You can
`curl http://localhost:8080/greeting?name=Justin`
instead of using your browser - get `curl` from
[curl.haxx.se/download.html](https://curl.haxx.se/download.html).

And most important of all, `./gradlew test`.
