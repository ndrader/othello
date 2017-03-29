
public class Game {

	Board board = new Board();
	int depth = 20;
	
	public Game() {
		//nothing to init
	}

	public void play(int gamemode) {
		//Based on input choose game mode
		if(gamemode == 1) singleplayer();
		else if(gamemode == 2) twoplayer();
		else if(gamemode == 0) noplayer();
		else return;
	}

	private void noplayer() {
		Computer ai = new Computer();
		Computer otherAI = new Computer();
		int turn = 1;
		int deadlock = 0;
		System.out.println("Player 1's turn.");
		while(board.emptyCell() && deadlock != 2){
			deadlock++;
			
			board.clearMoves();
			if(turn == 1) { 
				board.updatePlayerMoves(1);
				if(board.availableMove()){
					deadlock = 0;
					board.printBoard();
					otherAI.getMove(board, depth, 1);
				}
			} else {
				board.updatePlayerMoves(2);
				if(board.availableMove()){
					deadlock = 0;
					board.printBoard();
					ai.getMoveAlphaBeta(board, depth, 2);
				}
			}
			if(turn == 1 && !board.emptyCell()) break;
			turn = turn == 1 ? 2 : 1;
			System.out.println("Player " + turn + "'s turn.");
		}
		
		board.clearMoves();
		board.printBoard();
		board.declareWinner();
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
				Menu.getPlayerMove(board,1);
			}
			if(!board.emptyCell()) break;
			board.clearMoves();
			board.updatePlayerMoves(2);
			
			//Player 2 input
			if(board.availableMove()){
				deadlock = false;
				board.printBoard();
				Menu.getPlayerMove(board,2);
			}
		}
		board.clearMoves();
		board.printBoard();
		board.declareWinner();
	}

	private void singleplayer() {
		Computer ai = new Computer();
		int player = Menu.getColor();
		int computer = player == 1 ? 2 : 1;
		int turn = 1;
		int deadlock = 0;
		
		while(board.emptyCell() && deadlock != 2){
			deadlock++;
			
			board.clearMoves();
			if(turn == player) { 
				board.updatePlayerMoves(player);		
				//Player input
				if(board.availableMove()){
					deadlock = 0;
					board.printBoard();
					Menu.getPlayerMove(board, player);
				}
			} else {
				board.updatePlayerMoves(computer);
				if(board.availableMove()){
					deadlock = 0;
					board.printBoard();
					ai.getMove(board, depth, computer);
				}
			}
			if(turn == 1 && !board.emptyCell()) break;
			turn = turn == 1 ? 2 : 1;
		}
		
		board.clearMoves();
		board.printBoard();
		board.declareWinner();
	}
}
