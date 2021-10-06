package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import node.Node;

public class AStar extends Algorithms{
	public AStar() {
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
		Node r;
		Collections.sort(fringe,new Comparator<Node>() {
			@Override
			 public int compare(Node o1, Node o2) {
	             return (o1.pathCost + o1.dept) > (o2.pathCost+o2.dept) ? 1 : -1;
	         }
		});
		r = fringe.remove(0);
		return r;
		
	}
}
