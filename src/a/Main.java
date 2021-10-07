package a;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

@SuppressWarnings("unchecked")
public static void main(String[] args)
{
	//JSON parser object to parse read file
	JSONParser jsonParser = new JSONParser();

	try (FileReader reader = new FileReader("src//employees.json"))//poner ruta
	{
		//Read JSON file
		Object obj = jsonParser.parse(reader);

		JSONArray bottleList = (JSONArray) obj; //transforma el objeto json en un array
		System.out.println(bottleList);
	

		//Iterate over employee array
		ArrayList<Bottle> listBottles = new ArrayList<Bottle>(); 
		bottleList.forEach( bot -> createBottle(listBottles, bot) ); 
	
		
		//Representation of the bottles
		Iterator<Bottle> bottle = listBottles.iterator();
		while(bottle.hasNext()) {
			System.out.println(bottle.next().toString());
		}

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
}

private static void createBottle(ArrayList<Bottle> listBottles, Object bot)
{

	Bottle b = new Bottle();
	
	JSONArray bottleLiquids = (JSONArray) bot;
	bottleLiquids.forEach(values -> parseLiquid(values, b));

	listBottles.add(b); //adds bottle object to the list of bottles
	
}

private static void parseLiquid(Object values, Bottle b) 
{
	
	Color c = new Color();
	JSONArray liquid = (JSONArray) values;

	int liquidCode = (int) (long) liquid.get(0);
	int liquidQuantity = (int) (long) liquid.get(1);
	
	c.setCode(liquidCode);
	c.setQuantity(liquidQuantity);	
	
	b.getLiquids().add(c); //adds liquid to the bottle b

}

private static boolean Is_PossibleAction(Bottle OriginBottle, Bottle DestinationBottle, int quantity)
{
	int DestSpace = DestinationBottle.getHeight() - DestinationBottle.quantityLiquid();
	
	if(quantity <= DestSpace && OriginBottle.quantityLiquid() >= quantity) {
		return true;
    }
	else return false;
}

private static void Action(Bottle OriginBottle, Bottle DestinationBottle, int quantity) {
	if(Is_PossibleAction(OriginBottle, DestinationBottle, quantity)) {
			OriginBottle.moveLiquid(quantity, DestinationBottle);		 																																																											
	}
	else System.out.println("ERROR"); //mejorar esto :v
}

}

