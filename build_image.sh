sh ./gradlew bootJar
cd build/dependency
jar -xf ../libs/*.jar
cd ../..
docker build -t springio/dockerize-spring-boot .
docker tag springio/dockerize-spring-boot:latest public.ecr.aws/f5n7q8r5/aws-spring-param-store:latest
docker push public.ecr.aws/f5n7q8r5/aws-spring-param-store:latest