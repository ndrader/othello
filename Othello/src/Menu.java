import java.util.Scanner;

public class Menu {

	static Scanner sc = new Scanner(System.in);
	private int input;

	public Menu() {
		System.out.println("Othello Board Game");
		System.out.println("0 - Computer (alphaBeta) vs Computer (miniMax)");
		System.out.println("1 - Player vs Computer (alphaBeta)");
		System.out.println("2 - Player vs Player");
		System.out.print("Choose game mode: ");
		setInput(sc.nextInt());
	}

	public int getInput() {
		return input;
	}

	public void setInput(int input) {
		this.input = input;
	}

	public static void getPlayerMove(Board board, int player) {
		int x, y;
		do {
			System.out.println("Please choose an available move (X Y) or (Row# Column#): ");
			x = sc.nextInt();
			y = sc.nextInt();
		} while (!board.sendMove(x, y, player));
	}

	public void closeScanner() {
		sc.close();
	}

	public static int getColor() {
		int color;
		do {
			System.out.println("1 - Black (goes first)");
			System.out.println("2 - White");
			System.out.print("Choose your color: ");
			color = sc.nextInt();
		} while (color != 1 && color != 2);
		return color;
	}
}
