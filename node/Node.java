package node;
import application.*;

import java.util.ArrayList;

public class Node {
	// import controller for some parameters in controller
	static Controller c = new Controller();
	
	//Action
	static final int MOVEUP=0;
	static final int MOVEDOWN=1;
	static final int MOVELEFT=2;
	static final int MOVERIGHT=3;

	// characteristics
	public int mazeState[][];
	public Node parentNode;
	final public ArrayList<Node> childNodeArr = new ArrayList<Node>();	// if childArr is empty -> end of tree
	public int Action = -1;
	public int pathCost;
	public int dept;
	// current position of Node in maze
	public int row,column;
	
	// function find the position (R,C) of this NODE in maze
	public void where() {
		switch (this.Action) {
		case MOVEUP:
			row =parentNode.row-1;
			column = parentNode.column;
			break;

		case MOVEDOWN:
			row =parentNode.row+1;
			column = parentNode.column;
			break;
		case MOVERIGHT:
			row =parentNode.row;
			column = parentNode.column+1;
			break;

		case MOVELEFT:
			row =parentNode.row;
			column = parentNode.column-1;
			break;			
		}
	}
	
	// Constructor
	public Node(Node p, int ActionConstant) {
		
		this.parentNode = p;
		this.dept=p.dept+1;
		this.Action = ActionConstant;
		this.mazeState = updateState(p,this.Action);
		where();
		this.pathCost = c.rowEnd - this.row + c.columnEnd - this.column;
				
	}
	public Node() {
		this.mazeState = new int[c.rows][c.columns];
	}
	
	// root node
	public Node(int maze[][]) {
		this.parentNode=null;
		this.dept=0;
		this.mazeState=maze;
		row = 1;// start at left corner
		column =1;
		this.pathCost = c.rowEnd - this.row + c.columnEnd - this.column;

	}
	public boolean checkGoal() {
 		// problem in Controller c
 		if(this.row==c.rowEnd&&this.column==c.columnEnd)
 			return true;
 		return false;
 	}
	
	
	// return new maze based on action of the node
	public int[][] updateState(Node parent, int actionConstant){
		// Node change to another place -> we have old position = visited 
		int newState[][] = new int[c.rows][c.columns];
		for (int i=0;i<c.rows;i++)
			for(int j=0;j<c.columns;j++)
				newState[i][j]=parent.mazeState[i][j];
		
		newState[parent.row][parent.column] = Controller.pathCode;
		// now update newMaze base on action: Node move
		
		switch (actionConstant) {
		case MOVEUP:
		
			newState[parent.row-1][parent.column]=Controller.startCode;
			return newState;

		case MOVEDOWN:

			newState[parent.row+1][parent.column]=Controller.startCode;
			return newState;

		case MOVERIGHT:

			newState[parent.row][parent.column+1]=Controller.startCode;
			return newState;

		case MOVELEFT:

			newState[parent.row][parent.column-1]=Controller.startCode;
			return newState;
		}
		
		return new int[c.rows][c.columns];
		
	};

	// find children for this node if empty -> add to childArr
	public ArrayList<Node> expand() {
		
		ArrayList<Node> arr = new ArrayList<Node>();
		if (mazeState[row+1][column]==Controller.emptyCode||mazeState[row+1][column]==Controller.endCode) {
			arr.add(new Node(this,MOVEDOWN));
		}
		
		if(mazeState[row-1][column]==Controller.emptyCode||mazeState[row-1][column]==Controller.endCode){

			arr.add(new Node(this,MOVEUP));

		}
		

		if(mazeState[row][column+1]==Controller.emptyCode||mazeState[row][column+1]==Controller.endCode) {
			arr.add(new Node(this,MOVERIGHT));

		}
		

		if(mazeState[row][column-1]==Controller.emptyCode||mazeState[row][column-1]==Controller.endCode) {

			arr.add(new Node(this,MOVELEFT));
		}
		
		return arr;
	};
	
	// for testing this class, view node state in console
	public void view() {
		System.out.println(row+","+column);
		for (int i=0;i<c.rows;i++) {
			for (int j=0;j<c.columns;j++)
				System.out.print(mazeState[i][j]+" ");
			System.out.println();

		}
		System.out.println("***************"+ this.Action+"***************************");

	}

}
