
public class Othello{

	public static void main(String[] args) {
		Menu menu = new Menu();

		int gamemode = menu.getInput();
		
		Game game = new Game();
		
		game.play(gamemode);
		
		menu.closeScanner();
	}

}
