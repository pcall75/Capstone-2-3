
FROM openjdk:11

RUN mkdir /superconsole

COPY target/classes/com/wywm/superconsole/TroopTrackerApplication.class/ /superconsole

WORKDIR /superconsole

CMD java Main