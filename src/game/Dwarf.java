package game;

/**
 * 
 * @author László Ádám
 *
 */
public class Dwarf extends Enemy {
	
	int trappedValue;
	
	public Dwarf() {
		health = 50;
		trappedValue = 0;
	}
	
	/**
	Törp léptetése 3 útelemen keresztül
	Az utolsó útelem a cél, az utolsó elõtti egy csapda
	Csapda esetén egy kör erejéig beragad -> trappedValue++
	Célbaérés esetén igazzal tér vissza
	 */
	@Override
	public boolean move() {
		System.out.println("Dwarf --> move()");
		boolean isFinal = false;
		boolean isTrap = false;
		
		if(trappedValue == 0){
			
			Road r = currentRoad.getNext();
			currentRoad.removeEnemy(this);
			r.addEnemy(this);
			
			isFinal = r.isFinal();
			System.out.println("Utolsó-e?:" + isFinal );
			
			if(isFinal == false){
				isTrap = r.isTrap();
				System.out.println("Csapda-e?: " + isTrap);
				
				if(isTrap == true){
					trappedValue++;
				}
			}
			
			currentRoad = r;
			
		}else{
			
			trappedValue--;
			
			
		}
		
		System.out.println("trappedValue: " + trappedValue);
		
		return isFinal;
	}
	
	public int getTrappedValue(){
		return trappedValue;
	}

	@Override
	public Dwarf duplicate() {
		Dwarf dwarf = new Dwarf();
		int newhealth = health/2;
		dwarf.setHealth(newhealth);
		dwarf.setCurrentRoad(currentRoad);
		return dwarf;
	}

}
