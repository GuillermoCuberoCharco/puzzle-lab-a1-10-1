package labtask1;

import java.io.BufferedReader;
import java.io.File;
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
	

	try
	{
		ArrayList<String> jsonStrings = readFileLines("src//employees.json");
		Iterator<String> reader = jsonStrings.iterator();
		
		//Read JSON file
		while(reader.hasNext()) {
			String line = fixParsing(reader.next());
			Object obj = new Object();
			
			obj = jsonParser.parse(line);
			
			
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
		}

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
	
}

private static String fixParsing(String line) {
	JSONParser jsonParser = new JSONParser();
	
	try {
		jsonParser.parse(line);
	} catch (ParseException e) {
		line = line.substring(0, e.getPosition()) + line.substring(e.getPosition()+1);				
	}
	return line;
}

private static void createBottle(ArrayList<Bottle> listBottles, Object bot)
{

	Bottle b = new Bottle();
	
	JSONArray bottleLiquids = (JSONArray) bot;
	bottleLiquids.forEach(values -> parseLiquid(values, b));

	listBottles.add(b); //adds bottle object to the list of bottles
	
}


private static ArrayList<String> readFileLines(String filepath) throws FileNotFoundException, IOException{
	  File fp = new File(filepath);
	  FileReader fr = new FileReader(fp);
	  BufferedReader br = new BufferedReader(fr);

	  ArrayList<String> lines = new ArrayList<>();
	  String line;
	  while((line = br.readLine()) != null) { lines.add(line); }

	  fr.close();
	  return lines;
}


private static void parseLiquid(Object values, Bottle b) 
{
	
	Color c = new Color();
	JSONArray liquid = (JSONArray) values;
	
	Object liquidCode = liquid.get(0);
	Object liquidQuantity = liquid.get(1);
	
    //Store the String obtained from the objects above
    String code = liquidCode.toString();
    String quantity = liquidQuantity.toString();

    //If any String has a decimal point, we need to get rid of it 
    if(code.contains(".")) {
        int code_dot = code.indexOf("."); //index representing decimal point
        code = code.substring(0,code_dot); //taking integer part only

    }if (quantity.contains(".")) {
        int quantity_dot = quantity.indexOf(".");
        quantity = quantity.substring(0,quantity_dot);
    }

    c.setCode(Integer.parseInt(code));
    c.setQuantity(Integer.parseInt(quantity));

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
