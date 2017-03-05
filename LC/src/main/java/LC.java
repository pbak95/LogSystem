import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.ObjectOutputStream;

public class LC implements Runnable {
	
	private static LC lc_client;
	/** socket */
	private Socket socket = null;
	/** strumien wejsciowy */
	private BufferedReader input;
	/** strumien wyjsciowy */
	private PrintWriter output;
	/**Obiekt do wyslania*/
	private ObjectOutputStream outputStream;
	/**Referencja do calendar*/
	private  Vector<LogType1> logs = new Vector<LogType1>();
	private  int counter;
	private  int counter2;
	private Vector<NewLog> newlogs = new Vector<NewLog>();
	private ConsoleInterface console;
	
	/** Czyœci bufor wyjsciowy */
	public void flushOutput() {
		output.flush();
	}

	/** flaga dostepnosci serwera */
	public static boolean serverOn = true;
	/**Konstruktor klasy Log Client*/
	public LC(String host, String port) throws Exception {

		try {
			socket = new Socket(host, Integer.parseInt(port));
			//socket.setSoTimeout(10*1000);
		} catch (UnknownHostException e) {
			throw new Exception("Unknown host.");
		} catch (IOException e) {
			serverOn = false;
			return;
		} catch (NumberFormatException e) {
			throw new Exception("Port value must be a number.");
		}
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException ex) {
			serverOn = false;
		} catch (Exception e) {
			serverOn = false;
		}
		
		
		new Thread(this).start();
		output.flush();
	}

	/**
	 * Metoda
	 * 
	 * @return
	 */
	synchronized boolean isDisconnected() {
		if(socket==null){
			return true;
		}else{
			return false;
		}

	}

	/**
	 * Metoda odpowiadaj¹ca za wysy³anie danych potrzebnych do wlasciwej
	 * komunikacji klient-serwer
	 * 
	 * @param command
	 */
	synchronized void sendCommand(String command) {
		if (output != null)
			output.println(command);
	}

	/**
	 * Metoda obslugujaca watek klietna, oczekiwanie na przyjscie danych
	 */
	public void run() {
		while (true)
			try {
				String command = input.readLine();
				if (!handleCommand(command)) {
					output.close();
					input.close();
					outputStream.close();
					socket.close();
					break;
				}
			} catch (IOException e) {
			}

		output = null;
		input = null;
		synchronized (this) {
			socket = null;
			System.out.println("Brak po³¹czenia");
		}

	}

	/**
	 * Metoda obs³uguj¹ca dane przychodz¹ce z serwera
	 * 
	 * @param command
	 * @return
	 * @throws IOException
	 */
	synchronized private boolean handleCommand(String command) throws IOException {
		StringTokenizer st = new StringTokenizer(command);
		String cd = st.nextToken();
		if (cd.equals(Protocol.READY)) {
			if(lc_client.logs.size()>0){
			this.sendCommand(Protocol.SENDLOG);
			}else if(lc_client.newlogs.size()>0){
			this.sendCommand(Protocol.NEWLOG);
			}else{
				//this.sendCommand(Protocol.ENDSENDING);
				
				//return false;
				//System.exit(0);
			}
		}else if(cd.equals(Protocol.OK1)){
			lc_client.sendLog();
		}else if(cd.equals(Protocol.OK2)){
			lc_client.sendNewLog();
		}else if (cd.equals(Protocol.ERR)) {
		
			System.out.println("ERROR");
		} else {
			return false;
		}
		return true;

	}
	
	/**Metoda wysylajaca obiekt log
	 * @throws IOException */
	synchronized private void sendLog() throws IOException{
	
			
			//System.out.println("Wysylanie logu " + this.counter);
			outputStream.reset();
			outputStream.writeObject(logs.firstElement());
			outputStream.flush();
			//System.out.println("Wys³ano log " + this.counter);
			logs.remove(logs.firstElement());
			this.counter--;
		
		
	}

	synchronized private void sendNewLog() throws IOException{
		//System.out.println("Wysylanie logu " + this.counter2);
		outputStream.reset();
		outputStream.writeObject(newlogs.firstElement());
		outputStream.flush();
		//System.out.println("Wys³ano nowy log "+ this.counter2);
		newlogs.remove(newlogs.firstElement());
		this.counter2--;
	}
	
	public void addLog1(LogType1 log){
		logs.addElement(log);
		this.counter++;
	}
	
	public  void addNewLog(NewLog log){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		log.addPair("Time", (java.sql.Timestamp.valueOf(dateFormat.format(cal.getTime()))).toString());
		newlogs.addElement(log);
		this.counter2++;
	}
	
	public  LogType1 createLogType1(String priority, String host, String data){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		//System.out.println(java.sql.Timestamp.valueOf(dateFormat.format(cal.getTime())));
		LogType1 log = new LogType1();
		log.setDatetime(java.sql.Timestamp.valueOf(dateFormat.format(cal.getTime())));
		log.setPriority(priority);
		log.setHost(host);
		log.setData(data);
		
		return log;
	}
	
	public void exit(){
		System.exit(0);
	}

	public static void main(String[] args) {

		try {
			lc_client = new LC("localhost", "13389");
			
//			lc_client.addLog1(lc_client.createLogType1("HIGH","198.162.1.101","Security alarm"));
//			lc_client.addLog1(lc_client.createLogType1("WARNING","198.162.1.102","User XYZ logged into machine" ));
//			lc_client.addLog1(lc_client.createLogType1("CRITICAL","198.162.1.100","Low disk space" ));
//			NewLog newlog = new NewLog("logtype5");
//			newlog.addPair("Time", "15:12");
//			newlog.addPair("Host", "162.12.32.12");
//			newlog.addPair("Message", "jakas wiadomosc5");
//			newlog.addPair("User", "Janusz");
//			newlog.addPair("ErrorID", "120301asfxz1231");
//			lc_client.addNewLog(newlog);
			//lc_client.sendCommand(Protocol.STARTSENDING);
			
			 lc_client.console = new ConsoleInterface(lc_client);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	
	}

}
