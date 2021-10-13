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
			String line = fixParsing(reader.next()); //In the case there is a existing parsing error in the string it will be fixed
			
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
	String fixedLine=line;
	
	try {
		jsonParser.parse(line);
	} catch (ParseException e) {
		fixedLine = fixString(line);			
	}
	return fixedLine;
}


private static String fixString(String line) {
	String fixLine="";
	int nOpen = 0;
	int nClose = 0;
	
	for(int i=0; i<line.length(); i++) {
		
		if(line.charAt(i) == '[') {
			nOpen++;
		}
		if(line.charAt(i) == ']') {
			nClose++;
			if(nOpen>=1) {
				nOpen--;
				nClose--;
			}
		}
		
		if(line.charAt(i)=='[' && nOpen>2) { //error case 1 
			fixLine = line.substring(0, i-2) + "]" + line.substring(i-2);
			nOpen--;
		}
		if(line.charAt(i)==']' && nClose>1) { //error case 2 
			fixLine = line.substring(0, i);
			nOpen++;
		}	
	}
	return fixLine;
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
	
	Color liquidCol = new Color();
	JSONArray liquid = (JSONArray) values;
	
	Object liquidCode = liquid.get(0);
	Object liquidQuantity = liquid.get(1);
	
    //Store the String obtained from the objects above
    String code = liquidCode.toString();
    String quantity = liquidQuantity.toString();
    
    int c = errorCheck(code);
    int q = errorCheck(quantity);

    liquidCol.setCode(c);
    liquidCol.setQuantity(q);

	b.getLiquids().add(liquidCol); //adds liquid to the bottle b

}

private static int errorCheck(String num) {
    int n = 0;
    
    //If any String has a decimal point, we need to get rid of it 
    if(num.contains(".")) {
    	n = doubleToInt(num);
    } 
    else if(num.contains("-")) {
    	n = negativeToPositive(num);
    }
    else {
    	n = Integer.parseInt(num);
    }
    
    return n;
}

private static int negativeToPositive (String num) {
	long parseStr = Long.parseLong(num);
	int negNum = (int) parseStr;
	int n = Math.abs(negNum);
	return n;
}

private static int doubleToInt(String num) {
	double parseDb = Double.parseDouble(num);
	long n = Math.round(parseDb);
	int finalNum = (int) n;
	return finalNum;
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
