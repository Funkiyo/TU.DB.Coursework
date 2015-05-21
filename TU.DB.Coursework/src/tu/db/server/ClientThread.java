package tu.db.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClientThread extends Thread{
	private Socket s;
	private ObjectInputStream s_in;
	private ObjectOutputStream s_out;
	private Connection db_con;
	
	public ClientThread(Socket s,Connection db_con){
		this.s = s;
		this.db_con = db_con;
	}
	
	public void run(){
		try {
			s_out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
			s_out.flush();
			System.out.println("s_out inited...");
			s_in  = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
			System.out.println("s_in inited...");
			
//			s_out.flush();
//			Thread.sleep(3000);
			
//			s_in.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		try {
			//s_in= new ObjectInputStream(s.getInputStream());
			while(true){
				String action = s_in.readUTF();
				System.out.println("action is "+action);
				switch(action){
				case "get_companies":{
					ArrayList<Company> companies = getCompanies();
					int size = companies.size();
					System.out.println("size is "+ size);
					s_out.writeInt(size);
					for(int i =0;i< size;i++){
						s_out.writeObject(companies.get(i));
						s_out.flush();
					}
					break;
				}
				case "register":{
//					Record r = (Record) s_in.readObject();
//					registerCar(r);
					break;
				}
				case "display":{
					break;
				}
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e){
			
		}
		
		
		//executeClientCommand(command);
		
	}
	
	private ArrayList<Company> getCompanies(){
		ArrayList<Company> companies = new ArrayList<Company>();
		try {
			Statement st = db_con.createStatement();
			ResultSet rs = st.executeQuery("SELECT id,name FROM car_companies");
			while(rs.next()){
				
				int id = rs.getInt("id");
				String name = rs.getString("name");
				System.out.println("id "+id+" name"+ name);
				Company c = new Company(id,name);
				companies.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companies;
	}
	
	private boolean registerCar(Record r){
		try {
			Statement stmt = db_con.createStatement();
			// TODO write register sql
			String sql = "INSERT INTO register(car_num,time_entered,time_left,fine,description)";
			stmt.executeUpdate(sql);
			PreparedStatement sth = db_con.prepareStatement(sql);
//	        sth.setString(building_id);
	        sth.executeUpdate();
	        
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// TODO should return rows 
	private String[] getCarCompanies(){
		String[] car_companies = new String[]{};;
		try {
			Statement stmt = db_con.createStatement();
			String sql = "";
			stmt.execute(sql);
			stmt.getResultSet();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return car_companies;
	}
	
	public void close(){
		
	}
}