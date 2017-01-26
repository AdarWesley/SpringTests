package org.awesley.samples.domain;

import org.springframework.statemachine.StateMachine;

public interface ProposalStateMachineAdapter {

	StateMachine<String, String> getStateMachine(Proposal proposal);
	StateMachine<String, String> createStateMachine(Proposal proposal);

}
