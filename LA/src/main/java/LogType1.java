import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class LogType1 implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(name = "Id")
	private long id;
	// private static final long serialVersionUID = 1L;
	@Column(name = "DateTime")
	private java.sql.Timestamp datetime;
	@Column(name = "Priority")
	private String priority;
	@Column(name = "Data")
	private String data;
	@Column(name = "Host")
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
