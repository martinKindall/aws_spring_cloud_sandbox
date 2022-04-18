sh ./gradlew bootJar
cd build/dependency
jar -xf ../libs/*.jar
cd ../..
docker build -t springio/dockerize-spring-boot .
docker tag springio/dockerize-spring-boot:latest public.ecr.aws/f5n7q8r5/aws-spring-param-store:latest

aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/f5n7q8r5

docker push public.ecr.aws/f5n7q8r5/aws-spring-param-store:latest
