# Etapa de construção
FROM public.ecr.aws/lts/ubuntu:22.04 AS builder
LABEL maintainer="Fábio de Oliveira Sales <fabio.oliveira.sales@gmail.com>"

ARG USER_HOME_DIR="/root"
ENV LANG en_US.UTF-8

# Copia o script para o contêiner
COPY install_dependencies.sh /tmp/

# Executa o script para instalar as dependências
RUN chmod +x /tmp/install_dependencies.sh \
    && /bin/bash /tmp/install_dependencies.sh

# Adicionando o Java e o Maven ao PATH
ENV JAVA_HOME "$USER_HOME_DIR/.sdkman/candidates/java/current"
ENV MAVEN_HOME "$USER_HOME_DIR/.sdkman/candidates/maven/current"
ENV PATH="${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${PATH}"
ENV MAVEN_OPTS='-Xmx6g'

WORKDIR /build

# Copia o código-fonte para a imagem
COPY ./pom.xml /build/pom.xml
COPY src /build/src/

# Compila o projeto
RUN mvn clean package --no-transfer-progress -Dmaven.test.skip=true \
    && java -Djarmode=layertools -jar /build/target/qa.jar extract

# Etapa final
FROM public.ecr.aws/lts/ubuntu:22.04

RUN addgroup --system spring \
    && adduser --system spring --ingroup spring
USER spring:spring

EXPOSE 8080

WORKDIR /app

# Copia os arquivos da etapa de construção
COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/snapshot-dependencies/ ./
COPY --from=builder /build/application/ ./

# Limpeza opcional (remova se não for necessário)
RUN rm -rf /build

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
