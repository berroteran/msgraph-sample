package ec.com.bancointernacional.msgraph.msgraphclient.model;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class AgentScheduleResponse extends Response {
	
	private Integer AgenteID;
	private TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");

	private List<AgentScheduleDay> days;

	
	public Integer getAgenteID() {
		return AgenteID;
	}
	public void setAgenteID(Integer agenteID) {
		AgenteID = agenteID;
	}

	public String getTimeZone() {
		return timeZone.getID();
	}
	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}


	public List<AgentScheduleDay> getDays() {
		return days;
	}

	public void setDays(List<AgentScheduleDay> lDays) {
		this.days = lDays;
	}

	
	
	
}
