Follow the instructions here
https://spring.io/guides/gs/rest-hateoas/

but before that, you need to have installed gradle from https://gradle.org/ -

  `which gradle`

  - /usr/local/bin/gradle

  `gradle -v`

  - Gradle 4.7

You also need the Java JDK. See https://java.com/en/download/faq/develop.xml.

`cd gs-rest-hateoas/initial`

`gradlew bootRun`

- then navigate to http://localhost:8080/greeting?name=Jim in a browser, and you
should see some JSON like this:

    {"content":"Hello, Jim!","_links":{"self":{"href":"http://localhost:8080/greeting?name=Jim"}}}
