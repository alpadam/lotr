package game;

import java.io.IOException;

public class Program {

	public static void main(String[] args)  {
		
		Controller controller = new Controller();
		try {
			controller.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
