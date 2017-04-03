
public class Board {
	
	int p1, p2;
	int boardSize = 6;
	int boardArr[][] = new int[boardSize][boardSize];
	

	public Board() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if ((i == ((boardSize/2) - 1) && j == ((boardSize/2) - 1)) || (i == (boardSize/2) && j == (boardSize/2)))
					boardArr[i][j] = 2;
				if ((i == ((boardSize/2) - 1) && j == (boardSize/2)) || (i == (boardSize/2) && j == ((boardSize/2) - 1)))
					boardArr[i][j] = 1;
			}
		}
	}
	
	public Board(Board oldboard){
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				this.boardArr[i][j] = oldboard.boardArr[i][j];
			}
		}
		 
	}

	public void printBoard() {
		int player1 = 0, player2 = 0;
		System.out.print("   ");
		for(int i = 0; i < boardSize; i++){
			if(i < 10)
				System.out.print(i + " ");
			else if(i >= 10)
				System.out.print(i);
		}
		System.out.println();
		//System.out.println("   0 1 2 3 4 5 ");
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (j == 0) System.out.print(" " + i + " ");
				if (boardArr[i][j] == 0)
					System.out.print("_|");
				if (boardArr[i][j] == 1) {
					player1++;
					System.out.print("B|");
				}
				if (boardArr[i][j] == 2) {
					player2++;
					System.out.print("W|");
				}
				if (boardArr[i][j] == 3)
					System.out.print("x|");
			}
			System.out.println();
		}
		System.out.printf("Player 1: %d Player 2: %d\n", player1, player2);
		p1 = player1;
		p2 = player2;
	}

	public void updatePlayerMoves(int player) {
		int checkAgainst = player == 1 ? 2 : 1;

		int x, y;
		// looks for opposing piece
		// checks if a cell around it is blank
		// if the blank cell is in line with the current player update it to a
		// valid move
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (boardArr[i][j] == checkAgainst) {
					if (i - 1 > -1) {
						if (j - 1 > -1) {
							if (boardArr[i - 1][j - 1] == 0) {
								x = i + 1;
								y = j + 1;
								while (x < boardSize && y < boardSize) {
									if (boardArr[x][y] == player) {
										boardArr[i - 1][j - 1] = 3;
										break;
									} else if(boardArr[x][y] == 0 || boardArr[x][y] == 3){
										break;
									}
									x++;
									y++;
								}
							}
						}
						if (j + 1 < boardSize) {
							if (boardArr[i - 1][j + 1] == 0) {
								x = i + 1;
								y = j - 1;
								while (x < boardSize && y > -1) {
									if (boardArr[x][y] == player) {
										boardArr[i - 1][j + 1] = 3;
										break;
									} else if(boardArr[x][y] == 0 || boardArr[x][y] == 3){
										break;
									}
									x++;
									y--;
								}
							}
						}
						if (boardArr[i - 1][j] == 0) {
							x = i + 1;
							y = j;
							while (x < boardSize) {
								if (boardArr[x][y] == player) {
									boardArr[i - 1][j] = 3;
									break;
								} else if(boardArr[x][y] == 0 || boardArr[x][y] == 3){
									break;
								}
								x++;
							}
						}
					}
					if (i + 1 < boardSize) {
						if (j - 1 > -1) {
							if (boardArr[i + 1][j - 1] == 0) {
								x = i - 1;
								y = j + 1;
								while (x > -1 && y < boardSize) {
									if (boardArr[x][y] == player) {
										boardArr[i + 1][j - 1] = 3;
										break;
									} else if(boardArr[x][y] == 0 || boardArr[x][y] == 3){
										break;
									}
									x--;
									y++;
								}
							}
						}
						if (j + 1 < boardSize) {
							if (boardArr[i + 1][j + 1] == 0) {
								x = i - 1;
								y = j - 1;
								while (x > -1 && y > -1) {
									if (boardArr[x][y] == player) {
										boardArr[i + 1][j + 1] = 3;
										break;
									} else if(boardArr[x][y] == 0 || boardArr[x][y] == 3){
										break;
									}
									x--;
									y--;
								}
							}
						}
						if (boardArr[i + 1][j] == 0) {
							x = i - 1;
							y = j;
							while (x > -1) {
								if (boardArr[x][y] == player) {
									boardArr[i + 1][j] = 3;
									break;
								} else if(boardArr[x][y] == 0 || boardArr[x][y] == 3){
									break;
								}
								x--;
							}
						}
					}
					if (j - 1 > -1) {
						if (boardArr[i][j - 1] == 0) {
							x = i;
							y = j + 1;
							while (y < boardSize) {
								if (boardArr[x][y] == player) {
									boardArr[i][j - 1] = 3;
									break;
								} else if(boardArr[x][y] == 0 || boardArr[x][y] == 3){
									break;
								}
								y++;
							}
						}
					}
					if (j + 1 < boardSize) {
						if (boardArr[i][j + 1] == 0) {
							x = i;
							y = j - 1;
							while (y > -1) {
								if (boardArr[x][y] == player) {
									boardArr[i][j + 1] = 3;
									break;
								} else if(boardArr[x][y] == 0 || boardArr[x][y] == 3){
									break;
								}
								y--;
							}
						}
					}
				}
			}
		}
	}

	public void clearMoves() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (boardArr[i][j] == 3)
					boardArr[i][j] = 0;
			}
		}
	}

	public boolean availableMove() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (boardArr[i][j] == 3)
					return true;
			}
		}
		return false;
	}

	public boolean emptyCell() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (boardArr[i][j] == 0 || boardArr[i][j] == 3)
					return true;
			}
		}
		return false;
	}

	public boolean sendMove(int x, int y, int player) {
		if (boardArr[x][y] == 3) {
			boardArr[x][y] = player;
			flipBetween(x, y, player);
			return true;
		}
		return false;
	}

	private void flipBetween(int i, int j, int player) {
		int x, y;
		boolean shouldFlipBetween = false;
		// check from i, j
		// if same player stop iterating
		// if opponent player iterate until out of space
		// if blank stop iterating
		// if flag true iterate through again and change opponent to player

		// diagonal down right
		x = i + 1;
		y = j + 1;
		while (x < boardSize && y < boardSize) {
			if (boardArr[x][y] == player) {
				if ((x != (i + 1)) && (y != (j + 1)))
					shouldFlipBetween = true;
				break;
			} else if (boardArr[x][y] == 0 || boardArr[x][y] == 3)
				break;
			x++;
			y++;
		}
		x--;
		y--;
		while (x != i && y != j && shouldFlipBetween) {
			boardArr[x][y] = player;
			x--;
			y--;
		}

		// down
		shouldFlipBetween = false;
		x = i + 1;
		y = j;
		while (x < boardSize) {
			if (boardArr[x][y] == player) {
				if ((x != (i + 1)))
					shouldFlipBetween = true;
				break;
			} else if (boardArr[x][y] == 0 || boardArr[x][y] == 3)
				break;
			x++;
		}
		x--;
		while (x != i && shouldFlipBetween) {
			boardArr[x][y] = player;
			x--;
		}

		// diagonal down left
		shouldFlipBetween = false;
		x = i + 1;
		y = j - 1;
		while (x < boardSize && y > -1) {
			if (boardArr[x][y] == player) {
				if ((x != (i + 1)) && (y != (j - 1)))
					shouldFlipBetween = true;
				break;
			} else if (boardArr[x][y] == 0 || boardArr[x][y] == 3)
				break;
			x++;
			y--;
		}
		x--;
		y++;
		while (x != i && y != j && shouldFlipBetween) {
			boardArr[x][y] = player;
			x--;
			y++;
		}

		// diagonal up right
		shouldFlipBetween = false;
		x = i - 1;
		y = j + 1;
		while (x > -1 && y < boardSize) {
			if (boardArr[x][y] == player) {
				if ((x != (i - 1)) && (y != (j + 1)))
					shouldFlipBetween = true;
				break;
			} else if (boardArr[x][y] == 0 || boardArr[x][y] == 3)
				break;
			x--;
			y++;
		}
		x++;
		y--;
		while (x != i && y != j && shouldFlipBetween) {
			boardArr[x][y] = player;
			x++;
			y--;
		}

		// up
		shouldFlipBetween = false;
		x = i - 1;
		y = j;
		while (x > -1) {
			if (boardArr[x][y] == player) {
				if ((x != (i - 1)))
					shouldFlipBetween = true;
				break;
			} else if (boardArr[x][y] == 0 || boardArr[x][y] == 3)
				break;
			x--;
		}
		x++;
		while (x != i && shouldFlipBetween) {
			boardArr[x][y] = player;
			x++;
		}

		// diagonal up left
		shouldFlipBetween = false;
		x = i - 1;
		y = j - 1;
		while (x > -1 && y > -1) {
			if (boardArr[x][y] == player) {
				if ((x != (i - 1)) && (y != (j - 1)))
					shouldFlipBetween = true;
				break;
			} else if (boardArr[x][y] == 0 || boardArr[x][y] == 3)
				break;
			x--;
			y--;
		}
		x++;
		y++;
		while (x != i && y != j && shouldFlipBetween) {
			boardArr[x][y] = player;
			x++;
			y++;
		}

		// left
		shouldFlipBetween = false;
		x = i;
		y = j - 1;
		while (y > -1) {
			if (boardArr[x][y] == player) {
				if (y != (j - 1))
					shouldFlipBetween = true;
				break;
			} else if (boardArr[x][y] == 0 || boardArr[x][y] == 3)
				break;
			y--;
		}
		y++;
		while (y != j && shouldFlipBetween) {
			boardArr[x][y] = player;
			y++;
		}

		// right
		shouldFlipBetween = false;
		x = i;
		y = j + 1;
		while (y < boardSize) {
			if (boardArr[x][y] == player) {
				if (y != (j + 1))
					shouldFlipBetween = true;
				break;
			} else if (boardArr[x][y] == 0 || boardArr[x][y] == 3)
				break;
			y++;
		}
		y--;
		while (y != j && shouldFlipBetween) {
			boardArr[x][y] = player;
			y--;
		}
	}

	public void declareWinner() {
		int winner = 0;
		if(p1 > p2){
			winner = 1;
		} else if(p2 > p1) {
			winner = 2;
		} else {
			winner = 0;
		}
		if(winner != 0){
			System.out.println("Player " + winner + " is the winner!");
		} else {
			System.out.println("The game was a draw!");
		}
	}

	public void updateScore() {
		int player1 = 0, player2 = 0;
		
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (boardArr[i][j] == 1) {
					player1++;
				}
				if (boardArr[i][j] == 2) {
					player2++;	
				}	
			}
		}
		p1 = player1;
		p2 = player2;
	}
	
	public int whoWon() {
		
		if(p1 > p2){
			return 1;
		} else if(p2 > p1) {
			return 2;
		}		
		return 0;
	}

	
}
