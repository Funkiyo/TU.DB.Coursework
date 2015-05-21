package tu.db.server;

public class Company {
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
