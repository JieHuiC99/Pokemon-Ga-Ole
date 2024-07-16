import java.util.ArrayList;
public class PokeBack {
	
//	public final static int LOCATION_FOREST          = 1;
//	public final static int VOLCANO                  = 2;
//	public final static int ICEFALL_CAVE             = 3;
//	public final static int WATER_LABYRINTH          = 4;
	Pokemon LocationP1, LocationP2, LocationP3;
	
	Pokemon[][] grid;
	ArrayList<Pokemon> Location = new ArrayList<Pokemon>();
	
	Account player = new Account(0);
	Account npc = new Account(1);
	Displayer d = new Displayer();
	
	
	
//	private void GenerateGrid() {
//		int randomRow, randomCol;
//		
//		for( int i = 0; i < 1; i++) {
//			do {
//				randomRow = (int)(Math.random() * grid.length);
//	            randomCol = (int)(Math.random() * grid[0].length);
//			}while (grid[randomRow][randomCol] == new Charmander(1, 1000, 100, "FIRE", 1, 3));
//		}
//	}
	
	
//	private Pokemon GenerateFirePokemon() {
//		Pokemon[] p = new Pokemon[3];
//		int rand = (int)(Math.random() * 3);
//		p[0] = new FirePokemon("Charmander 1","FIRE",1000,1000,500);
//		p[1] = new FirePokemon("Charmander 2","FIRE",1000,1000,500);
//		p[2] = new FirePokemon("Charmander 3","FIRE",1000,1000,500);
//		return p[rand];
//	}
	
	
	
	public PokeBack() {
		grid = new Pokemon[3][3];
//		Location.add(LocationP1);
//		Location.add(LocationP2);
//		Location.add(LocationP3);
	}
	
	
	
	public void GeneratePokemon(int op) {
		
	}
	
	public Pokemon[][] GetGrid(){
		return this.grid;
	}
	
	public int Attack(Pokemon p1, Account p2) {
		ArrayList<Pokemon> inventory = p2.GetDisk();
		for( int i = 0; i < inventory.size(); i++) {
			p1.AttackVal(100,inventory.get(i));
		}
		return 0;
	}
	
	public void DisplayAllPlayerDisk(Account a) {
		ArrayList<Pokemon> inventory = a.GetDisk();
		for( int i = 0; i < inventory.size(); i++) {
			d.PrintDisk(inventory.get(i));
		}
	}
	
}
