
public class Displayer {
	public Displayer() {
		
	}
	
	public void PrintGrid(Pokemon[][] grid) {
		
		System.out.println("===============");
		System.out.println("|   Catch!!   |");
		System.out.println("===============");
		System.out.print("  ");
		for( int i = 1; i < grid[0].length + 1; i++) {
			System.out.print("  " + i + " " );          //column indicator
		}

		System.out.println();
		for(int i = 0; i < grid.length; i++) {      //row
			System.out.println("  -------------");
			System.out.print((i+1) + " ");
			for(int k = 0; k < grid[0].length; k++) {
				System.out.print("| ? ");
			}
			System.out.print("|" + "\n"); 
			
		}
		System.out.println("  -------------");
		System.out.println("===============");
		
	}
	
	public void PrintDisk(Pokemon p) {
		System.out.println("--------------------");
		System.out.printf ("|    %-10s    |\n",p.GetName());
		System.out.println("--------------------");
		System.out.printf ("|Star    : %-8d|\n",p.GetStar());
		System.out.printf ("|HP      : %-8d|\n",p.GetHp());
		System.out.printf ("|Attack  : %-8d|\n",p.GetBaseAtk());
		System.out.printf ("|Defense : %-8d|\n",p.GetBaseDef());
		System.out.printf ("|Element : %-8s|\n",p.GetElement());
		System.out.println("--------------------");
		
	}
	
	
}
