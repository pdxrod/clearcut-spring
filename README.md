# clearcut-spring

### This is what Clearcut would look like with Spring

[Clearcut](https://github.com/pdxrod/clearcut) is a dependency-injection framework I wrote to avoid
Spring XML configuration files. But subsequently, Spring has evolved, and no longer needs them.

clearcut-spring is combination of

[Building a Hypermedia-Driven RESTful Web Service](https://spring.io/guides/gs/rest-hateoas/)

[Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

[Consuming a RESTful Web Service](https://spring.io/guides/gs/consuming-rest/)

[Scheduling Tasks](https://spring.io/guides/gs/scheduling-tasks/)

[Validating Form Input](https://spring.io/guides/gs/validating-form-input/)

and

[Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)

from samples on the [spring.io](https://spring.io/) website.

You need MySQL. Get it from [dev.mysql.com/downloads/](https://dev.mysql.com/downloads/) if you don't have it.

Run the following command from the command-line

`mysql -u root < src/main/resources/schema-all.sql`

(You don't have to give root as the user, and you may need a password, in which case add `-p`

`mysql -u USERNAME -p < src/main/resources/schema-all.sql`

and MySQL will ask you for a password. application.properties should contain the MySQL username/password).

You need the Java JDK, 1.8 or above. See https://java.com/en/download/faq/develop.xml.

You also need to have installed Gradle from https://gradle.org/ -

  `which gradle`

  (e.g.) /usr/local/bin/gradle

  `gradle -v`

  Gradle 4.6 - or above

In this folder,

`./gradlew bootRun`

In the middle of a lot of logging messages, you should see
`Converting (firstName: Justin, lastName: Penrose) into (firstName: Justin, lastName: Penrose-Smythe)`
and so on - the 'name processor' just adds 'Smythe' to the last name and inserts it into a database.

Every 59 seconds, you'll see the time output. Just adding @EnableScheduling to Application.java and a file called
ScheduledTasks.java makes this happen. In the real world, you could schedule something useful.

Then navigate to http://localhost:8080/greeting?name=Alan in a browser, and you
should see some JSON like this:

     {"content":"Hello, Alan!","_links":{"self":{"href":"http://localhost:8080/greeting?name=Alan"}}}

You can

`curl http://localhost:8080/greeting?name=Alan`

instead of using your browser - get `curl` from
[curl.haxx.se/download.html](https://curl.haxx.se/download.html).

Also

`curl http://localhost:8080/add?name=Alan`

`curl http://localhost:8080/add?name=Chantel+Bloggs`

     {"name": "Chantel Bloggs", "saved": true}

and

`curl http://localhost:8080/all`

     [{"id":1,"lastName":"Bloggs","firstName":"Alan","name":"Alan Bloggs"},
     {"id":2,"lastName":"Bloggs","firstName":"Chantel","name":"Chantel Bloggs"}]

And most important of all, don't forget to `./gradlew test`

Or for just one test, e.g. `./gradlew test --tests clearcut.PersonTest.testPersonRepository`
