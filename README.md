# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

## Server Design Diagram
[Chess Server Design](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=C4S2BsFMAIGEAtIGcnQOIEMC2MDKkAnAN0OgBFkQBzAO2gFkMAHAKAwGNgB7AucESDWAsmGAqHYhRQ6PmKERYiVIwyAEqoAmUAovEhJ04LMJEDkPcqPkMwDAEF27ZEhabbGAEYYkMTZ5ZBYAIATyRRSRoqaAAGADoAThYWKgIuAFcmaABiGgwiEL5IMWgAbTIAUQAZCoAVCoBdaAB6fxZYfiDoAFoAPhN5AgAuMsqa+qbWgLkSXj7oDRptQhHy6rrGlrbF5bn+mfMR9igxAAoAShYD5x7+sg9HZxQjk4ILlnu7R5c+-xGAJUgWC4JGgGHA4Gg7jsbk8PT6nwcThcQw+D2RKG6fWukFROKxvR2OlRRMIBJmKyupjJfQ6AiEIwATDEYoElslUhksgRIJpoICqCAkMBSKUAAoAeVwtUm6V8ujpXXmFOGZUl0tl8oAOjQAN4AIjlhDyOH1Q310H1ABpLaIUAB3HiaM0W62WoEYEDgF2WgC+VMGtwWWmJ0ANRoIJsgPrd+rtSEdBGd5stNv1Hq9Mf9pL2AzMziOPNskAAcpB7QBVeXvTxFgDW0AAQhg+YCAI7pZDCHHw-o5kYAFhZOoNOBQGCo0ZG+oqBDSqu8fJ5Ha7+uzIdIBLzlJVvb49OAg+Hev1Y6QE6nltn85Gi+gy87wrXbM0AfzMHmiO+z2gk+AVcIU4IyjS4vwxVB5j+aAADEQCWaAAIIREWFrYoG2A7AYDsOtBDRL5wL3HERkQ5Cey3fsWBzQjqWGN9N3mRUGWgIcAGYR1PFwLzNK85x4EZwSLTRCmwwRn0EV8wKeCDlVMQ5oBodIITfcwg0klFoHYIsRTLStq1A9EpKDKCSI8ZSbk-Az1M04oRXsdJgHgd41JQIzPBGOyHNI2TzPmftoA8+BASQRThCorcVXc+z4C8wMt0Yw9oGZGJ2Iw00U1jDAotqLgcJoLMXw5NJMmgHhVEnaAqi4QU6HFKUZRaXwUBALgaHaToZBkwZVnVermoapBmpoFL5SjGM03jRNk1df1d18jdVXDEbMLG20fATJ18rC-ZvNxaBwCquDEPeMi7ksn8-yO1LIH0-DDMgtyYLgvkTJhVCMHQvIop4EAAC9eTwpE7s6988R2vcKLC4Gdxovd4qPABGdizy46drz46B0k+hzvr+51-XEgHv2k7biDkl6MDMj9TtuqytMgAKnLOiD+iggKYvfcH5pGSrqqCkLKPm6iurDQ0lrS1000yhzsty-LZoY9qEqS4bjWW9LJaynLRPS-H2RSIqslSSBBAqqqMmMNZxk2PqXEGtqDyDCLRnWCYGttlqdSl+AZdw2a+y5sFNdygWlh0R2dpGcT8CalrTi9n2aEuE6bBpn9ZhAAAzEIE7joPBBuwGXFckZYPgtnTLej6vZx-7nOJvNDkpznQ8pSGSdmUHYoVg8EaRzjJ24mdeNVTHq4IX7eTE9k66FkH-Ki9mVIs1OkBGTRICgWyotz6WtcTwmCPugEgRBGBy5hMi5pb2i2+3Wj5f6OHEpZAr9a5aAmHSAgmCgCqhWATAY4yhoDqJMKgmF7ZKnbisYBoCWjgJwJ7POrU-bBmvvxZBIddjh1JgWPa-9AHIB3t7PeScwbL0LudSAwAGbx1IQfO6LMHqlz5OfCmlcMZY3gDXCSTNZ6N0vv7dBWCw7hRop3WYsNFa9xPMjAeqNh4jFHl9ceuMp68JXvwvBbCm4UKJiMP8hCkCM00UfR68FCHIRnmI3Bu1LGmUEWg3Y3MCGYSQHzcAoVBY2I7iLBByBuKlANP4gAkmQbiaZ7TwDAJARCo11aWk8OADgdY4lqwlpafxJZ0l+gaDNGGcVpHP2SiefxSBAnBMwmEiJlookxLSeLVMiTknsFSWLKcGT9RZJyWuPJr9OTFSSSkuAdNCFqjqmAiB8UcG+NqhqeBmEkG71yuxbpjTnyoL8pUnA2T1nrmvjMuSNByyEOIQnG0azrq6OppQ1ev5qG0OQQXImxdzGsIXhXesnCx4Tw0bcrRuJdFX2cSI+i0D74FO7kEGRo5+6XiHjeb5KjfnqIYUXHxckdHJzriMY59pTnPMPkw9ymg+T2JhNY4GclyUU0cX5WAozMIeK8QcnxMDtmQGqSMfJXdH5FOVqUqp4TuX9INtAEIG99r2mgAAKS4HBMZ4oKy9X8ZAjq4LurKsmYgrhCd2I-wwBKggsAuD7QICtLpQruI8skcC0MBoDVGpNWai1oThXQH2dgqleCABW8qaCnLoblG0jrCDOp4BcoVZDbGqSZgYh528g35zRS5MxLD56eU+WhJF2NVG1z4RigsQKhEgtvhFOicwoVMSHIjWRcLB5oxHlwnhqLKXt0xR8i+5Cbn6PuQAzCpw3WEsYVCZhT10CYWQhwu8D4uwpvrkRYtTjiSgsrRqitUie7MWPLC8c8ieKIpnZAFcT5dZ-JeYWuxk6s3vQ0qangYJwCCWEu9X25C7Wt28VDCFvL9zQu3WxOte74WNv4k+4oQloAiTyme65KdblKKYNCSABL52vIrEh4sE6cCLx8iWldZbxEbsKVupKL4gA)
