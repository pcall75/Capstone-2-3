# we will use openjdk 17 with alpine as it is a very small linux distro
FROM openjdk:17

# copy the packaged jar file into our docker image
COPY target/TroopTracker-1.0.jar /TroopTracker.jar
COPY target/classes/dataset.xml /classes/dataset.xml

#Set the directory for executing futre commands
WORKDIR /app

# set the startup command to execute the jar
CMD ["java", "-jar", "/TroopTracker.jar"]