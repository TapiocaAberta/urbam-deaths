# urbam-deaths project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Before Running

Before running you should to change the property value `json.file.path` from `urbam-deaths/urbam-deaths-robot/src/main/resources/application.properties` and put your path;

To change the year, you should goes to `io.sjcdigital.ScrapMain` class and change it as follow:

```
public int run(String... args) throws Exception {
		fileService.saveJsonFile("2020", scrapper.getDeathsByYear("2020"));
		return 10;
}
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `urbam-deaths-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/urbam-deaths-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/urbam-deaths-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.
