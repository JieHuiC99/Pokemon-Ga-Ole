
public class ElectricPokemon extends Pokemon {

	public int damage, keyVal;
	public static float rate; 
	private Pokemon enemyP;
	
	public ElectricPokemon(String name, String element, int hp, int baseAtk, int baseDef) {
		super(name, "ELECTRIC", hp, baseAtk, baseDef);
		}
	
	public Pokemon getEnemyP() {
		return enemyP;
	}

	//Override setEnemyP for ElectricPokemon
//	public void setEnemyP(Pokemon enemyP) {
//		super.setEnemyP(enemyP);
//		this.enemyP = enemyP;
//		updateRate();
//	}
//	
	
	//Element effectiveness
	private void updateRate() {
		if(enemyP instanceof WaterPokemon) {
			rate = SUPER_EFFECTIVE;
	}
		else if(enemyP instanceof ElectricPokemon) {
			rate = NOT_VERY_EFFECTIVE;
	}
		else {
			rate = NORMAL;
	}}
	
	//Calculate attack damage
//	public int getDamage(int keyVal, float rate) {
//		int atkDmg;
//		atkDmg = (int)(((keyVal + getBaseAtk()) * rate) - getEnemyP().getBaseDef());
//		return atkDmg;
//	}
	
	public int getDamage(int keyVal, float rate, Pokemon enemyP) {
		int atkDmg;
		atkDmg = (int)(((keyVal + getBaseAtk()) * rate) - enemyP.getBaseDef());
		if(atkDmg < 0) {
			atkDmg = 0;
		}
		super.setDamageDealt(atkDmg);
		return atkDmg;
	}
	
	   /* Calculate the damage, and then subtract the enemy's hp based on the value
	    * @param keyVal      - the number of times player spammed the key (GUI feature)
	    * @param enemyP      - enemy pokemon object */  
	public void attackVal(int keyVal, Pokemon enemyP) {
		if (enemyP instanceof FirePokemon) {			
		    enemyP.setHp(enemyP.getHp() - getDamage(keyVal, SUPER_EFFECTIVE, enemyP));
		}
		else if (enemyP instanceof ElectricPokemon){
			enemyP.setHp(enemyP.getHp() - getDamage(keyVal, NORMAL, enemyP));
				
		}
		else if (enemyP instanceof WaterPokemon){
			enemyP.setHp(enemyP.getHp() - getDamage(keyVal, NOT_VERY_EFFECTIVE, enemyP));					
		}
	}
	
}