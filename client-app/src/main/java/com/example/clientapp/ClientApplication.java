package com.example.clientapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.auditlogger.service.AuditTrailService;
import org.springframework.context.annotation.Import;

// import com.example.auditlogger.service.AuditConfiguration;

@SpringBootApplication
// @Import(AuditConfiguration.class) // if AuditTrailConfig.java is present in logging library instead of this project. Also -> @Import({A.class, B.class})
public class ClientApplication implements CommandLineRunner {

    @Autowired
    private AuditTrailService auditTrailService;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
    	auditTrailService.logEvent("User logged in");
    	auditTrailService.logEvent("Data updated");
    }
}

/*

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
[32m :: Spring Boot :: [39m              [2m (v2.7.1)[0;39m

[2m2025-10-15 11:09:48.221[0;39m [32m INFO[0;39m [35m13640[0;39m [2m---[0;39m [2m[           main][0;39m [36mcom.example.clientapp.ClientApplication [0;39m [2m:[0;39m Starting ClientApplication using Java 17.0.16 on HOME-DESKTOP with PID 13640 (D:\dev\github\springboot-logging-library\client-app\target\classes started by Mohan in D:\dev\github\springboot-logging-library\client-app)
[2m2025-10-15 11:09:48.223[0;39m [32m INFO[0;39m [35m13640[0;39m [2m---[0;39m [2m[           main][0;39m [36mcom.example.clientapp.ClientApplication [0;39m [2m:[0;39m No active profile set, falling back to 1 default profile: "default"
[2m2025-10-15 11:09:48.520[0;39m [32m INFO[0;39m [35m13640[0;39m [2m---[0;39m [2m[           main][0;39m [36mc.e.a.service.AuditEventLogger          [0;39m [2m:[0;39m AUDIT EVENT: Audit Logger initialized | User: clientUser
[2m2025-10-15 11:09:48.589[0;39m [32m INFO[0;39m [35m13640[0;39m [2m---[0;39m [2m[           main][0;39m [36mcom.example.clientapp.ClientApplication [0;39m [2m:[0;39m Started ClientApplication in 0.639 seconds (JVM running for 1.206)
[2m2025-10-15 11:09:48.591[0;39m [32m INFO[0;39m [35m13640[0;39m [2m---[0;39m [2m[           main][0;39m [36mc.e.a.service.AuditEventLogger          [0;39m [2m:[0;39m AUDIT EVENT: User logged in | User: clientUser
[2m2025-10-15 11:09:48.591[0;39m [32m INFO[0;39m [35m13640[0;39m [2m---[0;39m [2m[           main][0;39m [36mc.e.a.service.AuditEventLogger          [0;39m [2m:[0;39m AUDIT EVENT: Data updated | User: clientUser
*/


/*  STEPS:
Step 1: Build and install the library
 - Open a terminal and go to your library project:
	cd /path/to/cloud-audit-logger
 - Clean, compile, and install the jar into your local Maven repository:
	mvn clean install
 - Output: Maven will compile your library and copy the jar to your local repo:
	~/.m2/repository/com/example/cloud-audit-logger/1.0.0/cloud-audit-logger-1.0.0.jar			--> thin jar
 - You’ll also see a .pom file alongside the jar.
 - At this point, your library is ready for other Maven projects.
 - Thin jar is generated
 
Step 2: Add the library as a dependency in the client app
 - Open your client Spring Boot app pom.xml and add:
	<dependency>
		<groupId>com.example</groupId>
		<artifactId>cloud-audit-logger</artifactId>
		<version>1.0.0</version>
	</dependency>
 - Maven will automatically resolve it from your local repository (~/.m2/repository) when you build the client app.
 
Step 3: Build the client Spring Boot app
 - Go to the client app folder:
	cd /path/to/client-app
 - Build the project (produces a fat jar):
	mvn clean package
 - Output jar will be in:	
	target/client-app-1.0.0.jar

Step 4: Run the client Spring Boot app
 - java -jar target/client-app-1.0.0.jar														--> fat jar
 - The client app will start.
 - Your AuditLoggerService will automatically log events using your library jar.


| Type         | Description                                                                                                                                          |
| ------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Thin Jar** | Contains **only your library’s classes**. Does **not include dependencies**. Smaller in size. Intended to be used as a dependency in other projects. |
| **Fat Jar**  | Contains **your classes + all dependencies**. Larger, executable standalone jar. Usually used for Spring Boot apps or standalone apps.               |

| Jar                              | Contents                                              | Usage                                   |
| -------------------------------- | ----------------------------------------------------- | --------------------------------------- |
| **cloud-audit-logger-1.0.0.jar** | Only library classes                                  | Thin jar, reusable in multiple projects |
| **client-app-1.0.0.jar**         | Client classes + all dependencies (including library) | Fat jar, executable Spring Boot app     |

| Command       | Output           | Where Artifact Goes              | When to Use                                |
| ------------- | ---------------- | -------------------------------- | ------------------------------------------ |
| `mvn package` | Compiled jar/war | `target/` folder only            | Just building/testing locally              |
| `mvn install` | Compiled jar/war | `target/` **+ local Maven repo** | Library to be used by other Maven projects |

Why your library is thin?
 - Your cloud-audit-logger/pom.xml only uses maven-jar-plugin.
 - No Spring Boot repackage or assembly plugin is configured.
 - Maven builds a normal jar with only your classes, so it’s a thin jar.
 
Recommended approach:
 - For libraries: always generate thin jars.
 - Client apps (like your Spring Boot app) can build a fat jar that includes all dependencies (including your library).
 
 
Suppose if my client app is using log4j2, what will happen. Logging library internally is using slf4j?
| Scenario                                                               | What happens                 | Fix / Recommendation                                      |
| ---------------------------------------------------------------------- | ---------------------------- | --------------------------------------------------------- |
| Library uses SLF4J, client uses Logback (default Spring Boot)          | Works out-of-the-box         | Nothing needed                                            |
| Library uses SLF4J, client uses Log4j2                                 | SLF4J logs may not appear    | Add `log4j-slf4j-impl` bridge, optionally exclude Logback |
| Library uses SLF4J, client uses Log4j2 and Spring Boot default logging | Conflict / multiple backends | Exclude default logging, use Log4j2 starter + bridge      |
 
*/