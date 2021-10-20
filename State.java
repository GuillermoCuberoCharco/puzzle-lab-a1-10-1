import java.util.ArrayList;
import java.util.Iterator;


public class State {
    private Bottle OriginBottle;
    private Bottle DestinationBottle;

    public State (Bottle o, Bottle d) {
        this.OriginBottle = o;
        this.DestinationBottle = d;
    }

	public State successor(int quantity) {
	    int sumQuantity = 0;
		int destQuantity = 0;
		int numLiquids = liquids.size();
		
		for(int i=numLiquids-1; i>=0; i--) {
			sumQuantity += liquids.get(i).getQuantity();
			
			if(quantity >= sumQuantity) {
				addLiquid(liquids.get(i).getCode(), liquids.get(i).getQuantity(), DestinationBottle);
				liquids.remove(i);				
			}
			else {
				destQuantity =  liquids.get(i).getQuantity() - (sumQuantity-quantity);
				addLiquid(liquids.get(i).getCode(), destQuantity, DestinationBottle);
				
				liquids.get(i).setQuantity(sumQuantity-quantity);
				break;
			}
		}
        State suc = new Suc(OriginBottle,DestinationBottle);
        return suc;
}