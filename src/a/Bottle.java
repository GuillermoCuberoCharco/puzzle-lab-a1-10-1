package a;

import java.util.ArrayList;
import java.util.Iterator;

public class Bottle {
	private int height = 0;
	private ArrayList<Color> liquids = new ArrayList<Color>();

	public ArrayList<Color> getLiquids() {
		return liquids;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setLiquids(ArrayList<Color> content) {
		this.liquids = content;
	}

	@Override
	public String toString() {
		return "Bottle [height=" + height + ", liquids=" + bottleContent() + "]";
	}
	
	public String bottleContent() {
		String content = "";
		Iterator<Color> color = liquids.iterator();
		
		while(color.hasNext()) {
			content += color.next().toString();
		}
		return content;
	}
	
	public int quantityLiquid() {
		int quantity = 0;
		Iterator<Color> color = liquids.iterator();
		
		while(color.hasNext()) {
			quantity += color.next().getQuantity();
		}
		return quantity;
	}
	
	public void moveLiquid(int quantity, Bottle DestinationBottle) {
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
	}
	
	public void addLiquid(int colorCode, int colorQuantity, Bottle DestinationBottle) {
		ArrayList<Color> liquidsDestination = DestinationBottle.getLiquids();
		Color lastLiquidDest =  liquidsDestination.get(liquidsDestination.size()-1);
		
		if(lastLiquidDest.getCode() == colorCode) {
			lastLiquidDest.setQuantity(colorQuantity + lastLiquidDest.getQuantity());
		}
		else {
			Color movedLiquid = new Color(colorCode, colorQuantity);
			liquidsDestination.add(movedLiquid);
		}
		
	}
	
	
}
