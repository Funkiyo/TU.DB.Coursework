package tu.db.server;

import java.awt.BorderLayout;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextPane;

public class ClientPanel extends JFrame {

	private JPanel contentPane;
	private JTextField CarNumber;
	private JTextField CarCompany;
	private JTextField CarModel;
	private JTable table;
	
	private ObjectInputStream din;
	private ObjectOutputStream dout;
	private Socket soc;
	static String host="localhost";

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
			din=new ObjectInputStream(soc.getInputStream());
			dout= new ObjectOutputStream(soc.getOutputStream());
			
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
			
			final JTextPane SystemMessage = new JTextPane();
			SystemMessage.setBounds(10, 290, 564, 59);
			contentPane.add(SystemMessage);
			
			JLabel SystemMessageLabel = new JLabel("System Message:");
			SystemMessageLabel.setBounds(10, 265, 100, 20);
			contentPane.add(SystemMessageLabel);
			
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
					
					if(r.number == ""){
						msg(SystemMessage,"Provide the registration number of the vehicle.");
						error = true;
					}
					if(r.company == ""){
						msg(SystemMessage,"Provide the car make of the vehicle.");
						error = true;
					}
					if(r.model == ""){
						msg(SystemMessage,"Provide the car model of the vehicle.");
						error = true;
					}
					if(r.description == ""){
						msg(SystemMessage,"Provide a description of the violation.");
						error = true;
					}
					if(!error){
						try {
							dout.writeObject(r);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			SubmitButton.setBounds(144, 215, 86, 30);
			contentPane.add(SubmitButton);
			
			table = new JTable();
			table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Date. Time.", "Reg. Number", "Make", "Model", "Viol. Type"
				}
			));
			table.setBounds(281, 14, 293, 231);
			contentPane.add(table);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void msg(JTextPane o,String msg){
		o.setText(msg);
	}
}
