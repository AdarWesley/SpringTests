package org.awesley.samples.domain;

import org.springframework.statemachine.StateMachine;

public interface Proposal {
	String getID();
	void setID(String id);
	
	String getName();
	void setName(String name);
	
	String getStatus();
	void setStatus(String status);
	
	StateMachine<String, String> getStateMachine();
	void setStateMachine(StateMachine<String, String> stateMachine);
}
