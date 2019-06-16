# Spring Cloud Config Builder for Sidecar Applications

The **Spring Cloud Config Builder for Sidecar Applicartions** is a Spring Boot Application, which fetches the configuration from a spring cloud configuration server and modifies configuration files and system variables accordingly.

## Configuration Files

**Configuration:**

```YAML
config:
  basepath: /etc/someapp
  files:
  	- file: mail.properties
  	  type: properties
  	  action: modify
  	  parameters:
  	  	- com.openexchange.mail.mailServer=1.2.3.4
      	- com.openexchange.mail.mailStartTls=true
   - file: test.properties
     type: properties
     action: create
     parameters:
     	- test.param=test1
     	- test2.param=test2
   - file: test2.propertis:
     type: properties
     action: remove
```

If the parameter file is not a absolute path, the basepath will be used as prefix.

### Config Actions

- create: creates a new configuration file with the specified configuration, if the file already exists, it will be deleted before creation
- modify: modifies or adds the specified parameters to the configuration file
- overwrite: same as create
- remove: deletes the specified files, does not support parameters.

### Type

Type of the configuration file:

| Type       |                                                           |
| ---------- | --------------------------------------------------------- |
| properties | File with key/value pairs                                 |
| json       | not yet implemented                                       |
| xml        | not yet implemented                                       |
| yaml       | not yet implemented                                       |
| dovecot    | not yet implemented (https://wiki.dovecot.org/ConfigFile) |



## Environment Variables

**Configuration:**

```YAML
system:
  variables:
    - variable1=value1
    - variable2=value2
```

As it is not possible to set system variables from java code, there exists a workaround for setting these variables for the current bash session. The export commands are written to a File, named `/tmp/environment.sh` which can be executed and deleted within the ENTRYPOINT script.

**Example:**

```bash
java \
    -Dspring.application.name=oxcontainer \
    -Dspring.cloud.config.uri=$CONFIGSERVER_URI \
    -Dspring.profiles.active=$PROFILE \
    -jar /tmp/configbuilder.jar
chmod u+x /tmp/environment.sh
source /tmp/environment.sh
rm /tmp/environment.sh

echo $variable1
```

The `source` command executes the script in the current shell and not in a subshell.

## Usage

The Configuration-Builder should be executed at container startup (RUN or ENTRYPOINT) before the application starts. If the Config-Server isn't available, the Configuration-Builder will try three times and waits 10 seconds between each try.

**Example:**

Dockerfile

```dockerfile
COPY configbuilder.jar /tmp/configbuilder.jar
COPY run.sh /tmp/run.sh
RUN dos2unix /tmp/run.sh #if you build on windows
RUN chmod u+x /tmp/run.sh
ENTRYPOINT [ "/tmp/run.sh" ]
```

run.sh

```bash
#!/bin/sh

java -jar /tmp/configbuilder.jar \
    -n "oxcontainer" \
    -c $CONFIGSERVER_URI \
    -p $PROFILE

# only if you want to set a system variable
chmod u+x /tmp/environment.sh
source /tmp/environment.sh
rm /tmp/environment.sh

#use configured system variable
echo $variable1

tail -f /dev/null

```

**Parameters:**

| Parameter                     |                                                              |
| ----------------------------- | ------------------------------------------------------------ |
| `-Dspring.application.name`   | Defines the application name, which is required for configuration fetching |
| `-Dspring.cloud.config.uri`   | Specifies the Uri of the configuration server e.g. http://configserver:8888 |
| `-Dspring.profiles.active`    | Sets the spring boot application profile which is required for configuration fetching |
| `-Dspring.cloud.config.label` | Specifies the label or git tag for configs fetched from git  |

Specify Parameters before `-jar`

## Additional Information

Parameter decryption may be working (it's a Spring boot Application)

If you want to use this client in another Spring Boot Application, just annotate the Application Class with `@EnableSidecarConfigClient`



## Todo

At this time only properties files are working, support for JSON, YAML and XML will be added in a future release.
