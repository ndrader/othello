
public class Game {

	Board board = new Board();
	
	public Game() {
		//nothing to init
	}

	public void play(int gamemode) {
		//Based on input choose game mode
		if(gamemode == 1) singleplayer();
		else if(gamemode == 2) twoplayer();
		else return;
	}

	private void twoplayer() {
		boolean deadlock = false;
		
		while(board.emptyCell() && !deadlock){
			deadlock = true;
			board.clearMoves();
			board.updatePlayerMoves(1);
			//Player 1 input
			if(board.availableMove()){
				deadlock = false;
				board.printBoard();
				Menu.getPlayerInput(board,1);
			}
			if(!board.emptyCell()) break;
			board.clearMoves();
			board.updatePlayerMoves(2);
			
			//Player 2 input
			if(board.availableMove()){
				deadlock = false;
				board.printBoard();
				Menu.getPlayerInput(board,2);
			}
		}
		board.clearMoves();
		board.printBoard();
	}

	private void singleplayer() {
		// TODO Auto-generated method stub
		
	}

}
