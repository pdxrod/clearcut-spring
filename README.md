Follow the instructions here
https://spring.io/guides/gs/rest-hateoas/

but before that, you also need the Java JDK. See https://java.com/en/download/faq/develop.xml.

You also need to have installed Gradle from https://gradle.org/ -

  `which gradle`

  (e.g.) /usr/local/bin/gradle

  `gradle -v`

  Gradle 4.6 - or above


`cd gs-rest-hateoas/initial`

`gradlew bootRun`

Then navigate to http://localhost:8080/greeting?name=Jim in a browser, and you
should see some JSON like this:

    `{"content":"Hello, Jim!","_links":{"self":{"href":"http://localhost:8080/greeting?name=Jim"}}}`
