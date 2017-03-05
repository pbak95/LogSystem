import java.io.Serializable;

public class LogType1 implements Serializable{
	
	private long id;
	//private static final long serialVersionUID = 1L;
	private java.sql.Timestamp datetime;
	private String priority;
	private String data;
	private String host;
	
	

	public LogType1() {

	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public java.sql.Timestamp getDatetime() {
		return datetime;
	}

	public void setDatetime(java.sql.Timestamp datetime) {
		this.datetime = datetime;
	}

	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	

	
}

