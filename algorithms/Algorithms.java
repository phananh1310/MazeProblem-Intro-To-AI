package algorithms;

import java.util.List;

import node.Node;

public abstract class Algorithms {	
	public List<Node> fringe;
	public abstract void put(Node n);
	public abstract Node get();
	
}
