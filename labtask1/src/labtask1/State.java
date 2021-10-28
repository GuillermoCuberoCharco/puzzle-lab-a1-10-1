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

//Lo he hecho por si tenemos que usarlo mas tarde? para cuando haya que expandir mas casos?
	public ArrayList<Node> Expand(int quantity){
		ArrayList<Node> successors = new ArrayList<Node>();
		ArrayList<Successor> new_actions = successorFN(this);
		Node n = new Node();
					    
		for(int i=0; i<new_actions.size(); i++) {
			n.setAction(new_actions.get(i).getAction());
			n.setParent(new Node(null, null, this, 0.0, 0));
			n.setDepth(1);
			n.setCost(1.0);
			n.setState(new_actions.get(i).getNew_state());
						
			successors.add(n);
		}
		
		return successors;

	}

	//Saca todos los posibles resultados de hacer el movimiento con quantity sobre todas as botellas entre si
	public ArrayList<Successor> successorFN(State init_State) {
		ArrayList<Successor> New_actions = new ArrayList<Successor>();
		State new_state = new State();	

		for(int i=0; i<init_State.getBottles().size(); i++) {
			int quantity = init_State.getBottles().get(i).getQuantityTop();

			for(int j=0; j<init_State.getBottles().size(); j++) {
				
				new_state.setBottles(init_State.getBottles());

				if(i!=j) {
					
					if( quantity > 0 && Is_PossibleAction(init_State.getBottles().get(i), init_State.getBottles().get(j), quantity)) {			
						new_state.getBottles().get(i).moveLiquid(quantity, new_state.getBottles().get(j));
												
						Action actionPerformed = new Action(i, j, quantity);
						Successor successor = new Successor(actionPerformed, new_state, 1.0);
						
						New_actions.add(successor);
					}
				}
			}
		}
					
		return New_actions;	
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
	private State Action(int OriginBottle, int DestinationBottle, int quantity) {
		State actual_state = new State ();
		actual_state.setBottles(bottles);
		
		State new_state = null;
		
		Action actionPerformed = new Action(OriginBottle,  DestinationBottle, quantity);
		
		ArrayList<Successor> new_actions = successorFN(actual_state);
		
		for(int i=0; i<new_actions.size(); i++) {
			if(new_actions.get(i).getAction().equals(actionPerformed)) {
				new_state = new_actions.get(i).getNew_state();
			}
		}
		
		return new_state;		 																																																											
	}
	
}

