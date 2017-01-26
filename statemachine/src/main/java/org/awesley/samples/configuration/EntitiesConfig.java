package org.awesley.samples.configuration;

import org.awesley.samples.domain.DefaultProposalStateMachineAdapter;
import org.awesley.samples.domain.Proposal;
import org.awesley.samples.domain.ProposalStateMachineAdapter;
import org.awesley.samples.persistance.jpa.JpaProposal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class EntitiesConfig {

	@Bean
	@Scope("prototype")
	public Proposal proposal(ProposalStateMachineAdapter proposalStateMachineAdapter){
		JpaProposal proposal = new JpaProposal();
		proposalStateMachineAdapter.createStateMachine(proposal);
		
		return proposal;
	}
	
	@Bean
	public ProposalStateMachineAdapter proposalStateMachineAdapter(){
		return new DefaultProposalStateMachineAdapter();
	}
}
