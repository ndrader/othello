
public class Board {

	int boardArr[][] = new int[8][8];

	public Board() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i == 3 && j == 3) || (i == 4 && j == 4))
					boardArr[i][j] = 2;
				if ((i == 3 && j == 4) || (i == 4 && j == 3))
					boardArr[i][j] = 1;
			}
		}
	}

	public void printBoard() {
		int player1 = 0, player2 = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
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
	}

	public void updatePlayerMoves(int player) {
		int checkAgainst = player == 1 ? 2 : 1;

		int x, y;
		// looks for opposing piece
		// checks if a cell around it is blank
		// if the blank cell is in line with the current player update it to a
		// valid move
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardArr[i][j] == checkAgainst) {
					if (i - 1 > -1) {
						if (j - 1 > -1) {
							if (boardArr[i - 1][j - 1] == 0) {
								x = i + 1;
								y = j + 1;
								while (x < 8 && y < 8) {
									if (boardArr[x][y] == player) {
										boardArr[i - 1][j - 1] = 3;
										break;
									}
									x++;
									y++;
								}
							}
						}
						if (j + 1 < 8) {
							if (boardArr[i - 1][j + 1] == 0) {
								x = i + 1;
								y = j - 1;
								while (x < 8 && y > -1) {
									if (boardArr[x][y] == player) {
										boardArr[i - 1][j + 1] = 3;
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
							while (x < 8) {
								if (boardArr[x][y] == player) {
									boardArr[i - 1][j] = 3;
									break;
								}
								x++;
							}
						}
					}
					if (i + 1 < 8) {
						if (j - 1 > -1) {
							if (boardArr[i + 1][j - 1] == 0) {
								x = i - 1;
								y = j + 1;
								while (x > -1 && y < 8) {
									if (boardArr[x][y] == player) {
										boardArr[i + 1][j - 1] = 3;
										break;
									}
									x--;
									y++;
								}
							}
						}
						if (j + 1 < 8) {
							if (boardArr[i + 1][j + 1] == 0) {
								x = i - 1;
								y = j - 1;
								while (x > -1 && y > -1) {
									if (boardArr[x][y] == player) {
										boardArr[i + 1][j + 1] = 3;
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
								}
								x--;
							}
						}
					}
					if (j - 1 > -1) {
						if (boardArr[i][j - 1] == 0) {
							x = i;
							y = j + 1;
							while (y < 8) {
								if (boardArr[x][y] == player) {
									boardArr[i][j - 1] = 3;
									break;
								}
								y++;
							}
						}
					}
					if (j + 1 < 8) {
						if (boardArr[i][j + 1] == 0) {
							x = i;
							y = j - 1;
							while (y > -1) {
								if (boardArr[x][y] == player) {
									boardArr[i][j + 1] = 3;
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
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardArr[i][j] == 3)
					boardArr[i][j] = 0;
			}
		}
	}

	public boolean availableMove() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardArr[i][j] == 3)
					return true;
			}
		}
		return false;
	}

	public boolean emptyCell() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardArr[i][j] == 0)
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
		while (x < 8 && y < 8) {
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
		while (x < 8) {
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
		while (x < 8 && y > -1) {
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
		while (x > -1 && y < 8) {
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
		while (y < 8) {
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
}
