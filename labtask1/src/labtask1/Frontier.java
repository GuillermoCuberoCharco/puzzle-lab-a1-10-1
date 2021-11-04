package labtask1;

import java.lang.Object;
import java.lang.reflect.Array;
import java.util.*;

public class Frontier {
	private Deque<Node> frontier;
	
//	BFS -> insterta al final, quita al principio 
//	DLS -> inserta al principio, quita al principio (si llega a un limite no busca mas hijos)
//	UCS -> insterta al final, quita al principio (ordenando de menor a mayor coste)


	public Frontier(Deque<Node> frontier) {
		super();
		this.frontier = frontier;
	}
	public Frontier() {
		super();
	}
	public Deque<Node> getFrontier() {
		return frontier;
	}
	public void setFrontier(Deque<Node> frontier) {
		this.frontier = frontier;
	}
	
	public void pushBFS(Node n) {
		frontier.addLast(n);
	}
	
	public void pushDLS(Node n) {
		frontier.addFirst(n);
	}
	
	public Node popBFS() {
		Node n;
		n = frontier.removeFirst();
		return n;
	}
	
	public Node popDLS() {
		Node n;
		n = frontier.removeFirst();
		return n;
	}
	
	public void sortFrontier() {
		
		Node [] aux = new Node [frontier.size()];
		for(int i=0; i<aux.length; i++) {
			aux[i] = frontier.removeFirst();
		}
		
		Arrays.sort(aux, new Comparator<Node>() {
			public int compare(Node n1, Node n2) {
				if(n1.getValue() > n2.getValue()) {
					return 1;
				}
				else if(n1.getValue() < n2.getValue()) {
					return -1;
				}
				else {
					return 0;
				}
			}
		});
		
		for(int i=0; i<aux.length; i++) {
			frontier.addLast(aux[i]);
		}
		
	}

}

