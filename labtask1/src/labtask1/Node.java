package labtask1;

public class Node implements Comparable<Node>{
	
		private int id;
		private double  cost;
		private State state;
		private Node parent;
		private  Action action;
		private  int depth;
		private double heuristic;
		private double value;
	
		
		public Node(int id, double cost, State state, Node parent, Action action, int depth, double heuristic,
				double value) {
			super();
			this.id = id;
			this.cost = cost;
			this.state = state;
			this.parent = parent;
			this.action = action;
			this.depth = depth;
			this.heuristic = heuristic;
			this.value = value;
		}
		public Node() {
			super();
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public double getHeuristic() {
			return heuristic;
		}
		public void setHeuristic(double heuristic) {
			this.heuristic = heuristic;
		}
		public double getValue() {
			return value;
		}
		public void setValue(double value) {
			this.value = value;
		}
		public Node getParent() {
			return parent;
		}
		public void setParent(Node parent) {
			this.parent = parent;
		}
		public Action getAction() {
			return action;
		}
		public void setAction(Action action) {
			this.action = action;
		}
		public State getState() {
			return state;
		}
		public void setState(State state) {
			this.state = state;
		}
		public double getCost() {
			return cost;
		}
		public void setCost(double cost) {
			this.cost = cost;
		}
		public int getDepth() {
			return depth;
		}
		public void setDepth(int depth) {
			this.depth = depth;
		}
		@Override
		public int compareTo(Node o) {
			if(this.getCost() > o.getCost()) {
				return 1;
			}
			else if(this.getCost() < o.getCost()) {
				return -1;
			}
			else {
				return 0;
			}
		}
		
}

