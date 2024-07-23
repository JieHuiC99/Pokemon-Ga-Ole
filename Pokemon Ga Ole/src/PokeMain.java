import java.util.*;

public class PokeMain {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);		
		
		boolean running = true;
		
		
		PokeGame p = new PokeGame();
		ArrayList<Pokemon> pp = new ArrayList<Pokemon>();
		Pokemon p1 = new WaterPokemon("a", "a", 1, 1, 1);
		Pokemon p2 = new WaterPokemon("a", "a", 1, 1, 1);
		Pokemon p3 = new WaterPokemon("a", "a", 1, 1, 1);
		Collections.addAll(pp, p1,p2,p3);
		
		p.start();
		
		
		
//		p.Catch(pp);
//		
		//p.DisplayAllPlayerDisk(p.a[0]);
//		
//		int i = 0;
//		while(i < 3) {
//			try {
//				System.out.println("enter message: ");
//				input.nextInt();
//				if( i == 3 ) {
//					running = false;
//				}
//				i++;
//			}catch(Exception e) {
//				System.out.println(e.getMessage());
//				input.next();
//			}
//		}
		
//		while( running) {
//			for( int i = 0; i < 3; i++) {
//				try {
//					System.out.println("enter message: ");
//					input.nextInt();
//					if( i == 3 ) {
//						running = false;
//					}
//				}catch(Exception e) {
//					System.out.println(e.getMessage());
//					input.next();
//				}
//			}
//		}
		
//		for( int i = 0; i < 3; i++) {
//			while(i < 3) {
//				try {
//					System.out.println("enter message: ");
//					input.nextInt();
//					if( i == 3 ) {
//						running = false;
//					}
//					
//				}catch(Exception e) {
//					System.out.println(e.getMessage());
//					input.next();
//				}
//			}
//		}
		
		
//		while(running) {
//			try {
//				for( int i = 0; i < 3; i ++ ) {
//					System.out.println("enter message: ");
//					input.nextInt();
////					if( i == 3) {
////						running = false;
////					}
//				}
//				running = false;
//			}catch(Exception e) {
//				System.out.println(e.getMessage());
//				input.next();
//			}
//		}
		
		
//		KeySpam counter = new KeySpam();
//		counter.setVisible(true);
//		input.nextLine();
//		System.out.print(counter.getKeyVal());
//		
		
		
//		PokeGame g1 = new PokeGame();
//		g1.start();
		
//		Account player = new Account(0);
//		Account npc = new Account(1);
//		PokeGame p = new PokeGame();
//		
//		p.DisplayAllPlayerDisk(player);
//		p.DisplayAllPlayerDisk(npc);
//		
//		p.Attack(player.GetDisk().get(0),npc);
//		
//		System.out.println("======================");
//		
//		p.DisplayAllPlayerDisk(player);
//		p.DisplayAllPlayerDisk(npc);
		
//		Scanner input = new Scanner(System.in);
//		
		//dummy
		//Pokemon p1 = new Charmander(10000,10000,10000,4);
//		
//		boolean running = true;
//		
//		
//		while(running) {
//			try {				
//				
//				System.out.println("1. Login");
//				System.out.println("2. Exit");
//				System.out.print("Option: ");
//				int op = input.nextInt();
//				
//				if(op == 1) {
//					
//					//Login---------------------------------------------------------					
//					System.out.print("Enter PlayerID: ");
//					int playerID = input.nextInt();	
//					input.nextLine();
//					System.out.print("Enter Password: ");					
//					String playerPass = input.nextLine();
//					
//					if(a.IsLoginValid(playerID, playerPass) == true) {  //successfully logged in --------------------
//						System.out.println("Succesfully Logged In!");
//						System.out.println("Here is your inventory!");
//						System.out.println(pInventory.size());
//						
//						for( int i = 0; i < pInventory.size(); i++ ) {
//							d.PrintDisk(pInventory.get(i));
//						}
//						
//						System.out.println("Location: Volcano");
//						
//						//d.PrintDisk(p1);
//						
//					}else {  //log in invalid -------------------------------------------------
//						System.out.println("Your ID or Password is invalid!");
//					}
//					
//				}
//			}catch(Exception e) {
//				if(e instanceof InputMismatchException	) {
//					System.out.println("ERROR!: Invalid Input");	
//					input.next();
//				}else {
//					//System.out.println("ERROR!: An error occured!");
//					System.out.println(e.getMessage());
//				}
//				
//			}
//		}
		
		
		
	}
}

