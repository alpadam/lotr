package game;

public class Program {

	public static void main(String[] args) {
		
		int testNumber = Integer.parseInt(args[0]);
		
		//int testNumber = 9;
		
		Controller controller = new Controller(testNumber);
		int gemType;
		
		switch (testNumber) {
			
			//Teszteset #1 K� elhelyez�se toronyba
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
				
			//Teszteset #2 K� elhelyez�se akad�lyba	
			case 2:
				controller.placeGem(Type.MOVEMENT_DECREASER, true);
				break;
			
			//Teszteset #3 Var�zsk� v�s�rl�s	
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
			
			//Teszteset #4 Torony�p�t�s	
			case 4:
				controller.buildTower();
				break;
			
			//Teszteset #5 Akad�ly�p�t�s
			case 5:
				controller.buildTrap();
				break;
			
			//Teszteset #6 Ellens�g l�trehoz�sa
			case 6:
				controller.createEnemy();
				break;
			
			//Teszteset #7 Var�zsk� kiv�tele toronyb�l	
			case 7:
				controller.removeGem();
				break;
			
			//Teszteset #8 Ellens�g l�ptet�s	
			case 8:
				controller.run(testNumber);
				break;
			
			//Teszteset #9 L�v�s toronnyal
			case 9:
				controller.run(testNumber);
				break;
				
			default:
				break;
		}
	}
}
