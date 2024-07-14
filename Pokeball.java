public class PokeBall {
    public final static int POKE = 2;
    public final static int GREAT = 4;
    public final static int ULTRA = 6;
    
    private String type;
    private int chance;
    private int numOfChance;

    public PokeBall(int chance, String type) {
        this.chance = chance;
        this.type = type;
    }

    public int getChance() {
        return chance;
    }

    public String getType() {
        return type;
    }
}
