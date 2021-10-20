package labtask1;

public class Node {
	
		private Node parent;
		private  Action action;
		private State state;
		private double  cost;
		private  int depth;
		
		public Node(Node parent, Action action, State state, double cost, int depth) {
			super();
			this.parent = parent;
			this.action = action;
			this.state = state;
			this.cost = cost;
			this.depth = depth;
		}
		public Node() {
			super();
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
		
		
}

