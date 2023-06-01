FROM openjdk:11
EXPOSE 7070
ADD target/companionapp-1.0.jar companionapp-1.0.jar
ENTRYPOINT ["java","-jar","/companionapp-1.0.jar"]