package labtask1;

public class Successor {
		private Action action;
		private State new_state;
		private double cost;
		
		public Successor(Action action, State new_state, double cost) {
			super();
			this.action = action;
			this.new_state = new_state;
			this.cost = cost;
		}
		public Action getAction() {
			return action;
		}
		public void setAction(Action action) {
			this.action = action;
		}
		public State getNew_state() {
			return new_state;
		}
		public void setNew_state(State new_state) {
			this.new_state = new_state;
		}
		public double getCost() {
			return cost;
		}
		public void setCost(double cost) {
			this.cost = cost;
		}
		
		
}
