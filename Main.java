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
<<<<<<< Updated upstream:Main.java
=======
	int option;
	Scanner sc = new Scanner(System.in);
	
	System.out.println("What do you would like to do?");
	System.out.println("1) Read States");
	System.out.println("2) Check actions");
	option = sc.nextInt();
	while(option < 1 || option > 2) {
		System.out.println("What do you would like to do?");
		System.out.println("1) Read States");
		System.out.println("2) Check actions");
		option = sc.nextInt();
	}
	// Lo suyo seria pedir la ruta del fichero por texto, para generalizarlo 
	//para cualquier fichero
    if(option == 1) read_states();
    else checkActions();
}

private static void read_states() {
>>>>>>> Stashed changes:labtask1/src/labtask1/Main.java
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
<<<<<<< Updated upstream:Main.java
	
}


=======
}

private static void checkActions() {
	JSONParser jsonParser = new JSONParser();
	String inline ="";
	try
	{
		Object obj = jsonParser.parse(new FileReader("labtask1//src//acciones.json"));
        JSONObject jsonObject =  (JSONObject) obj;
	
        String name = (String) jsonObject.get("Action");
        System.out.println(name);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

//Rosa explicara esto, no?
>>>>>>> Stashed changes:labtask1/src/labtask1/Main.java
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

//Rosa explicara esto, no?
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

//Creates a bottle and adds it to our arraylist
private static void createBottle(ArrayList<Bottle> listBottles, Object bot)
{

	Bottle b = new Bottle();	
	JSONArray bottleLiquids = (JSONArray) bot;
	bottleLiquids.forEach(values -> parseLiquid(values, b));

	listBottles.add(b); //adds bottle object to the list of bottles
	
}

//Reads the file 
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
	//Creates a Color, which represents a liquid object
	Color liquidCol = new Color();

 	/*With the values Object we pass to the method, we convert it into JSON
	and then we check if it has any mistakes */
	JSONArray liquid = (JSONArray) values;
	
	//Gets the liquid attributes from that JSONArray
	Object liquidCode = liquid.get(0);
	Object liquidQuantity = liquid.get(1);
	
    //Store the String obtained from the objects above
    String code = liquidCode.toString();
    String quantity = liquidQuantity.toString();
    
	//In order to parse correctly the liquids we must check first
    int c = errorCheck(code);
    int q = errorCheck(quantity);

	//Stores the liquid after being corrected
    liquidCol.setCode(c);
    liquidCol.setQuantity(q);

	//Add liquid to Bottle b
	b.getLiquids().add(liquidCol);

}

private static int errorCheck(String num) {
    int n = 0;
    
    //If any String has a decimal point, we need to get rid of it 
    if(num.contains(".")) {
    	n = doubleToInt(num);
    } 

	//If any String has a negative value, we need to convert it
    else if(num.contains("-")) {
    	n = negativeToPositive(num);
    }
	//If we don't have any mistakes, we parse the integer
    else {
    	n = Integer.parseInt(num);
    }
    
    return n;
}

//Method to check if number is negative, to convert it
private static int negativeToPositive (String num) {
	
	//We store the String into a long variable type
	long parseStr = Long.parseLong(num);

	//We convert that long into an integer value
	int negNum = (int) parseStr;

	//As we detect the integer is negative, we perform the abs() function
	int n = Math.abs(negNum);

	return n;
}

private static int doubleToInt(String num) {

	//We store the String into a double (because we detected it has decimal point)
	double parseDb = Double.parseDouble(num);

	//To be able to work with it, we need to conver
	long n = Math.round(parseDb);
	int finalNum = (int) n;
	return finalNum;
}

//Esto lo explico yo, antonio, que creo que lo entiendo
private static boolean Is_PossibleAction(Bottle OriginBottle, Bottle DestinationBottle, int quantity)
{
	int DestSpace = DestinationBottle.getHeight() - DestinationBottle.quantityLiquid();
	
	if(quantity <= DestSpace && OriginBottle.quantityLiquid() >= quantity) {
		return true;
    }
	else return false;
}

private static State Action(Bottle OriginBottle, Bottle DestinationBottle, int quantity) {
	State actual_state = new State (OriginBottle,DestinationBottle);

	if(Is_PossibleAction(OriginBottle, DestinationBottle, quantity)) {
			State next_state = actual_state.successor(quantity);
			return next_state;		 																																																											
	}
	else {
		System.out.println("Cannot do the successor function :(("); //mejorar esto :v
		return null;
	}	
}

}
