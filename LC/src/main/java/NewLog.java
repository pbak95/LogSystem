import java.io.Serializable;
import java.util.ArrayList;

public class NewLog implements Serializable{
	
	String logType;
	ArrayList<String> keyArray = new ArrayList<String>();
	ArrayList<String> valueArray = new ArrayList<String>(); 
	
	public void addPair(String key, String value){
		keyArray.add(key);
		valueArray.add(value);
		
	}
	
	public NewLog(String type){
		this.logType=type;
	}

}
