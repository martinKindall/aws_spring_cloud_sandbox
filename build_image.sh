#!/bin/sh

abort()
{
    echo >&2 '
***************
*** ABORTED ***
***************
'
    echo "An error occurred. Exiting..." >&2
    exit 1
}

trap 'abort' 0

set -e

sh ./gradlew bootJar
mkdir build/dependency
cd build/dependency
jar -xf ../libs/*.jar
cd ../..

aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 371417955885.dkr.ecr.us-east-1.amazonaws.com

docker build -t springio/dockerize-spring-boot .
docker tag springio/dockerize-spring-boot:latest public.ecr.aws/f5n7q8r5/aws-spring-param-store:latest

aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/f5n7q8r5

docker push public.ecr.aws/f5n7q8r5/aws-spring-param-store:latest

trap : 0

echo >&2 '
************
*** DONE ***
************
'