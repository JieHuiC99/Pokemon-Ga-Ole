
public class WaterPokemon extends Pokemon {

	int damage, atkVal;
	double rate; 
	
	public WaterPokemon(String name, String element, int hp, int baseAtk, 
			int baseDef) {
		super(name, element, hp, baseAtk, baseDef);
		element = "WATER";
		}
	
	if(enemyP instanceof FirePokemon) {
		rate = SUPER_EFFECTIVE;
	}
	else if(enemyP instanceof ElectricPokemon) {
		rate = NOT_VERY_EFFECTIVE;
	}
	else {
		rate = NORMAL;
	}
	
	public int getDamage(int atkVal, double rate) {
		int atkDmg;
		atkDmg = (atkVal + getBaseAtk()) * rate) - enemyP.getBaseDef(); ENEMY POKEMON
		return atkDmg;
	}
	//@Override
	public void AttackVal2(int atkVal, Pokemon enemyP) {
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
}
