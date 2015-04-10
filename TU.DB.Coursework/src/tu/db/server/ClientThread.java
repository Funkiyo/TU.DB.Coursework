package tu.db.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientThread extends Thread{
	private Socket s;
	private DataInputStream s_in;
	private DataOutputStream s_out;
	private Connection db_con;
	
	public ClientThread(Socket s,Connection db_con){
		this.s = s;
		this.db_con = db_con;
		try {
			s_in=new DataInputStream(s.getInputStream());
			s_out=new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		
	}
	
	private ClientInput getClientInput(){
		ClientInput data = new ClientInput();
		try {
			// TODO getting data from user
			data.car_num = s_in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	private boolean registerCar(ClientInput data){
		try {
			Statement stmt = db_con.createStatement();
			// TODO write register sql
			String sql = "INSERT INTO register(time_entered)";
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}