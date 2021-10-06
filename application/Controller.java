package application;
import node.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import algorithms.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;


public class Controller implements Initializable {

	ObservableList<String> list = FXCollections.observableArrayList();
	public int blockSize= 35; // size of block
	
	public int pathingDelay = 300; // delay finding path
	
	// size of maze
	public int rows=19;
	public int columns=27;
	// Goal point
    public int rowEnd = rows-2;
    public int columnEnd = columns-2;

	private int maze[][] = new int[rows][columns]; // maze contains all 0 - wallCode
	// initialMaze for reset part
	private int initialMaze[][] = new int[rows][columns]; // maze contains all 0 - wallCode
	
	public GraphicsContext gc;
	// algorithm for searching
	Algorithms A;
	
	public final static int wallCode = 0; // walls
    public final static int pathCode = 1; // current path
    public final static int emptyCode = 2; // path can go
    public final static int visitedCode = 3; // visited path
    public final static int startCode = 4; // start point
    public final static int endCode = 5; // end point
    
    // color for each of code
	Color[] color = new Color[] {
            Color.AQUA,
            Color.BLACK,
            Color.WHITE,
            Color.PINK,
            Color.YELLOW,
            Color.RED,
        };  
	
    @FXML
    private Button resetBtn;

    @FXML
    private Canvas canvasID;
	
    @FXML
    private Button newMapBtn;

    @FXML
    private Button startBtn;

    @FXML
    private ChoiceBox<String> algorithmChoiceBox;
    // for choice Box options
    String BFS = "BFS Algorithm";
	String DFS = "DFS Algorithm";
	String GreedyBFS = "Best First Search Algorithm";
	String AStar = "A* Algorithm";
	
    void loadChoice() {
    	list.removeAll();
    	list.addAll(BFS,DFS,GreedyBFS,AStar);
    	algorithmChoiceBox.getItems().addAll(list);
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadChoice();
	}
   
    @FXML
    void startSearching(ActionEvent event) {
    	// after the NEW MAP, we would have an initial state of the maze by 2D array
    	// and now we have to make a problem-solved tree from that state
    	// use the algorithm to solve
    	
    	// loading algorithms
    	if (algorithmChoiceBox.getValue().equals(DFS)) {
    		A = new DFS();
    	}
    	else if (algorithmChoiceBox.getValue().equals(BFS)) {
    		A = new BFS();
    		pathingDelay += 200;
    	}
    	else if(algorithmChoiceBox.getValue().equals(GreedyBFS)) {
    		A = new GreedyBFS();
    	}
    	else if (algorithmChoiceBox.getValue().equals(AStar)) {
    		A = new AStar();
    	}
    	else 
    		A = null; 
    	// TO-DO ERROR not choosing algorithms
    	this.Search(A);	
}

    void Search(Algorithms A) {
    	// create initial Node
    	Node root = new Node(maze);
    	
    	// to note the path has gone but not the solution
    	int [][] visited = new int [rows][columns];
    	//start searching, use a Stack fringe (DFS)
    	A.put(root);
 		final Thread delay = new Thread(){
    		public void run(){
    		while(true) {
     			Node newNode;
     			// this node is for traversing maze
    			if (A.fringe.isEmpty()) {
     				maze = new int[rows][columns]; // Not found
     				break;
     			}
    			newNode = A.get();    			
    			// take state of the current newNode
    			maze = newNode.mazeState;

    			// note a visited point
    			visited[newNode.row][newNode.column] = Controller.visitedCode;  
    			
    			// this loop for print the visited point, which is not the current point or the path finding point
    			for (int i=0;i<rows;i++) {
    				for (int j=0;j<columns;j++) {
    					if (maze[i][j]!= startCode && maze[i][j]!=pathCode && visited[i][j]==visitedCode)
    						maze[i][j]= visitedCode;
    				}
    			}
    		
    			if (newNode.checkGoal()) {
    				break; // reach the solution
    			}

    			try {
    				Thread.sleep(pathingDelay); // finding path deday
    				// update the GUI
    				update(gc);
    			} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    			ArrayList<Node> Arr = newNode.expand();
     			for (Node temp: Arr) {
     					A.put(temp);
     			}
    		}
    		}
    	};
    	delay.start(); 
    
    }
    
    @FXML
    void resetMap(ActionEvent event) {
    	A = null;
    	for (int i=0;i<rows;i++)
    		for (int j=0;j<columns;j++)
    			maze[i][j]=initialMaze[i][j];
    	update(gc);
    }
    

