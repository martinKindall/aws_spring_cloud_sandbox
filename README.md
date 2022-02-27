# How to unpack the JAR for containerization

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
