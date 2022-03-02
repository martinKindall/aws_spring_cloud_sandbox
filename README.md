# AWS Spring Cloud

Just testing out some features of the https://awspring.io/

- Parameter Store

And also trying [micrometer](https://micrometer.io/) for pushing metrics into Cloudwatch. 

## How to unpack the JAR for containerization

First, build the project and generate the fat JAR in build/libs (just run gradle).

Then execute:
````bash
cd build/dependency
jar -xf ../libs/*.jar
````

This will separate the fat JAR into BOOT-INF and META-INF.
In order to build the docker image:

````bash
docker build -t <image_repo/image_name> .
````

The previous steps can be done automatically just executing
````bash
mkdir build/dependency
sh build_image.sh
````

Beware! This assumes you have an ECR repository with a public image named __public.ecr.aws/f5n7q8r5/aws-spring-param-store__. 
Feel free to push the image to another repo like Docker Hub and to name it however you want.

## Testing the image in a EC2 instance

A plain EC2 instance with Amazon Linux 2 is enough to test this.
Just install docker [in the EC2](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/docker-basics.html).
Then pull the image and run a container.

### Instance Profile Authentication

As we are not providing the credentials neither in the environment nor as java system properties, the [AWS Credentials Provider Chain](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html) is going to keep
looking for credentials in other levels. In this example we are using the Instance profile credentials, which under my perspective
are the safest ones.

__Do not forget__ to assign the appropiate IAM role to the EC2 for reading from the Parameter Store and for pushing metrics into CloudWatch.
For this basic example the __AmazonSSMReadOnlyAccess__ plus the __CloudWatchAgentServerPolicy__ roles are going to be enough, although you will want
 to apply in real life apps the _least privilege permissions_ principle, in case the former policies are too permisive.