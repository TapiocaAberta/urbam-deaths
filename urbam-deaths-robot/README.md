# urbam-deaths project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Before Running

Before running you should to change the property value `file.path` from `urbam-deaths/urbam-deaths-robot/src/main/resources/application.properties` and put your path;

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```
./mvnw compile quarkus:dev -Dquarkus.args='2019,2020,2018 json'
```

* The first argument is a list with the year that you want to get information;
* The second argumento is the file format. You should choose beetween `json` or `csv` file;
