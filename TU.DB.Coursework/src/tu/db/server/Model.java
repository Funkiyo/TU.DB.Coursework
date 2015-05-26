package tu.db.server;

import java.io.Serializable;

public class Model implements Serializable {
	public int id;
	public int company_id;
	public String name;
	
	Model(int i ,int c,String s){
		this.id = i;
		this.company_id = c;
		this.name = s;
	}
	
	public String toString(){
		return name;
	}
}
