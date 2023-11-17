#!/bin/bash

# Instalação das dependências
apt-get update --fix-missing && apt-get install zip curl -y

# Instalação do SDKMAN!
curl -s "https://get.sdkman.io" | bash
source $HOME/.sdkman/bin/sdkman-init.sh

# Instalação do Java e Maven
sdk install java 17.0.5-amzn
sdk install maven
