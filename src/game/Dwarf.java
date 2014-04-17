package game;

/**
 * 
 * @author L�szl� �d�m
 *
 */
public class Dwarf extends Enemy {
	
	int trappedValue;
	
	public Dwarf() {
		health = 50;
		trappedValue = 0;
	}
	
	/**
	T�rp l�ptet�se 3 �telemen kereszt�l
	Az utols� �telem a c�l, az utols� el�tti egy csapda
	Csapda eset�n egy k�r erej�ig beragad -> trappedValue++
	C�lba�r�s eset�n igazzal t�r vissza
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
			System.out.println("Utols�-e?:" + isFinal );
			
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
