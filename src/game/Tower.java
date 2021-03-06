package game;

import java.util.List;

/**
 * 
 * A Block-b�l lesz�rmaz� oszt�ly, a m�r fel�p�tett torony.
 * Tartalmazza a torony m�kd�s�hez sz�ks�ges fontos v�ltoz�kat �s f�ggv�nyeket.
 *
 */
public class Tower extends Block {
	public static int simpleDamage = 10;	//ez a v�ltoz� tartja sz�mon azt az �rt�ket, ami egy minden fejleszt�s n�lk�li torony alapsebz�se, �gy az k�nnyen �t�rthat�
	
	private MagicGem gem;	//a toronyba behelyezett var�zsk�
	private int damage; 	//az aktu�lis sebz�s
	private int radius;		//az a t�vols�g, amerre a torony m�g "ell�t"
	
	public boolean iSeeThem = false;	//arra van, hogy csak akkor l�j�n a torony, amikor van ellens�g a hat�t�vols�g�n bel�l
	
	public Tower() {
		
		gem = null;
		damage = simpleDamage;		//�gy k�nnyen finomhangolhat� a j�t�kegyens�ly
		radius = 2;		//a torony alapb�l 2 t�vols�gra "l�t el"
		
		blockView = new TowerView(this);
	}
	
	/**
	 * 
	 * Var�zsk� behelyez�se a toronyba. Figyeli, hogy csak megfelel� k� helyezhet� be, �s milyen v�ltoz�sokkal j�r ez a torony szempontj�b�l.
	 * Ha m�r volt k� a toronyba, akkor sikertelen a behelyez�si k�s�rlet.
	 * A visszat�r�si r�t�k jelzi, hogy siker�lt-e berakni a k�vet.
	 *
	 */
	public boolean placeGem(MagicGem magicGem){
		if (gem != null && magicGem.getType() == Type.MOVEMENT_DECREASER) {
			return false;
		} else {
			gem = magicGem;
			
			switch (magicGem.getType()) { //megvizsg�ljuk a k� t�pus�t
				case RANGE_EXPANDER:
					radius = radius + 1;  //egyel n� a l�tott t�vols�g
				break;
				case DAMAGE_INCREASER:
					damage = simpleDamage + 15; 	//n� az akut�lis sebz�s
				break;
				case SHOOTING_INCREASER:
					damage = 2 * simpleDamage;		//k�t l�ved�ket is l�
				break;

				default:
				break;
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 * Var�zsk� kiv�tele a toronyb�l, �s visszaad�sa. Ha nem volt benne k�, akkor null-lal t�r vissza.
	 *
	 */
	public MagicGem removeGem() {
		MagicGem g = null;
		
		if (gem != null) {
			try {
				g = (MagicGem) gem.clone();	//kl�nozzuk az aktu�lis var�zsk�vet, felk�sz�l�nk az esetleges kiv�telre
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			//mivel kivessz�k a k�vet, ez�rt a torony viselked�se m�dosul az eredetire
			if (gem.getType() == Type.DAMAGE_INCREASER) {
				damage = simpleDamage; 
			} else if (gem.getType() == Type.SHOOTING_INCREASER) {
				damage = simpleDamage;
			} else if (gem.getType() == Type.RANGE_EXPANDER) {
				radius--;
			}
		}
		gem = null;
		
		return g;
	}
	
	/**
	 * 
	 * Toronyban l�v� k� t�pus�nak meghat�roz�sa.
	 *
	 */
	public Type getGemType(){
		if (gem == null) {
			return null;
		}
		return gem.getType();
	}
	
	/**
	 * 
	 * L�v�s f�ggv�ny, feladata, hogy az �ltala emgkapott - a torony �ltal "l�tott" - utakr�l kiv�lasszon egy ellenfelet, �s arra t�zeljen.
	 *
	 */
	public boolean shoot(List<Road> roads) {
		boolean died = false;	//kell egy visszat�r�si �rt�k, ami megmondja, hogy a megl�tt ellenf�l meghalt-e
		
		for (int i = 0; i < roads.size(); i++) {
			Road tempRoad = roads.get(i);
			List<Enemy> enemies = tempRoad.getEnemies();		//lek�rj�k az �tr�l a rajta tart�zkod� ellenfeleket
			
			if (enemies.size() > 0) {
				Enemy tempEnemy = enemies.get(0);
				
				died = tempEnemy.damage(damage);		//tudnunk kell, hogy meghalt-e az ellenf�l
				
				if (died)
					tempRoad.removeEnemy(tempEnemy);		//ha az ellenf�l meghalt, akkor meg kell h�vnunk a megfelel� f�gg�nyt, ami elt�nteti
				
				return died;
			}
		}

		return died;
	}
	
	/**
	 * 
	 * A l�v�s speci�lis esete, amikor nem sima, hanem duplik�l� l�ved�ket l� a torony. A visszat�r�si �rt�ke az �jonnan "sz�letett" - teh�t duplik�lt - ellenf�l.
	 *
	 */
	public Enemy duplicateShoot(List<Road> roads) {
		for (int i = 0; i < roads.size(); i++) {

			Road tempRoad = roads.get(i);
			List<Enemy> enemies = tempRoad.getEnemies();		//lek�rj�k az �tr�l a rajta tart�zkod� ellenfeleket
			
			if (enemies.size() > 0) {
				Enemy tempEnemy = enemies.get(0);
				
				Enemy newEnemy = tempEnemy.duplicate();		//duplik�l�dik az ellenf�l
				
				return newEnemy;
			}
		}
		
		return null;
		
	}
	
	@Override
	public boolean isTower() {		
		return true;
	}
	
	public int getRadius(){
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public MagicGem getGem(){
		return gem;
	}
}
