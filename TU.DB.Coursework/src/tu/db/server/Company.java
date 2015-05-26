package tu.db.server;

import java.io.Serializable;

public class Company implements Serializable {
	public int id;
	public String name;
	
	Company(int i ,String s){
		this.id = i;
		this.name = s;
	}
	
	public String toString(){
		return name;
	}
}
