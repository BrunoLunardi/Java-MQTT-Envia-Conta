# Java-MQTT-Envia-Conta

Java-MQTT-Envia-Conta


## Adicionar dependência no arquivo pom.xml (Maven), após a tag \<version>0.0.1-SNAPSHOT\</version>:

\<dependencies>
  
  \<dependency>
  
    <groupId>org.eclipse.paho\</groupId>
  
    <artifactId>org.eclipse.paho.client.mqttv3\</artifactId>
  
    <version>1.1.0\</version>
  
  \</dependency>
  
\</dependencies>    


## Instalar mosquitto linux:

Com snap:

snap install mosquitto

Com o ubuntu:

sudo apt-add-repository ppa:mosquitto-dev/mosquitto-ppa

sudo apt-get update

## Teste mosquitto:

Subscribe:

mosquitto_sub -h 127.0.0.1 -t iot_data

Publisher:

mosquitto_pub -h 127.0.0.1 -t iot_data -m "Hello world"

## Limpar cache mosquitto

mosquitto_pub -h 127.0.0.1 -t enviar_resultado -n -r -d

mosquitto_pub -h 127.0.0.1 -t enviar_conta -n -r -d
