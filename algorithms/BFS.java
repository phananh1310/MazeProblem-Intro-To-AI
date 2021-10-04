package algorithms;

import java.util.ArrayList;

import node.Node;

public class BFS extends Algorithms {
	
	public BFS() {
		fringe = new ArrayList<Node>();
	}
	
	@Override
	public
	void put(Node n) {
		fringe.add(n);
	}

	@Override
	public
	Node get() {
		return fringe.remove(0);
	}
}
