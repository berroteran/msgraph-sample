package ec.com.bancointernacional.msgraph.msgraphclient.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AgentScheduleDay {
	
	private Calendar cal = Calendar.getInstance();
	private Date dayDate;
	private List<AgentScheduleHora > times = new ArrayList<AgentScheduleHora>();


	public Date getDayDate() {
		return dayDate;
	}

	public void setDayDate(Date fecha) {
		this.dayDate = fecha;
	}

	public List<AgentScheduleHora > getTimes() {
		return times;
	}

	public void setTimes(List<AgentScheduleHora > horas) {
		this.times = horas;
	}

	public void addHora(AgentScheduleHora hora) {
		if ( null == times)
			times = new ArrayList<AgentScheduleHora>();
		times.add(hora);
	}
	public void addHora(String start, String end, boolean free) {
		if ( null == times)
			times = new ArrayList<AgentScheduleHora>();
		times.add( new AgentScheduleHora( start,end , free) );
	}

}
