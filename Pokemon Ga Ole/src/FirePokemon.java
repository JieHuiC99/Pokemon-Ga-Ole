
public class FirePokemon extends Pokemon {

	public int damage, atkVal;
	public static float rate; 
	private Pokemon enemyP;
	
	public FirePokemon(String name, String element, int hp, int baseAtk, int baseDef) {
		super(name, "WATER", hp, baseAtk, baseDef);
		}
	
	public Pokemon getEnemyP() {
		return enemyP;
	}

	//Override setEnemyP for FirePokemon
	public void setEnemyP(Pokemon enemyP) {
		super.setEnemyP(enemyP);
		this.enemyP = enemyP;
		updateRate();
	}
	
	
	//Element effectiveness
	private void updateRate() {
		if(enemyP instanceof ElectricPokemon) {
			rate = SUPER_EFFECTIVE;
	}
		else if(enemyP instanceof FirePokemon) {
			rate = NOT_VERY_EFFECTIVE;
	}
		else {
			rate = NORMAL;
	}}
	
	//Calculate attack damage
	public int getDamage(int atkVal, float rate) {
		int atkDmg;
		atkDmg = (int)(((atkVal + getBaseAtk()) * rate) - getEnemyP().getBaseDef());
		return atkDmg;
	}
	
	//Calculate enemy's HP after attack 
	public void AttackVal(int atkVal, Pokemon enemyP) {
		if (enemyP instanceof FirePokemon) {			
		    enemyP.setHp(enemyP.getHp() - getDamage(atkVal, SUPER_EFFECTIVE));
		}
		else if (enemyP instanceof ElectricPokemon){
			enemyP.setHp(enemyP.getHp() - getDamage(atkVal, NORMAL));
				
		}
		else if (enemyP instanceof WaterPokemon){
			enemyP.setHp(enemyP.getHp() - getDamage(atkVal, NOT_VERY_EFFECTIVE));					
		}
	}
	
}
