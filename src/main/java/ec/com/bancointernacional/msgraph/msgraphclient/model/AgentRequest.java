package ec.com.bancointernacional.msgraph.msgraphclient.model;


import com.fasterxml.jackson.annotation.JsonProperty;


public class AgentRequest {
	
	private Integer agentId;
	
	public AgentRequest() {
		super();
	}

	
	
	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
	
	
}
