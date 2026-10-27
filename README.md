# Student Management System — Spring Boot + Java 25

A clean Maven Spring Boot starter project based on the project shown in your screenshot. The missing `org.springframework.boot` package issue is normally caused by Maven dependencies not being imported/downloaded or an incorrect `pom.xml`.

## Requirements
- JDK 25 installed
- IntelliJ IDEA (or another Java IDE)
- Internet access on the first Maven build so Maven can download dependencies

## Run in IntelliJ IDEA
1. Extract this ZIP.
2. Open the extracted `StudentManagementSystem` folder (the folder containing `pom.xml`).
3. Set **Project SDK** and **Module SDK** to JDK 25:
   `File → Project Structure → Project → SDK`.
4. Open `pom.xml`, then choose **Load Maven Changes** / **Reload All Maven Projects**.
5. Wait until Maven finishes downloading dependencies.
6. Run `StudentManagementSystemApplication.java`.

## Run from terminal
From the project root:

```bash
java -version
mvn -version
mvn clean spring-boot:run
```

If Maven is not installed globally, install Maven or use the Maven wrapper provided by your IDE.

## Test endpoints
- `http://localhost:8080/` — application welcome JSON
- `http://localhost:8080/api/health` — health status JSON

## Important
- Java is set to **25** in `pom.xml`.
- Spring Boot parent version is **3.5.6**.
- Maven downloads dependencies on the first build. The ZIP does not bundle external dependency JARs.
- If imports remain red in IntelliJ: right-click `pom.xml` → **Maven → Reload project**, then check **Settings → Build Tools → Maven → JDK for importer** is set to JDK 25.
