package enviar_calculo;

//Imports para utilizar o protocolo MQTT
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class Publisher {
	
	//String que enviará a conta para o outro sistema
    String mensagemEnviaConta;
    //construtor da classe para enviar a mensagem
	public Publisher(String mensagemStatusBarragem){
		this.mensagemEnviaConta = mensagemStatusBarragem;
	}
    
	//Método para enviar a mensagem para o mosquitto
	public void enviarMensagemConta() throws MqttException {
		//Local para onde será enviado a mensagem (broker)
		MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
		//abre conexão com o broker (Mosquitto)
		client.connect();
		//objeto de envio de mensagem do broker
		MqttMessage menssagem = new MqttMessage();
			//Payload, conteudo da mensagem montagem da mensagem que será enviada ao broker
		menssagem.setPayload(mensagemEnviaConta.getBytes());
	    //Publica a mensagem, com seu tópico (para alguém se escrever neste tópico) e a mensagem montada
	    client.publish("enviar_conta", menssagem);
	    //fecha conexão com broker mosquitto
	    client.disconnect();
	}	

}
