package labtask1;

import java.util.ArrayList;
import java.util.Iterator;


public class State {
    private ArrayList<Bottle> bottles;

	public State(ArrayList<Bottle> bottles) {
		super();
		this.bottles = bottles;
	}
	public State() {
		super();
	}

	public ArrayList<Bottle> getBottles() {
		return bottles;
	}

	public void setBottles(ArrayList<Bottle> bottles) {
		this.bottles = bottles;
	}
	
	public static boolean Is_Goal(State state) {
		boolean isGoal = true;
		
		for(int i=0; i<state.getBottles().size() && isGoal; i++) {
			if(!state.getBottles().get(i).sameLiquid()) {
				isGoal = false;
			}
		}
		
		return isGoal;
	}
	
	private static boolean Is_PossibleAction(Bottle OriginBottle, Bottle DestinationBottle, int quantity)
	{
		int DestSpace = DestinationBottle.getHeight() - DestinationBottle.quantityLiquid();
		
		if(quantity <= DestSpace && OriginBottle.quantityLiquid() >= quantity) {
			return true;
	    }
		else return false;
	}

	
	//Retorna el new state despues de hacer la accion. El new state se recoje de los posibles resultados calculados por successorFN 
	public State Action(int OriginBottle, int DestinationBottle, int quantity) {		
		State new_state = new State ();
		new_state.setBottles(bottles);		
		
		if(quantity > 0 && Is_PossibleAction(new_state.getBottles().get(OriginBottle), new_state.getBottles().get(DestinationBottle), quantity)) {
			new_state.getBottles().get(OriginBottle).moveLiquid(quantity, new_state.getBottles().get(DestinationBottle));
		}
		
		return new_state;
	}
	
}

