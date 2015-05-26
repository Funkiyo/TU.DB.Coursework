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
	
	private ObjectInputStream din;
	private ObjectOutputStream dout;
	private Socket soc;
	static String host="localhost";
	
	private JTextPane SystemMessage;
	private JComboBox CarCompany;
	private JComboBox CarModel;
	
	private ArrayList<Company> companies;
	private ArrayList<Model> models;

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
			
			get_car_companies();
			
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
			
			JLabel CarModelLabel = new JLabel("Car Model");
			CarModelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			CarModelLabel.setBounds(10, 73, 100, 20);
			contentPane.add(CarModelLabel);
			
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
			
			CarCompany = new JComboBox();
			CarCompany.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					get_car_models();
				}
			});
			loadComboBoxCompanies();
			CarCompany.setBounds(144, 42, 86, 20);
			contentPane.add(CarCompany);
			
			CarModel = new JComboBox();
			CarModel.setBounds(144, 73, 86, 20);
			contentPane.add(CarModel);
			
			JButton SubmitButton = new JButton("Submit");
			SubmitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boolean error = false;
					
					Record r = new Record();
					r.number = CarNumber.getText();
					r.company = (String) CarCompany.getSelectedItem();
					r.model = (String)CarModel.getSelectedItem();
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
							dout.writeObject(r);
							dout.flush();
							msg("Car successfully registered. .. ");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			SubmitButton.setBounds(144, 215, 86, 30);
			contentPane.add(SubmitButton);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void get_car_companies() {
		try {
			dout.writeUTF("get_companies");
			dout.flush();
			this.companies = (ArrayList<Company>)din.readObject();
//			System.out.println("Companies received...AASDAS"+this.companies.size());
			
		} catch (IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void get_car_models() {
		String s = (String)CarCompany.getSelectedItem();
		int company_id = 0;
		for(Company c : companies){
			if(s.equals(c.name)){
				company_id = c.id;
				break;
			}
		}
		if(company_id == 0){
			msg("Select company.");
			return;
		}
		try{
			dout.writeUTF("get_models");
			dout.flush();
			dout.writeInt(company_id);
			dout.flush();
			this.models = (ArrayList<Model>)din.readObject();
			loadComboBoxModels();
		}catch(IOException e){
			
		}catch (ClassNotFoundException e) {
			// TODO: handle exception
		}
	}
	
	private void loadComboBoxCompanies(){
		ArrayList<String> companies_model = new ArrayList<String>();
		companies_model.add("");
		for(Company c : companies){
			companies_model.add(c.toString());
		}
		CarCompany.setModel(new DefaultComboBoxModel(companies_model.toArray(new String[companies_model.size()])));
	}
	

	
	private void loadComboBoxModels(){
		ArrayList<String> models_model = new ArrayList<String>();
		for(Model c : models){
			models_model.add(c.toString());
		}
		CarModel.setModel(new DefaultComboBoxModel(models_model.toArray(new String[models_model.size()])));
//		CarCompany.repaint();
	}

	private void msg(String msg){
		SystemMessage.setText(msg+ "\r\n"+SystemMessage.getText());
	}
}
