
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
		String currStatus = null;
		p.checkStatus();
		if(p.getStatus() == true) {
			currStatus = "ALIVE";
		}else if(p.getStatus() == false) {
			currStatus = "DEAD";
		}
		System.out.println("--------------------");
		System.out.printf ("|    %-10s    |\n",p.getName());
		System.out.println("--------------------");
		System.out.printf ("|Status  : %-8s|\n", currStatus);
		System.out.println("--------------------");
		System.out.printf ("|HP      : %-8d|\n",p.getHp());
		System.out.printf ("|Attack  : %-8d|\n",p.getBaseAtk());
		System.out.printf ("|Defense : %-8d|\n",p.getBaseDef());
		System.out.printf ("|Element : %-8s|\n",p.getElement());
		System.out.println("--------------------");
		
	}
	
	
}
