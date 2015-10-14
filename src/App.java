package chat.app;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONObject;


public class App extends JFrame implements ActionListener {

	private JButton btnSend;
	private JTextField message;
	private TextArea txtArea;
	private JFrame jf;

	public App() {	
		super();
	}

	public void configWindows(){

		jf = new JFrame();

		JPanel panelChat = new JPanel();
		JPanel panelMsg = new JPanel();

		//Panel input text
		panelChat.add(new JLabel("Message: "));
		message = new JTextField(" ",14);
		panelChat.add(message);
		btnSend = new JButton("Send");
		panelChat.add(btnSend);

		//Panel text area
		txtArea = new TextArea(15, 45);
		panelMsg.add(txtArea);	

		//Grid panels	 
		jf.add(panelChat, BorderLayout.NORTH);
		jf.add(panelMsg, BorderLayout.CENTER); 
		
		//Action button
		btnSend.addActionListener(this);

		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
		jf.setResizable(false);
		jf.setSize(410, 350);
		jf.setVisible(true);
		jf.setTitle("App Native Message");

		/* The window starts at the middle of the screen*/
		Dimension d = new Dimension();
		jf.setLocation((int) ((d.getWidth()/2)+290), 50);
	}

	/*Action button for write to Extension*/
	public void actionPerformed(ActionEvent arg) {
        txtArea.setText(txtArea.getText()+"Sent: "+message.getText()+"\n");
		try {
			write(message.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void read(){
		try {

			DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(System.in));

			//Main thread
			while(true){

				int size = 0;

				// We load the character length in bytes
				byte[] bytes = new byte[4];
				dataInputStream.readFully(bytes, 0, 4);
				size = getInt(bytes);
				
				if (size > 0){
                    //load data
                    byte[] data = new byte[size];
                   
                    // Read the full data into the buffer
                    dataInputStream.readFully(data);
                   
                    // Loop getchar to pull in the message until we reach the total length provided.
                    String input  = new String(data, StandardCharsets.UTF_8);
                   
                    // Read text in format JSON
                    JSONObject obj = new JSONObject(input);
                    String txtInput = obj.getString("text");
                              
                    txtArea.setText(txtArea.getText()+"Received: "+txtInput+"\n");
                }

			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Write  message for extension */
	public static void write(String msg) throws IOException {
       
        String respuesta = "{\"text\":\"" + msg + "\"}";
        System.out.write(getBytes(respuesta.length()));
        System.out.write(respuesta.getBytes(Charset.forName("UTF-8")));
	}
	
	
	/* read the message size for message. */
	public static int getInt(byte[] bytes) {
		return  (bytes[3]<<24) & 0xff000000|
				(bytes[2]<<16) & 0x00ff0000|
				(bytes[1]<< 8) & 0x0000ff00|
				(bytes[0]<< 0) & 0x000000ff;
	}

	/* transform the length into the 32-bit message length. */
	public static byte[] getBytes(int length) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) ( length      & 0xFF);
		bytes[1] = (byte) ((length>>8)  & 0xFF);
		bytes[2] = (byte) ((length>>16) & 0xFF);
		bytes[3] = (byte) ((length>>24) & 0xFF);
		return bytes;
	}    

}