@echo off
REM Compile Main.java to target/classes
javac -cp "src/main/resources/sqlite-jdbc.jar;src/main/java" -d target/classes src\main\java\com\hospital\app\Main.java

REM Run Clinic Management App with native access enabled from target/classes
java --enable-native-access=ALL-UNNAMED -cp "src/main/resources/sqlite-jdbc.jar;target/classes" com.clinic.app.Main
