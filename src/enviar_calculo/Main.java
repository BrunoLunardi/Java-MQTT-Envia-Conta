package enviar_calculo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;



public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					////////////////////
					////////////////////Recebe o resultado/////////////////////////
					////////////////////
					//Local para onde será enviado a mensagem (broker)
					/*
					MqttClient client=new MqttClient("tcp://localhost:1883", 
							MqttClient.generateClientId());
							*/
					MqttClient client=new MqttClient("tcp://localhost:1883", 
							MqttClient.generateClientId());					
					/*Utilização da interface MqttCallback, que Permite que um 
					 * aplicativo seja notificado 
					 * quando ocorrerem eventos assíncronos relacionados ao cliente*/
					client.setCallback(new Subscriber());
					//Cria conexão com o servidor
					client.connect();
					//subscribe no tópico para receber os valores para realização da conta
					client.subscribe("enviar_resultado");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Valor 1:");
		lblNewLabel.setBounds(28, 26, 66, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Operador:");
		lblNewLabel_1.setBounds(159, 26, 114, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Valor 2:");
		lblNewLabel_2.setBounds(329, 26, 66, 15);
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(28, 69, 83, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(329, 69, 83, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(162, 66, 111, 24);
		comboBox.addItem("+");
		comboBox.addItem("-");
		comboBox.addItem("/");
		comboBox.addItem("*");
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("Enviar Calculo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//chamada para validar campos para aceitarem apenas número
				if( ValidarCampoNumerico(textField) && ValidarCampoNumerico(textField_1) ) { 
					System.out.println(textField.getText() + " " + 
							comboBox.getSelectedIndex() + " " + 
							textField_1.getText());

					//monta a string que será enviada para o broker
					String mensagem = textField.getText() + " " + 
							comboBox.getSelectedIndex() + " " + 
							textField_1.getText();
					
					//instancia o objeto para publicar as mensagens
					Publisher publisher = new Publisher( mensagem );
					//tratamento para envio de mensagem
					try {
						//chama método da classe Publihser para enviar a mensagem
						publisher.enviarMensagemConta();	
					}catch(MqttException e) {
						//imprime exceção no console
						System.out.println(e);
					}					
					
				}
			}
		});
		btnNewButton.setBounds(159, 175, 142, 25);
		contentPane.add(btnNewButton);

	}
	
	//Método para validar JTextField como número
	public boolean ValidarCampoNumerico(JTextField TextoCampo) {
		Double valor;
		if (TextoCampo.getText().length() != 0){
			try {
				valor = Double.parseDouble(TextoCampo.getText());
			}catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(null, "Digite um valor válido!" ,"Erro de valor",
						JOptionPane.INFORMATION_MESSAGE);
				TextoCampo.grabFocus();
				return false;
			}
		}else {
			JOptionPane.showMessageDialog(null, "Digite um número" ,"Erro de valor",
					JOptionPane.INFORMATION_MESSAGE);
			TextoCampo.grabFocus();			
			return false;
		}
		return true;
	}	
	
	
}