    // After Map creation, I would have an 2D array maze[rows][columns]
    // makeMaze algorithm is reference from SimpleMaze 
    @FXML
    void createNewMap(ActionEvent event) throws InterruptedException {
    	
    	// reset variable
    	
    	// setting up new map with all walls
    	gc = canvasID.getGraphicsContext2D();// graphics context for drawing on the canvas
  
    	gc.setFill(color[wallCode]);
    	// fill with rectangle
    	gc.fillRect(0, 0, blockSize*columns, blockSize*rows);
    	//***********************************************************************
    	// create Maze 
		mazeCreator();
    	final Thread delay = new Thread(){
    		public void run(){
    		// run the mazeCreatator    	
    			try {
    				Thread.sleep(500); // new map delay
    				update(gc);
    			} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    	};
    	delay.start(); 
    }
 
    
    void breakWallorNot(int row, int col) {
    	// at the wall-point (row,col) consider if it can be break or not
    	// odd row: consider left, right
    	if (row%2!=0&&maze[row][col+1]==maze[row][col-1]) {
    		// if left = right code -> hold the wall else break
    	}
    	// even row: consider up, down
    	//else if () {
    	
    	//}
    	// even row: consider up, down
    }
    
    public void mazeCreator() {
    	makeMaze();
    	maze[1][1] = startCode;
    	maze[rowEnd][columnEnd] = endCode;
    	// start at odd point (1,1) and end at right bottom of maze (which is not a wall)
    	/*
    	 * 0 0 0 0 0 ... 0 0 
    	 * 0 4 0 2 0 ... 2 0
    	 * 0 0 0 0 0 ... 0 0
    	 * 0 2 0 2 0 ... 2 0
    	 * 0 0             0
    	 * .               .
    	 * .               .
    	 * 0 2           5 0 
    	 * 0 0 0 0 0 ... 0 0
    	 * emptyCode = 2
    	 * start =4, end = 5
    	 * wallCode = 0 
    	 */
    	
    	// initial maze = maze
    	for (int i=0;i<rows;i++)
    		for (int j=0;j<columns;j++)
    			initialMaze[i][j]=maze[i][j];
    	
    }
	
    void makeMaze() {
            // Create a random maze.  The strategy is to start with
            // a grid of disconnected "rooms" separated by walls,
            // then look at each of the separating walls, in a random
            // order.  If tearing down a wall would not create a loop
            // in the maze, then tear it down.  Otherwise, leave it in place.
        int i,j;
        int emptyCt = 0; // number of rooms
        int wallCt = 0;  // number of walls
        int[] wallrow = new int[(rows*columns)/2];  // position of walls between rooms
        int[] wallcol = new int[(rows*columns)/2];
        for (i = 0; i<rows; i++)  // start with everything being a wall
            for (j = 0; j < columns; j++)
                maze[i][j] = wallCode;
        for (i = 1; i<rows-1; i += 2)  { // make a grid of empty rooms
            for (j = 1; j<columns-1; j += 2) {
                emptyCt++;
                maze[i][j] = -emptyCt;  // each room is represented by a different negative number
                if (i < rows-2) {  // record info about wall below this room
                    wallrow[wallCt] = i+1;
                    wallcol[wallCt] = j;
                    wallCt++;
                }
                if (j < columns-2) {  // record info about wall to right of this room
                    wallrow[wallCt] = i;
                    wallcol[wallCt] = j+1;
                    wallCt++;
                }
            }
        }
        
        int r;
        for (i=wallCt-1; i>0; i--) {
            r = (int)(Math.random() * i);  // choose a wall randomly and maybe tear it down
            tearDown(wallrow[r],wallcol[r]);
            wallrow[r] = wallrow[i];
            wallcol[r] = wallcol[i];
        }
        for (i=1; i<rows-1; i++)  // replace negative values in maze[][] with emptyCode
            for (j=1; j<columns-1; j++)
                if (maze[i][j] < 0)
                    maze[i][j] = emptyCode;
    }

    void tearDown(int row, int col) {
            // Tear down a wall, unless doing so will form a loop.  Tearing down a wall
            // joins two "rooms" into one "room".  (Rooms begin to look like corridors
            // as they grow.)  When a wall is torn down, the room codes on one side are
            // converted to match those on the other side, so all the cells in a room
            // have the same code.  Note that if the room codes on both sides of a
            // wall already have the same code, then tearing down that wall would 
            // create a loop, so the wall is left in place.
        if (row % 2 == 1 && maze[row][col-1] != maze[row][col+1]) {
            // row is odd; wall separates rooms horizontally
            fill(row, col-1, maze[row][col-1], maze[row][col+1]);
            maze[row][col] = maze[row][col+1];
        }
        else if (row % 2 == 0 && maze[row-1][col] != maze[row+1][col]) {
            // row is even; wall separates rooms vertically
            fill(row-1, col, maze[row-1][col], maze[row+1][col]);
            maze[row][col] = maze[row+1][col];
        }
    }
    void fill(int row, int col, int replace, int replaceWith) {
            // called by tearDown() to change "room codes".
            // (My algorithm really should have used the standard
            //  union/find data structure.)
        if (maze[row][col] == replace) {
            maze[row][col] = replaceWith;
            fill(row+1,col,replace,replaceWith);
            fill(row-1,col,replace,replaceWith);
            fill(row,col+1,replace,replaceWith);
            fill(row,col-1,replace,replaceWith);
        }
    }
    // updating the color of maze base on loop of 2D array with appropriate color Code
    public void update(final GraphicsContext gc){
    	   Platform.runLater(new Runnable(){

			@Override
			public void run() {
				
				for (int i=0;i<rows;i++)
		    		for (int j=0;j<columns;j++) {
		    			if (maze[i][j]<0)
		    				gc.setFill(Color.BLACK);
		    			else
		    				gc.setFill(color[maze[i][j]]); 
						gc.fillRect(j*blockSize, i*blockSize, blockSize, blockSize);
		    		}
			}
    	   });
    }

	
    
}
