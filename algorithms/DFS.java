package algorithms;

import java.util.Stack;

import node.Node;

public class DFS extends Algorithms {
	
	public DFS() {
		fringe = new Stack<Node>();
	}
	
	@Override
	public
	void put(Node n) {
		((Stack<Node>) fringe).push(n);
	}

	@Override
	public
	Node get() {
		return ((Stack<Node>) fringe).pop();
	}
	
}
