A combination of

[Building a Hypermedia-Driven RESTful Web Service](https://spring.io/guides/gs/rest-hateoas/)

and

[Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)

from samples on the [spring.io](https://spring.io/) website.

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
