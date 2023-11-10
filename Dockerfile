FROM ghcr.io/graalvm/native-image:ol8-java17-22 AS builder

# Definindo a variável de ambiente LANG
ENV LANG en_US.UTF-8

# Instalando tar, gzip, e find para extrair os binários do Maven e outras utilidades do sistema
RUN microdnf update \
 && microdnf install --nodocs \
    unzip \
    zip \
    tar \
    gzip \
    findutils \
 && microdnf clean all \
 && rm -rf /var/cache/yum

# Mudando para o shell Bash para execução interativa
SHELL ["/bin/bash", "-c"]


ARG USER_HOME_DIR="/root"

# Baixando e instalando o SDKMAN!
RUN curl -s "https://get.sdkman.io" | bash

# Instalando o Maven usando o SDKMAN!
RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && sdk install maven

# Limpando o cache do YUM
RUN rm -rf /var/cache/yum

ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
ENV MAVEN_HOME "$USER_HOME_DIR/.sdkman/candidates/maven/current"
ENV PATH="${MAVEN_HOME}/bin:${PATH}"
ENV MAVEN_OPTS='-Xmx10g'

# Set the working directory to /home/app
WORKDIR /build

# Copy the source code into the image for building
COPY . /build


# Build
RUN mvn clean package -Pnative --no-transfer-progress -Dmaven.test.skip=true && java -Djarmode=layertools -jar /build/target/qa.jar extract


#FROM docker.io/oraclelinux:8-slim
FROM public.ecr.aws/lts/ubuntu:22.04

RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

EXPOSE 8080

# Copy the native executable into the containers
COPY --from=builder /build/target/qa  .
ENTRYPOINT ["/qa"]