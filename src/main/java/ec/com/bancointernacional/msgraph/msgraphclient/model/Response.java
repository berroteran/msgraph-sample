package ec.com.bancointernacional.msgraph.msgraphclient.model;


public class Response {

	private int messageId;

	private String messageDescription;

	public Response() {
		super();
	}

	public Response(int messageId, String messageDescription) {
		super();
		this.messageId = messageId;
		this.messageDescription = messageDescription;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getMessageDescription() {
		return messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}

	@Override
	public String toString() {
		return "Response [messageId=" + messageId + ", messageDescription=" + messageDescription + "]";
	}

}
