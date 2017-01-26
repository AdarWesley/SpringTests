package org.awesley.samples.domain;

import org.springframework.statemachine.StateMachineContext;

public interface Proposal {
	String getID();
	void setID(String id);
	
	String getName();
	void setName(String name);
	
	String getStatus();
	void setStatus(String status);
	
	StateMachineContext<String, String> getStateMachineContext();
	void setStateMachineContext(StateMachineContext<String, String> stateMachineContext);
}
