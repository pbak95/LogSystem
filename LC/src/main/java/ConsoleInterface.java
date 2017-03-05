import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.Console;


public final class ConsoleInterface {

private LC log_client;

String priorityType;

private boolean quit;

ArrayList<ArrayList<String>> listOLists = new ArrayList<ArrayList<String>>();

Map<String, Integer> map = new HashMap<String, Integer>();

private int counter;

	public ConsoleInterface(LC log_client){
		this.log_client = log_client;
		
		if(log_client.isDisconnected()){
			System.out.println("Brak po³¹czenia z serwerem, spróbuj ponownie póŸniej.");
			log_client.exit();
		}else{
		System.out.println("Witaj w Log Cliencie\n");
		run();
		}
	}	
	
	public void run(){
	
		while(!quit){	
			System.out.print("MENU: \n1) Stwórz standardowy log typu 1 \n2) Stwórz nowy log\n3) Stwórz wczeœniej utworzony log\n4) Wyœlij utworzone logi\n5) WyjdŸ\n");
			Scanner scanner = new Scanner(System.in);
			
			try{
			int answer = Integer.parseInt(scanner.nextLine());

			  switch(answer) {
			  
			    case 1:
			        System.out.println("Podaj wartoœci parametrów: \n");
			        System.out.println("Wybierz priorytet: \n");
			        System.out.print("1) WARNING  \n2) HIGH\n3) CRITICAL\n4) DISEASTER\n");
			        int priority = Integer.parseInt(scanner.nextLine());
			        
			        switch(priority){
			        case 1:
			        	priorityType="WARNING";
			        	break;
			        case 2:
			        	priorityType="HIGH";
			        	break;
			        case 3:
			        	priorityType="CRITICAL";
			        	break;
			        case 4:
			        	priorityType="DISEASTER";
			        	break;
			        default:
			        	System.out.println("Niepoprawna opcja, wybierz odpowiedni priorytet!\n");
				        priority = Integer.parseInt(scanner.next());
			        }
			        System.out.println("Wpisz nazwê hosta: \n");
			        String host = scanner.nextLine();
			        System.out.println("Wpisz tresc logu: \n");
			        String data = scanner.nextLine();
			        log_client.addLog1(log_client.createLogType1(priorityType, host, data));
			        break;
			    case 2:
			    	
			    	 System.out.println("---Podaj nazwy i wartoœci parametrów---");
			    	 System.out.println("Podaj nazwê typu logu: \n");
			    	 String type = scanner.nextLine();
			    	 ArrayList<String> keys = new ArrayList<String>();
			    	 ArrayList<String> values = new ArrayList<String>();
			    	 boolean nextparam = false;
			    	 while(!nextparam){
			    		 System.out.println("\nPodaj nazwê parametru: \n");
			    		 keys.add(scanner.next());
			    		 
			    		 System.out.println("Podaj wartoœæ parametru: \n");
			    		 values.add(scanner.next());
			    		 char choice;
			    		 System.out.println("Czy chcesz dodaæ kolejny parametr Y/N ?");
			    		 choice= scanner.next().charAt(0);
			    		 switch(choice){
			    		 case 'Y':
			    			 break;
			    		 case 'N':
			    		 nextparam=true;
			    		 break;
			    		 default:
			    			 System.out.println("B³edna opcja, wybierz Y lub N!\n");
			    			 choice= scanner.next().charAt(0);
			    		 }
			    	 }
			    	 map.put(type, counter);
			    	 addKeysList(keys);
			    	 NewLog newlog = new NewLog(type);
			    	 for(int i=0; i<keys.size(); i++){
			    		 newlog.addPair(keys.get(i), values.get(i));
			    	 }
			    	 log_client.addNewLog(newlog);
			    	 counter++;
			        break;
			    case 3:
			    	System.out.println("Wybierz typ:\n");
			    	 Set mapSet = (Set) map.entrySet();
			    	 Iterator mapIterator = mapSet.iterator();
			    	 ArrayList<Integer> listtmp1 = new ArrayList<Integer>();
			    	 ArrayList<String> listtmp2 = new ArrayList<String>();
			         while (mapIterator.hasNext()) 
			         {
			         Map.Entry mapEntry = (Map.Entry) mapIterator.next();
			         String keyValue = (String) mapEntry.getKey();
			         Integer value = (Integer) mapEntry.getValue();
			         listtmp1.add(value);
			         listtmp2.add(keyValue);
			         System.out.println(value+") "+keyValue+" \n");
			         }
			         try{
			         int selectedType = Integer.parseInt(scanner.nextLine());
			         
			         if(listtmp1.contains(selectedType)){
			        	 ArrayList<String> paramsNames = listOLists.get(selectedType);
			        	 NewLog newlog2 = new NewLog(listtmp2.get(selectedType));
			        	 for(int i=0; i< paramsNames.size(); i++){
			        		System.out.println(paramsNames.get(i)+": \n");
			        		String paramvalue = scanner.nextLine();
			        		newlog2.addPair(paramsNames.get(i), paramvalue);
			        		 
			        	 }
			        	 log_client.addNewLog(newlog2);
			         }else{
			        	 System.out.println("Wybrano z³y typ");
			        	 break;
			         }
			         }catch (NumberFormatException e) {
			        	 System.out.println("Wybrano z³y typ");
			             break;
			         }
			        break;
			    case 4:
			    	 log_client.sendCommand(Protocol.STARTSENDING);
			        break;
			    case 5:
			    	log_client.sendCommand(Protocol.ENDSENDING);
			    	scanner.close();
			    	log_client.exit();
			        quit=true;
			        break;
			    default:
			        answer = Integer.parseInt(scanner.next());
			    }
	}catch (NumberFormatException e) {
   	 System.out.println("Z³y format!\n");
    
 }
	}
	}
	
	private void addKeysList(ArrayList oldList){
		ArrayList<String> newList = new ArrayList<String>(oldList);
		listOLists.add(newList);
	}


}
