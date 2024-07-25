
public class ElectricPokemon extends Pokemon {

//	private int damage, keyVal;
//	private static float rate; 
	private Pokemon enemyP;
	
	/*=============== CONSTRUCTOR ===============*/
	public ElectricPokemon(String name, String element, int hp, int baseAtk, int baseDef) {
		super(name, "ELECTRIC", hp, baseAtk, baseDef);
		}
	
	//=========== PUBLIC METHOD ===========//
	   /* Get the pokemon.object
	    * @return   - pokemon object */ 
	public Pokemon getEnemyP() {
		return enemyP;
	}	
	
	/* chosen pokemon attack all enemy pokemon 
	    * update and return the calculated damage 
	    * @param keyVal      - the number of times player spammed the key (GUI feature)
	    * @param rate        - damage multiplier based on attacker and defender types
	    * @param enemyP      - enemy pokemon object
	    * @return            - calculated damage */   
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
