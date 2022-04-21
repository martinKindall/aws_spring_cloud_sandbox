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

# Extract branch name from CODEBUILD_WEBHOOK_HEAD_REF
# E.g. refs/heads/main
regex="([^\/]+$)"
TAG=$([ "$CODEBUILD_WEBHOOK_EVENT" = "PULL_REQUEST_MERGED" ] && [ "$CODEBUILD_WEBHOOK_BASE_REF" = "refs/heads/main" ] && echo "latest" || if [[ $CODEBUILD_WEBHOOK_HEAD_REF =~ $regex ]]; then echo ${BASH_REMATCH[1]}; fi)
IMAGE_URI=public.ecr.aws/f5n7q8r5/aws-spring-param-store:$TAG
docker tag springio/dockerize-spring-boot:latest $IMAGE_URI

aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/f5n7q8r5

docker push $IMAGE_URI

trap : 0

echo >&2 '
************
*** DONE ***
************
'