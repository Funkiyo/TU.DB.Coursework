package tu.db.server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JButton;












import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JTextPane;

public class ClientPanel extends JFrame {

	private JPanel contentPane;
	private JTextField CarNumber;
	private JTextField CarCompany;
	private JTextField CarModel;
	
	private ObjectInputStream din;
	private ObjectOutputStream dout;
	private Socket soc;
	static String host="localhost";
	
	private JTextPane SystemMessage;
	
	private ArrayList<Company> companies;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientPanel frame = new ClientPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientPanel() {
		try {
			soc = new Socket(host,Server.port);
			
			dout= new ObjectOutputStream(new BufferedOutputStream(soc.getOutputStream()));
			dout.flush();
			System.out.println("dout inited...");
			din = new ObjectInputStream(new BufferedInputStream(soc.getInputStream()));
			System.out.println("din inited...");
			
			
//			dout.flush();
			//din=new ObjectInputStream(soc.getInputStream());
			//dout.flush();
//			din.readInt();

			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 641, 399);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel CarNumberLabel = new JLabel("Reg. Number");
			CarNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			CarNumberLabel.setBounds(10, 11, 100, 20);
			contentPane.add(CarNumberLabel);
			
			CarNumber = new JTextField();
			CarNumber.setBounds(144, 11, 86, 20);
			contentPane.add(CarNumber);
			CarNumber.setColumns(10);
			
			JLabel CarCompanyLabel = new JLabel("Car Company");
			CarCompanyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			CarCompanyLabel.setBounds(10, 42, 100, 20);
			contentPane.add(CarCompanyLabel);
			
			CarCompany = new JTextField();
			CarCompany.setBounds(144, 42, 86, 20);
			contentPane.add(CarCompany);
			CarCompany.setColumns(10);
			
			JLabel CarModelLabel = new JLabel("Car Model");
			CarModelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			CarModelLabel.setBounds(10, 73, 100, 20);
			contentPane.add(CarModelLabel);
			
			CarModel = new JTextField();
			CarModel.setBounds(144, 73, 86, 20);
			contentPane.add(CarModel);
			CarModel.setColumns(10);
			
			JLabel ViolationTypeLabel = new JLabel("Viol. Type");
			ViolationTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			ViolationTypeLabel.setBounds(10, 104, 100, 20);
			contentPane.add(ViolationTypeLabel);
			
			final JComboBox ViolationType = new JComboBox();
			ViolationType.setModel(new DefaultComboBoxModel(new String[] {"Unpaid parking", "Forbidden stay", "Unauthorized parking"}));
			ViolationType.setBounds(144, 104, 86, 20);
			contentPane.add(ViolationType);
			
			JLabel DescriptionLabel = new JLabel("Description");
			DescriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			DescriptionLabel.setBounds(10, 135, 100, 20);
			contentPane.add(DescriptionLabel);
			
			final JTextArea Description = new JTextArea();
			Description.setBounds(144, 135, 86, 69);
			contentPane.add(Description);
			
			SystemMessage = new JTextPane();
			SystemMessage.setBounds(10, 290, 564, 59);
			contentPane.add(SystemMessage);
			
			JLabel SystemMessageLabel = new JLabel("System Message:");
			SystemMessageLabel.setBounds(10, 265, 100, 20);
			contentPane.add(SystemMessageLabel);
			
			JTextPane RowsText = new JTextPane();
			RowsText.setBounds(240, 11, 375, 268);
			contentPane.add(RowsText);
			
			JButton SubmitButton = new JButton("Submit");
			SubmitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boolean error = false;
					
					Record r = new Record();
					r.number = CarNumber.getText();
					r.company = CarCompany.getText();
					r.model = CarModel.getText();
					r.violation_type = (String) ViolationType.getSelectedItem();
					r.description = Description.getText();
					
					if(r.number.isEmpty()){
						msg("Provide the registration number of the vehicle.");
						error = true;
					}
					if(r.company.isEmpty()){
						msg("Provide the car make of the vehicle.");
						error = true;
					}
					if(r.model.isEmpty()){
						msg("Provide the car model of the vehicle.");
						error = true;
					}
					if(r.description.isEmpty()){
						msg("Provide a description of the violation.");
						error = true;
					}
					if(!error){
						try {
							dout.writeUTF("register");
							dout.flush();
//							dout.writeObject(r);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			SubmitButton.setBounds(144, 215, 86, 30);
			contentPane.add(SubmitButton);
			
			JButton ConnectButton = new JButton("Connect");
			ConnectButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						dout.writeUTF("get_companies");
						dout.flush();
						ArrayList<Company> companies = new ArrayList<Company>();
						int size;
						size = din.readInt();
						msg("Size is "+size);
						for(int i =0;i < size;i++){
							String c = din.readUTF();
							msg("Company received with name:"+c);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			ConnectButton.setBounds(10, 219, 89, 23);
			contentPane.add(ConnectButton);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void msg(String msg){
		SystemMessage.setText(msg+ "\r\n"+SystemMessage.getText());
	}
}
