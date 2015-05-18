package tu.db.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientThread extends Thread{
	private Socket s;
	private ObjectInputStream s_in;
	private ObjectOutputStream s_out;
	private Connection db_con;
	
	public ClientThread(Socket s,Connection db_con){
		this.s = s;
		this.db_con = db_con;
		try {
			s_in=new ObjectInputStream(s.getInputStream());
			s_out=new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			try {
				Record r = (Record) s_in.readObject();
				registerCar(r);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//executeClientCommand(command);
		
	}
	
	private boolean registerCar(Record r){
		try {
			Statement stmt = db_con.createStatement();
			// TODO write register sql
			String sql = "INSERT INTO register(time_entered)";
			stmt.executeUpdate(sql);
			PreparedStatement sth = db_con.prepareStatement(sql);
	        sth.setString(building_id);
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
}