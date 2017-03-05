import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class LA_Service implements Runnable {

	private int id;

	private LA_Server server;

	private Socket clientSocket;

	private ObjectInputStream inStream;

	private BufferedReader input;

	private PrintWriter output;
	
	private EntityManager entityManager;
	
	Connection conn;
	java.sql.Statement stmt;

	public LA_Service(Socket clientSocket, LA_Server server, EntityManager entityManager) {
		this.server = server;
		this.clientSocket = clientSocket;
		this.entityManager = entityManager;
	}

	void init() throws IOException {
		Reader reader = new InputStreamReader(clientSocket.getInputStream());
		input = new BufferedReader(reader);
		output = new PrintWriter(clientSocket.getOutputStream(), true);
	}

	void close() {
		try {
			output.close();
			input.close();
			clientSocket.close();

		} catch (IOException e) {
			System.err.println("Error closing client (" + id + ").");
		} finally {
			output = null;
			input = null;
			clientSocket = null;
			inStream = null;
		}
	}

	public void run() {
		try {
			inStream = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			String request = receive();
			StringTokenizer st = new StringTokenizer(request);
			String command = st.nextToken();
			
			if(command.equals(Protocol.STARTSENDING)){
				send(Protocol.READY);
			}else if (command.equals(Protocol.SENDLOG)) {
			
				output.flush();
				send(Protocol.OK1);
					LogType1 logReceived;
					try {
						
						logReceived = (LogType1) inStream.readObject();
						System.out.println("Doszed³ log");
						entityManager.getTransaction().begin();
						entityManager.persist(logReceived);
						entityManager.getTransaction().commit();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				output.flush();
				send(Protocol.READY);
			}else if(command.equals(Protocol.ENDSENDING)){
				try {
					inStream.close();
					server.removeClientService(this);
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
				server.removeClientService(this);
			} else if (command.equals(Protocol.NEWLOG)) {
				output.flush();
				send(Protocol.OK2);
					NewLog logReceived;
					try {
						
						logReceived = (NewLog) inStream.readObject();
						System.out.println("Doszed³ nowy log");
						StringBuilder createTableQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS `" + logReceived.logType + "` (");
						 createTableQuery.append("`Id`   INT              NOT NULL AUTO_INCREMENT,");
						for (int i=0; i<logReceived.keyArray.size(); i++){
				             
				            createTableQuery.append("`" + logReceived.keyArray.get(i) + "` ");
				            createTableQuery.append("varchar(256)" + " ");
				            createTableQuery.append(", ");
				           
						}
						createTableQuery.append("PRIMARY KEY (Id))");
					  // System.out.println(createTableQuery.toString());
					   
					   StringBuilder insertRecord= new StringBuilder("INSERT INTO `" + logReceived.logType + "` VALUES(NULL,");
					   for (int i=0; i<logReceived.valueArray.size(); i++){
						   insertRecord.append("'"+logReceived.valueArray.get(i)+"',");
					   }
					   insertRecord.replace(insertRecord.lastIndexOf(","),insertRecord.length(), ")");
					  // System.out.println(insertRecord.toString());
						try {
							
							conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/la_database", "root", "root123");
							stmt = conn.createStatement();
							stmt.executeUpdate(createTableQuery.toString());
							stmt.executeUpdate(insertRecord.toString());
						} catch (SQLException e) {
							e.printStackTrace();
						}
					      
					 
					    }
					 catch (ClassNotFoundException e) {
						
						e.printStackTrace();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
				output.flush();
				send(Protocol.READY);
			} else if (command.equals(Protocol.NULLCOMMAND)) {
				server.removeClientService(this);
				break;
			}
		}

		
	}

	void send(String command) {
		output.println(command);
	}

	private String receive() {
		try {
			return input.readLine();
		} catch (IOException e) {
			System.err.println("Error reading client (" + id + ").");
		}
		return Protocol.NULLCOMMAND;
	}

}
