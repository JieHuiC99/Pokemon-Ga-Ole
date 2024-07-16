import java.util.ArrayList;
public class Account {
	
	private int playerID;
	private String pass;
	private final static int accountPlayerID = 0001;
	private final static String accountPass = "p";
	
	private ArrayList<Pokemon> pInventory;
	private ArrayList<Pokemon> onField;
	private int score;
	
//	public Account(int playerID, String pass) {
//		this.playerID = playerID;
//		this.pass = pass;
//	}
	
	public Account(int num) {
		pInventory = new ArrayList<Pokemon>();
		onField = new ArrayList<Pokemon>();
//		Pokemon p1 = new Pikachu(10000,10000,10000,4);
//		Pokemon p2 = new Charmander(10000,10000,10000,4);
//		p.add(p1);
//		p.add(p2);
	}
	
	public boolean IsLoginValid(int playerID, String pass) {
		if( playerID == accountPlayerID && pass.equals(accountPass)) {
			return true;
		}
		return false;
	}
	
	public ArrayList<Pokemon> GetDisk() {
		return this.pInventory;
	}
	
}
