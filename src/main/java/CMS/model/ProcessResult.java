package CMS.model;

import lombok.Data;

@Data
public class ProcessResult {
	private String identifier;
	private EventErrorStatus eventStatus;

	public ProcessResult(String identifier) {
		this.identifier = identifier;
	}

	public ProcessResult(EventErrorStatus eventStatus) {
		this.eventStatus = eventStatus;
	}

	public String getIdentifier() {
		// TODO Auto-generated method stub
		return this.identifier;
	}

	public EventErrorStatus getEventStatus() {
		// TODO Auto-generated method stub
		return this.eventStatus;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setEventStatus(EventErrorStatus eventStatus) {
		this.eventStatus = eventStatus;
	}
}
