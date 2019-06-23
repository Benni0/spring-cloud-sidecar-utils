# Eureka Registrar for Sidecar Applications

[![License](https://img.shields.io/github/license/Benni0/spring-cloud-sidecar-utils.svg)](https://github.com/Benni0/spring-cloud-sidecar-utils/blob/master/LICENSE)

The **Eureka Registrar for Sidecar Applications** is a Spring Boot Application, which can do eureka registration for applications which are unable to register itself.

To do so, the Eureka Registrar must run in background.

## Configuration

The Configuration can specified over command line arguments

| Argument                 |                                                              |
| ------------------------ | ------------------------------------------------------------ |
| `--sidecar.appName`      | Application Name which is used for Eureka registration.      |
| `--sidecar.checkCommand` | Check Command, which is executed on bash and should return something meaningful which indicates if the service is running properly. This Command is used for health indication. |
| `--sidecar.checkResult`  | Some String, which is included in the return of the Check Command, if and only if, the Service is running properly. |
| `--eureka.serverUrl`     | The Url of the Eureka Server, including `/eureka`            |
| `--server.port`          | Port for the Health Indicator API                            |
| `--sidecar.port`         | Port where the Service is listening                          |

