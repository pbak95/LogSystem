import java.net.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.io.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LA_Server implements Runnable{
	/** referencja na serwer*/
	private ServerSocket serverSocket;
	/** referencja do klienta*/
    private Vector<LA_Service> clients = new Vector<LA_Service>();
    /** referencja do properties*/
    private Properties props;

    private EntityManagerFactory entityManagerFactory;
	
	private EntityManager entityManager;
    
    public LA_Server(Properties p, String title) {
        
        props = p;
        int port = Integer.parseInt(props.getProperty("port"));
        try {
            serverSocket = new ServerSocket(port);
            entityManagerFactory = Persistence.createEntityManagerFactory("MyDatabase");
        } catch (IOException e) {
            System.err.println("Error starting Log Agent.");
            System.exit(1);
        }
       
        new Thread(this).start();
      
    }
	
	

	public void run() {
		 while (true)
	            try {
	                Socket clientSocket = serverSocket.accept();
	               // clientSocket.setSoTimeout(10*1000);
	                LA_Service clientService = new LA_Service(clientSocket, this, entityManagerFactory.createEntityManager());
	                addClientService(clientService);
	            } catch (IOException e) {
	                System.err.println("Error accepting connection. "
	                        + "Client will not be served...");
	            }
		
	}
	
	synchronized void addClientService(LA_Service clientService)
            throws IOException {
        clientService.init();
        clients.addElement(clientService);
        new Thread(clientService).start();
        System.out.println("Add. " + clients.size());
    }
	
	synchronized void removeClientService(LA_Service clientService) {
        clients.removeElement(clientService);
        clientService.close();
        System.out.println("Remove. " + clients.size());
    }
	
	synchronized void send(String msg) {
        Enumeration<LA_Service> e = clients.elements();
        while (e.hasMoreElements())
            ((LA_Service) e.nextElement()).send(msg);
    }
	
	synchronized void send(String msg, LA_Service skip) {
        Enumeration<LA_Service> e = clients.elements();
        while (e.hasMoreElements()) {
        	LA_Service elem = (LA_Service) e.nextElement();
            if (elem != skip)
                elem.send(msg);
        }
    }
	
	private int _lastID = -1;

    synchronized int nextID() {
        return ++_lastID;
    }
    
    
public static void main(String[] args) {
		
		
	
	 Properties p = new Properties();
     String pName = "LAServer.properties";
     try {
         p.load(new FileInputStream(pName));
     } catch (Exception e) {
         p.put("port", "13389");
        
     }
     try {
         p.store(new FileOutputStream(pName), null);
     } catch (Exception e) {
     }
     new LA_Server(p, "Log Agent");

	}

}
