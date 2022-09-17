package ec.com.bancointernacional.msgraph.msgraphclient.model;

import com.microsoft.graph.models.DateTimeTimeZone;
import ec.com.bancointernacional.msgraph.msgraphclient.utils.Fechas;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class AgentScheduleHora  {

	private String subject;
	private String start;
	private String end;
	private Boolean free = false;
	
	public AgentScheduleHora(String start, String end, boolean free) {
		this.start = start;
		this.end = end;
		this.free = free;
	}

	public AgentScheduleHora(DateTimeTimeZone start, DateTimeTimeZone end, boolean free) {
		this.start = Fechas.getDateTimeFormatComputer( start);
		this.end = Fechas.getDateTimeFormatComputer( end );
		this.free = free;
	}

	public AgentScheduleHora(String start, String end, boolean b, String subject) {
		this.start = start;
		this.end = end;
		this.free = free;
		this.subject = subject;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Boolean getFree() {
		return free;
	}

	public void setFree(Boolean free) {
		this.free = free;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}