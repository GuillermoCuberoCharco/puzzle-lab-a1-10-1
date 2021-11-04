package labtask1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

@SuppressWarnings("unchecked")
public static void main(String[] args)
{
	int option; 
	Scanner sc = new Scanner(System.in);
	ArrayList<State> init_States = new ArrayList<State>();
	
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
    if(option == 1) read_states(init_States);
    else checkActions();
}

private static void read_states(ArrayList<State> init_States) {

	//JSON parser object to parse read file
	JSONParser jsonParser = new JSONParser();
	

	try
	{

		ArrayList<String> jsonStrings = readFileLines("src//Estados.json");
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
				
			init_States.add(new State(listBottles)); //creamos los states iniciales
		
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


private static void checkActions() {
	JSONParser jsonParser = new JSONParser();
	String inline ="";
	try
	{
		Object obj = jsonParser.parse(new FileReader("src//acciones.json"));
        JSONObject jsonObject =  (JSONObject) obj;
	
        String name = (String) jsonObject.get("Action");
        System.out.println(name);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

//Rosa explicara esto, no?
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

//Saca todos los posibles resultados de hacer el movimiento con quantity sobre todas as botellas entre si
public ArrayList<Successor> successorFN(State init_State) {
	ArrayList<Successor> New_actions = new ArrayList<Successor>();
	State new_state = new State();	

	for(int i=0; i<init_State.getBottles().size(); i++) {
		int quantity = init_State.getBottles().get(i).getQuantityTop();
		
		for(int j=0; j<init_State.getBottles().size(); j++) {
			if(i!=j) {
					new_state = init_State.Action(i, j, quantity);
							
					Action actionPerformed = new Action(i, j, quantity);
					Successor successor = new Successor(actionPerformed, new_state, 1.0);
					
					New_actions.add(successor);
			}
		}
	}
				
	return New_actions;	
}


//Lo he hecho por si tenemos que usarlo mas tarde? para cuando haya que expandir mas casos?
	public ArrayList<Node> Expand(Node node, String strategy){
		ArrayList<Node> successors = new ArrayList<Node>();
		ArrayList<Successor> new_actions = successorFN(node.getState());
						    
		for(int i=0; i<new_actions.size(); i++) {
			Node n = new Node();
			n.setId(node.getId()+i);
			n.setParent(node);
			n.setAction(new_actions.get(i).getAction());
			n.setState(new_actions.get(i).getNew_state());
			n.setCost(node.getCost()+1.0);	
			n.setDepth(node.getDepth()+1);
			n.setHeuristic(0); //No sabemos la heuristica
			//depende de la estrategia BFS = DEPTH | DLS = 1/(DEPTH+1) | UCS = COST
			if(strategy.equals("BREADTH"))
				n.setValue(n.getDepth()); 
			else if(strategy.equals("DEPTH"))
				n.setValue(1/(n.getDepth()+1));
			else if(strategy.equals("UNIFORM"))
				n.setValue(n.getCost());
			
			successors.add(n);
		}
		
		return successors;

	}
	
//	BFS -> insterta al final, quita al principio 
//	DLS -> inserta al principio, quita al principio (si llega a un limite no busca mas hijos)
//	UCS -> insterta al final, quita al principio (ordenando de menor a mayor coste)
	public ArrayList<Action> BFS(){
		return null;
	}

	public ArrayList<Action> DLS(){
		return null;

	}

	public ArrayList<Action> UCS(){
		return null;

	}

}
