package game;

public class Program {

	public static void main(String[] args) {
		
		int testNumber = Integer.parseInt(args[0]);
		
		//int testNumber = 9;
		
		Controller controller = new Controller(testNumber);
		int gemType;
		
		switch (testNumber) {
			
			//Teszteset #1 Kö elhelyezése toronyba
			case 1:
				gemType = Integer.parseInt(args[1]);
				
				switch (gemType) {
					case 1:
						controller.placeGem(Type.RANGE_EXPANDER, false);
						break;
						
					case 2:
						controller.placeGem(Type.DAMAGE_INCREASER, false);
						break;
						
					case 3:
						controller.placeGem(Type.SHOOTING_INCREASER, false);
						break;
						
					default:
						break;
				}
				break;
				
			//Teszteset #2 Kõ elhelyezése akadályba	
			case 2:
				controller.placeGem(Type.MOVEMENT_DECREASER, true);
				break;
			
			//Teszteset #3 Varázskõ vásárlás	
			case 3:
				gemType = Integer.parseInt(args[1]);
				
				switch (gemType) {
					case 1:
						controller.buyGem(Type.RANGE_EXPANDER);
						break;
						
					case 2:
						controller.buyGem(Type.DAMAGE_INCREASER);
						break;
						
					case 3:
						controller.buyGem(Type.SHOOTING_INCREASER);
						break;
						
					case 4:
						controller.buyGem(Type.MOVEMENT_DECREASER);
						break;
						
					default:
						break;
				}
				break;
			
			//Teszteset #4 Toronyépítés	
			case 4:
				controller.buildTower();
				break;
			
			//Teszteset #5 Akadályépítés
			case 5:
				controller.buildTrap();
				break;
			
			//Teszteset #6 Ellenség létrehozása
			case 6:
				controller.createEnemy();
				break;
			
			//Teszteset #7 Varázskõ kivétele toronyból	
			case 7:
				controller.removeGem();
				break;
			
			//Teszteset #8 Ellenség léptetés	
			case 8:
				controller.run(testNumber);
				break;
			
			//Teszteset #9 Lövés toronnyal
			case 9:
				controller.run(testNumber);
				break;
				
			default:
				break;
		}
	}
}
