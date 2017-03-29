
public class Node {

	int row;		//position
	int column;		//position
	double heuristic;	//if this move is made
	Board board;
	
	public Node(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public Node(Board board) {
		this.board = new Board(board);
	}

	public void setHeuristic(double d){
		this.heuristic = d;
	}
	
	public double getHeuristic(){
		return heuristic;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Board getBoard() {
		return board;
	}
}
