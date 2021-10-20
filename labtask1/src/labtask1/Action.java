package labtask1;

public class Action {
	
		private int OriginBottle;
		private int DestBottle;
		private int quantity;
		private State result = null;
		

		public Action(int originBottle, int destBottle, int movedQuantity) {
			super();
			OriginBottle = originBottle;
			DestBottle = destBottle;
			quantity = movedQuantity;
		}
		
		public int getOriginBottle() {
			return OriginBottle;
		}
		
		public void setOriginBottle(int originBottle) {
			OriginBottle = originBottle;
		}
		
		public int getDestBottle() {
			return DestBottle;
		}
		
		public void setDestBottle(int destBottle) {
			DestBottle = destBottle;
		}
		
		public void setQuantity(int movedQuantity) {
			quantity = movedQuantity;
		}

		public void getQuantity() {
			return quantity;
		}

		public State getResult() {
			return result;
		}

		public void setResult(State result) {
			this.result = result;
		}
		
		public boolean equals(Action action) {
			if(this.OriginBottle==action.getOriginBottle() 
					&& this.DestBottle==action.getDestBottle() && this.quantity==action.getQuantity()) {
				return true;
			}
			return false;
		}
}
