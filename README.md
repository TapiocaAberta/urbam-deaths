# urbam-deaths project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Before Running

Before running you should to change the property value `file.path` from `urbam-deaths/src/main/resources/application.properties` and put your path;

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```
./mvnw compile quarkus:dev -Dquarkus.args='2019,2020,2018 1,2,3,4 json'
```

* The first argument is a list with the year that you want to get information;
* The second argument is a list with the months that you want to get information. You should pass the ordinal month values 1,2,3,4 .. 12;
* The third argument is the file format. You should choose between `json` or `csv` file;

You can also, pass no args and perform a `POST` call to fill your database, also configured at `urbam-deaths/src/main/resources/application.properties`

```
./mvnw compile quarkus:dev
```

We have swagger-ui configured [access on localhost](http://localhost:8080/swagger-ui) and refer the endpoints
