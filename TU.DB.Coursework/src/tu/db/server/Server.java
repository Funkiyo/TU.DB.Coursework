package tu.db.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
	
	private static String db_con_url = "jdbc:mysql://localhost:3306/parkings";
	private static String db_con_user = "parkings";
	private static String db_con_pass = "parkings_pass";
	
	public static int port = 8036;

	public static void main(String[] args) {
		try {
			Connection db_con = DriverManager.getConnection( db_con_url, db_con_user, db_con_pass );
			
			ServerSocket ss = new ServerSocket(port);
			Socket client_soc;
			System.out.println("Listening for clients...");
			while((client_soc = ss.accept()) != null){
				ClientThread ct = new ClientThread(client_soc,db_con);
				ct.start();
				System.out.println("A client has connected.");
			}
			
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
