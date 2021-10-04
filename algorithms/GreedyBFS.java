package algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


import node.Node;

public class GreedyBFS extends Algorithms {
	public GreedyBFS() {
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
		Collections.sort(fringe,new Comparator<Node>() {
			@Override
			 public int compare(Node o1, Node o2) {
	             return o1.pathCost > o2.pathCost ? 1 : -1;
	         }
		});
		
		return fringe.remove(0);
	}
	 
}
