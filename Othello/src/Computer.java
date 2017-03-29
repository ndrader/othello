public class Computer {

	long startTime;
	long timeOut = 5 * 1000;

	public Computer() {

	}

	public void getMove(Board board, int depth, int computer) {
		startTimer();
		board.clearMoves();
		board.updatePlayerMoves(computer);
		Board temp = new Board(board);
		Node node = new Node(temp);
		Node moveToMake;
		moveToMake = miniMax(node, depth, computer, true);
		if(!board.sendMove(moveToMake.row, moveToMake.column, computer)){
			
			System.out.println("Error: invalid move returned from getMove.");
			System.out.println("Attempted to make move: " + moveToMake.row + " " + moveToMake.column);
			System.exit(1);
		}
		System.out.println("Player " + computer +": Making move " + moveToMake.getRow() + ", " + moveToMake.getColumn() + " with heuristic "
				+ moveToMake.getHeuristic());
	}
	
	public void getMoveAlphaBeta(Board board, int depth, int computer) {
		startTimer();
		board.clearMoves();
		board.updatePlayerMoves(computer);
		Board temp = new Board(board);
		Node node = new Node(temp);
		Node moveToMake;
		moveToMake = alphaBeta(node, depth, computer, true, -999999999, 999999999);
		if(!board.sendMove(moveToMake.row, moveToMake.column, computer)){
			
			System.out.println("Error: invalid move returned from getMoveAlphaBeta.");
			System.out.println("Attempted to make move: " + moveToMake.row + " " + moveToMake.column);
			System.exit(1);
		}
		System.out.println("Player " + computer +": Making move " + moveToMake.getRow() + ", " + moveToMake.getColumn() + " with heuristic "
				+ moveToMake.getHeuristic());
	}

	private Node miniMax(Node node, int depth, int currentPlayer, boolean maximizing) {
		
		int otherPlayer = currentPlayer == 2 ? 1 : 2;
		Node bestvalue = new Node(node.board);	//return value in cases where node is changed
		Node testing;							//node to test with (avoid data being manipulated)
		Node newMove;							//node to compare to
		
		//update player scores for node received
		node.board.updateScore();
		
		//set heuristic value of node received, other player just played
		node.setHeuristic(heuristic(node.board, otherPlayer));

		if (depth == 0 || hasTimeLimitPassed()) {
			return node;
		}
		
		//if there are no valid moves for player, but an empty cell remains on the board
		if (!node.board.availableMove() && node.board.emptyCell()) {
			//switch board to check if other player can move
			node.board.updatePlayerMoves(otherPlayer);
			
			//if other player can move
			if(node.board.availableMove()){
				//reset board to how it was received
				node.board.updatePlayerMoves(currentPlayer);
				//max else min
				if (maximizing) {
					//imitate (-infinity)
					bestvalue.setHeuristic(-999999999);
					newMove = new Node(node.board);
					newMove.board.updatePlayerMoves(otherPlayer);
					//call minimax without adding a new move, since there was no valid move for current player
					testing = miniMax(newMove, depth - 1, otherPlayer, false);
					//compare results
					bestvalue = max(bestvalue, testing);
					//update move in case of change, keep new H
					if (bestvalue.getHeuristic() == testing.getHeuristic()) {
						bestvalue.setColumn(node.getColumn());
						bestvalue.setRow(node.getRow());
					}
					return bestvalue;
				}
				//imitate (+infinity)
				bestvalue.setHeuristic(999999999);
				newMove = new Node(node.board);
				newMove.board.updatePlayerMoves(otherPlayer);
				//call minimax without adding a new move, since there was no valid move for current player
				testing = miniMax(newMove, depth - 1, otherPlayer, true);
				//compare results
				bestvalue = max(bestvalue, testing);
				//update move in case of change, keep new H
				if (bestvalue.getHeuristic() == testing.getHeuristic()) {
					bestvalue.setColumn(node.getColumn());
					bestvalue.setRow(node.getRow());
				}
				return bestvalue;
			} else {
				//no valid move 2 turns in a row, return node with received board state
				node.board.updatePlayerMoves(currentPlayer);
				//update the heuristic since this would be an end game state
				double winner = 0;
				double whoWon = 0;
				whoWon = node.board.whoWon();
				
				if(whoWon == otherPlayer) winner = 10000;
				else if(whoWon == currentPlayer) winner = -10000;
				node.setHeuristic(node.getHeuristic() + winner);
				return node;
			}
			
			//no valid moves for either player and no empty cells 
		} else if(!node.board.availableMove()){
			//64 spaces filled so just return this node
			//the heuristic after this return has the winner result built in
			return node;
		}

		//Max
		if (maximizing) {
			//imitate (-Infinity)
			bestvalue.setHeuristic(-999999999);
			
			// for each possible valid move
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (node.board.boardArr[i][j] == 3) {
						newMove = new Node(node.board);
						//update this node with a move being played
						newMove.board.sendMove(i, j, currentPlayer);
						newMove.setColumn(j);
						newMove.setRow(i);
						//switch board to other players moves
						newMove.board.updatePlayerMoves(otherPlayer);
						//send updated node into minimax
						testing = miniMax(newMove, depth - 1, otherPlayer, false);
						//compare results
						bestvalue = max(bestvalue, testing);
						//update node if changed, but keep new H
						if (testing.getHeuristic() == bestvalue.getHeuristic()) {
							bestvalue.setColumn(j);
							bestvalue.setRow(i);
						}
					}
				}
			}
			return bestvalue;
		}
		// else minimizing
		//imitate (+Infinity)
		bestvalue.setHeuristic(999999999);
		// for each possible valid move
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (node.board.boardArr[i][j] == 3) {
					newMove = new Node(node.board);
					//update this node with a new move
					newMove.board.sendMove(i, j, currentPlayer);
					newMove.setColumn(j);
					newMove.setRow(i);
					//switch board to other players moves
					newMove.board.updatePlayerMoves(otherPlayer);
					//send new node to minimax
					testing = miniMax(newMove, depth - 1, otherPlayer, true);
					//compare results
					bestvalue = min(bestvalue, testing);
					//update node if changed but keep new H
					if (testing.getHeuristic() == bestvalue.getHeuristic()) {
						bestvalue.setColumn(j);
						bestvalue.setRow(i);
					}
				}
			}
		}
		return bestvalue;
	}
	
	private Node alphaBeta(Node node, int depth, int currentPlayer, boolean maximizing, double alpha, double beta) {

		int otherPlayer = currentPlayer == 2 ? 1 : 2;
		Node bestvalue = new Node(node.board);	//return value in cases where node is changed
		Node testing;							//node to test with (avoid data being manipulated)
		Node newMove;							//node to compare to
		
		//update player scores for node received
		node.board.updateScore();
		
		//set heuristic value of node received, other player just played
		node.setHeuristic(heuristic(node.board, otherPlayer));

		if (depth == 0 || hasTimeLimitPassed()) {
			return node;
		}
		
		//if there are no valid moves for player, but an empty cell remains on the board
		if (!node.board.availableMove() && node.board.emptyCell()) {
			//switch board to check if other player can move
			node.board.updatePlayerMoves(otherPlayer);
			
			//if other player can move
			if(node.board.availableMove()){
				//reset board to how it was received
				node.board.updatePlayerMoves(currentPlayer);
				//max else min
				if (maximizing) {
					//imitate (-infinity)
					bestvalue.setHeuristic(-999999999);
					newMove = new Node(node.board);
					newMove.board.updatePlayerMoves(otherPlayer);
					//call alphaBeta without adding a new move, since there was no valid move for current player
					testing = alphaBeta(newMove, depth - 1, otherPlayer, false, alpha, beta);
					//compare results
					bestvalue = max(bestvalue, testing);
					//update move in case of change, keep new H
					if (bestvalue.getHeuristic() == testing.getHeuristic()) {
						bestvalue.setColumn(node.getColumn());
						bestvalue.setRow(node.getRow());
					}
					return bestvalue;
				}
				//imitate (+infinity)
				bestvalue.setHeuristic(999999999);
				newMove = new Node(node.board);
				newMove.board.updatePlayerMoves(otherPlayer);
				//call alphaBeta without adding a new move, since there was no valid move for current player
				testing = alphaBeta(newMove, depth - 1, otherPlayer, true, alpha, beta);
				//compare results
				bestvalue = max(bestvalue, testing);
				//update move in case of change, keep new H
				if (bestvalue.getHeuristic() == testing.getHeuristic()) {
					bestvalue.setColumn(node.getColumn());
					bestvalue.setRow(node.getRow());
				}
				return bestvalue;
			} else {
				//no valid move 2 turns in a row, return node with received board state
				node.board.updatePlayerMoves(currentPlayer);
				//update the heuristic since this would be an end game state
				double winner = 0;
				double whoWon = 0;
				whoWon = node.board.whoWon();
				
				if(whoWon == otherPlayer) winner = 10000;
				else if(whoWon == currentPlayer) winner = -10000;
				node.setHeuristic(node.getHeuristic() + winner);
				return node;
			}
			
			//no valid moves for either player and no empty cells 
		} else if(!node.board.availableMove()){
			//64 spaces filled so just return this node
			//the heuristic after this return has the winner result built in
			return node;
		}

		//Max
		if (maximizing) {
			//imitate (-Infinity)
			bestvalue.setHeuristic(-999999999);
			
			// for each possible valid move
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (node.board.boardArr[i][j] == 3) {
						newMove = new Node(node.board);
						//update this node with a move being played
						newMove.board.sendMove(i, j, currentPlayer);
						newMove.setColumn(j);
						newMove.setRow(i);
						//switch board to other players moves
						newMove.board.updatePlayerMoves(otherPlayer);
						//send updated node into alphaBeta
						testing = alphaBeta(newMove, depth - 1, otherPlayer, false, alpha, beta);
						//compare results
						bestvalue = max(bestvalue, testing);
						alpha = max(alpha, bestvalue.getHeuristic());
						//update node if changed, but keep new H
						if (testing.getHeuristic() == bestvalue.getHeuristic()) {
							bestvalue.setColumn(j);
							bestvalue.setRow(i);
						}
						if(beta <= alpha) break;
					}
				}
				if(beta <= alpha) break;
			}
			return bestvalue;
		}
		// else minimizing
		//imitate (+Infinity)
		bestvalue.setHeuristic(999999999);
		// for each possible valid move
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (node.board.boardArr[i][j] == 3) {
					newMove = new Node(node.board);
					//update this node with a new move
					newMove.board.sendMove(i, j, currentPlayer);
					newMove.setColumn(j);
					newMove.setRow(i);
					//switch board to other players moves
					newMove.board.updatePlayerMoves(otherPlayer);
					//send new node to alphaBeta
					testing = alphaBeta(newMove, depth - 1, otherPlayer, true, alpha, beta);
					//compare results
					bestvalue = min(bestvalue, testing);
					beta = min(beta, bestvalue.getHeuristic());
					//update node if changed but keep new H
					if (testing.getHeuristic() == bestvalue.getHeuristic()) {
						bestvalue.setColumn(j);
						bestvalue.setRow(i);
					}
					if(beta <= alpha) break;
				}
			}
			if(beta <= alpha) break;
		}
		return bestvalue;
	}

	private double max(double a, double b) {
		if (a > b)
			return a;
		return b;
	}
	
	private double min(double a, double b) {
		if (a < b)
			return a;
		return b;
	}

	private Node min(Node a, Node b) {
		if (a.getHeuristic() < b.getHeuristic())
			return a;
		return b;
	}

	private Node max(Node a, Node b) {
		if (a.getHeuristic() > b.getHeuristic())
			return a;
		return b;
	}

	private boolean hasTimeLimitPassed() {
		long elapsed = System.currentTimeMillis() - startTime;
		if (elapsed >= timeOut) {
			return true;
		}
		return false;
	}

	private void startTimer() {
		startTime = System.currentTimeMillis();
	}

	private double heuristic(Board board, int player) {
		Board temp = new Board(board);
		temp.updateScore();
		double finalHeuristicValue = 0;
		double coinDiff = 0;
		// coin difference
		if (player == 1) {
			coinDiff = 100 * (temp.p1 - temp.p2) / (temp.p1 + temp.p2);
		} else {
			coinDiff = 100 * (temp.p2 - temp.p1) / (temp.p1 + temp.p2);
		}

		// spaces each player can move (mobility)
		temp.updatePlayerMoves(player);
		double mobility = 0;
		int p1Mobility = 0, p2Mobility = 0;
		if (player == 1) {
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(temp.boardArr[i][j] == 3){
						p1Mobility++;
					}
				}
			}
		} else {
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(temp.boardArr[i][j] == 3){
						p2Mobility++;
					}
				}
			}
		}
		int otherPlayer = player == 2 ? 1 : 2; 
		temp.updatePlayerMoves(otherPlayer);
		if (player == 1) {
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(temp.boardArr[i][j] == 3){
						p2Mobility++;
					}
				}
			}
		} else {
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(temp.boardArr[i][j] == 3){
						p1Mobility++;
					}
				}
			}
		}
		
		if((p1Mobility + p2Mobility) != 0){
			if (player == 1) {
				mobility = 500 * (p1Mobility - p2Mobility) / (p1Mobility + p2Mobility);
			} else {
				mobility = 500 * (p2Mobility - p1Mobility) / (p1Mobility + p2Mobility);
			}
		} 
		
		//is game over with max tokens
		double winner = 0;
		double whoWon = 0;
		if(temp.p1 + temp.p2 == 64){
			whoWon = temp.whoWon();
		}
		if(whoWon == player) winner = 10000;
		else if(whoWon == otherPlayer) winner = -10000;
		
		// corners held
		int p1Corner = 0;
		int p2Corner = 0;
		double Corners = 0;
		if(temp.boardArr[0][0] == 1) p1Corner++;
		if(temp.boardArr[0][7] == 1) p1Corner++;
		if(temp.boardArr[7][0] == 1) p1Corner++;
		if(temp.boardArr[7][7] == 1) p1Corner++;
		if(temp.boardArr[0][0] == 2) p2Corner++;
		if(temp.boardArr[0][7] == 2) p2Corner++;
		if(temp.boardArr[7][0] == 2) p2Corner++;
		if(temp.boardArr[7][7] == 2) p2Corner++;
		
		if((p1Corner + p2Corner) != 0){
			if (player == 1) {
				Corners = 1000 * (p1Corner - p2Corner) / (p1Corner + p2Corner);
			} else {
				Corners = 1000 * (p2Corner - p1Corner) / (p1Corner + p2Corner);
			}
		}
		
		finalHeuristicValue = coinDiff + mobility + winner + Corners;
		return finalHeuristicValue;

	}

}
