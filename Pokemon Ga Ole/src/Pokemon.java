

public class Pokemon {
	//Attributes
	public final static float SUPER_EFFECTIVE = 2f;
	public final static float NOT_VERY_EFFECTIVE = 1.5f;
	public final static float NORMAL = 1f;
	private String name;
	private String element;
	private int hp;
	private int baseAtk;
	private int baseDef;
	private boolean status;
	private boolean isFlipped;
	private int damageDealt;
	
	
	//Constructor
	public Pokemon(String name, String element, int hp, int baseAtk, int baseDef) {
		this.name = name;
		this.element = element;
		this.hp = hp;
		this.baseAtk = baseAtk;
		this.baseDef = baseDef;
		this.status = true;
		this.isFlipped = false;
	}

	//Setter & getters
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getBaseAtk() {
		return baseAtk;
	}
	
	public void setBaseAtk(int atk) {
		this.baseAtk = atk;
	}

	public int getBaseDef() {
		return baseDef;
	}
	
	public void setBaseDef(int def) {
		this.baseDef = def;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
		if(this.hp > 0) {
			setStatus(true);
		}
	}

	public String getElement() {
		return element;
	}
	
	public void setElement(String element) {
		this.element = element;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean getIsFlipped() {
		return this.isFlipped;
	}
	
	public void setIsFlipped(boolean flip) {
		this.isFlipped = flip;
	}
	
	public void checkStatus() {
		if (this.hp > 0) {
			status = true;
		}
		else {
			setHp(0);
			status = false;
		}
	}
	
	public void setDamageDealt(int dmg) {
		this.damageDealt = dmg;
	}
	
	public int getDamageDealt() {
		return this.damageDealt;
	}
	
	//Other methods
	
	
	/* attack method to be overidden by child class  */   
	public void attackVal(int keyVal, Pokemon enemyP) {
		
	}
	
	//toString
	@Override
	public String toString() {
		return String.format("Pokemon [name=%s, element=%s, hp=%s, baseAtk=%s, baseDef=%s]", name, element, hp, baseAtk,
				baseDef);
	}
}
