FROM public.ecr.aws/lts/ubuntu:22.04 AS builder
LABEL maintainer="Fábio de Oliveira Sales <fabio.oliveira.sales@gmail.com>"

ARG USER_HOME_DIR="/root"
ENV LANG en_US.UTF-8

# Copia o script para o contêiner
COPY install_dependencies.sh /tmp/

# Executa o script para instalar as dependências
RUN chmod +x /tmp/install_dependencies.sh \
    && /bin/bash /tmp/install_dependencies.sh

# Adicionando o Java ao PATH
ENV JAVA_HOME "$USER_HOME_DIR/.sdkman/candidates/java/current"
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Adicionando o Maven ao PATH
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
ENV MAVEN_HOME "$USER_HOME_DIR/.sdkman/candidates/maven/current"
ENV PATH="${MAVEN_HOME}/bin:${PATH}"
ENV MAVEN_OPTS='-Xmx6g'

WORKDIR /build

#
## Copy the source code into the image for building
COPY ./pom.xml /build/pom.xml
COPY src /build/src/

### Build
RUN mvn clean package --no-transfer-progress -Dmaven.test.skip=true \
    && java -Djarmode=layertools -jar /build/target/qa.jar extract

RUN ls -la


FROM public.ecr.aws/lts/ubuntu:22.04

RUN addgroup --system spring  \
    && adduser --system spring --ingroup spring
USER spring:spring

EXPOSE 8080

# Copy the native executable into the containers
#COPY --from=builder /build/target/qa.jar  .
WORKDIR /app

COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/snapshot-dependencies/ ./
COPY --from=builder /build/application/ ./

RUN ls -la
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]