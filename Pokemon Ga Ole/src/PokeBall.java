
public abstract class PokeBall {
	
	public final static int POKE   = 2;
	public final static int GREAT  = 4;
	public final static int ULTRA  = 6;
	private String type;
	private int chance;
	
	public PokeBall(int chance, String type) {
		this.chance = chance;
		this.type = type;
	}
	
}
