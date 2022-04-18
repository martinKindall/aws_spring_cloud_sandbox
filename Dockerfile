FROM 371417955885.dkr.ecr.us-east-1.amazonaws.com/11.0.4-jre-slim:v1
RUN adduser --system --group spring
USER spring:spring
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","-Dspring.profiles.active=docker","com.codigo_morsa.docker.DockerApplication"]