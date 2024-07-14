
public class Pokemon {
	//Attributes
	private String name;
	private String element;
	private int hp;
	private int baseAtk;
	private int baseDef;
	private boolean status;
	public final static float SUPER_EFFECTIVE = 2f;
	public final static float NOT_VERY_EFFECTIVE = 1.5f;
	public final static float NORMAL = 1f;
	
	
	//Constructor
	public Pokemon(String name, String element, int hp, int baseAtk, int baseDef, boolean status) {
		this.name = name;
		this.element = element;
		this.hp = hp;
		this.baseAtk = baseAtk;
		this.baseDef = baseDef;
		this.status = true;
	}

	//Setter & getters
	public String getName() {
		return name;
	}

	public String getElement() {
		return element;
	}
	
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getBaseAtk() {
		return baseAtk;
	}

	public int getBaseDef() {
		return baseDef;
	}

	public boolean getStatus() { //getStatus method
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	//Other methods
	public void checkStatus() {
		if (this.hp > 0) {
			status = true;
		}
		else {setHp(0);
			status = false;
		}
	}
	
	//toString
	@Override
	public String toString() {
		return String.format("Pokemon [name=%s, element=%s, hp=%s, baseAtk=%s, baseDef=%s]", name, element, hp, baseAtk,
				baseDef);
	}
}
