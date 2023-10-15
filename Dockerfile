FROM eclipse-temurin:17
WORKDIR /workspace/app
ARG JAR_FILE
ADD build/libs/${JAR_FILE} /workspace/app/app.jar
CMD ["java","-noverify","-XX:TieredStopAtLevel=1","-jar","/workspace/app/app.jar"]

# 1. BUILD the JAR, Run the Tests
# ./gradlew clean build

# 2. JAR file for Docker
# export JAR_FILE=customer-management-0.0.1-SNAPSHOT.jar

# 3. BUILD docker image, docker images
# docker build -t enterprisesaas/customer-management .

# 4. RUN the docker image as a CONTAINER, docker ps, customer-management:latest
# docker run -d --restart=unless-stopped -p 8080:8080 enterprisesaas/customer-management:latest /bin/bash