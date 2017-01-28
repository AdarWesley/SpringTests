package org.awesley.samples.domain;

import org.awesley.samples.persistance.JpaStateMachineContextRepositoy;
import org.awesley.samples.persistance.jpa.JpaProposal;
import org.awesley.samples.persistance.jpa.JpaRepositoryStateMachineContext;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;

public class DefaultProposalStateMachineAdapter implements ProposalStateMachineAdapter {
	@Autowired
	StateMachineFactory<String, String> stateMachineRepositoryFactory;
	
	@Autowired
	JpaStateMachineContextRepositoy stateMachineContextRepository;
	
	@Override
	public StateMachine<String, String> getStateMachine(Proposal proposal) {
		JpaProposal jpaProposal = (JpaProposal)proposal;
		
		StateMachine<String, String> stateMachine = jpaProposal.getStateMachine();
		if (stateMachine == null){
			stateMachine = createStateMachine(proposal);
		}
		return stateMachine;
	}

	@Override
	public StateMachine<String, String> createStateMachine(Proposal proposal) {
		JpaProposal jpaProposal = (JpaProposal)proposal;
		
		StateMachine<String, String> stateMachine = stateMachineRepositoryFactory.getStateMachine("Proposal");
		stateMachine.start();
		
		stateMachine.getExtendedState().getVariables().put("proposal", proposal);
		
		jpaProposal.setStateMachine(stateMachine);
		
		jpaProposal.setStateMachineContext(getProposalStateMachineContext(proposal.getID(), stateMachine));
		jpaProposal.setStatus(stateMachine.getState().getId());
		
		return stateMachine;
	}

	@Override
	public void saveStateMachine(Proposal proposal) {
		JpaProposal jpaProposal = (JpaProposal)proposal;
		
		StateMachine<String, String> stateMachine = jpaProposal.getStateMachine();
		JpaRepositoryStateMachineContext stateMachineContext = 
				getProposalStateMachineContext(proposal.getID(), stateMachine);
		
		stateMachineContext.getExtendedState().getVariables().remove("proposal");
		stateMachineContextRepository.save(stateMachineContext);
	}
	
	private JpaRepositoryStateMachineContext getProposalStateMachineContext(String proposalId, StateMachine<String, String> stateMachine) {
		JpaRepositoryStateMachineContext[] jpaStateMachineContextContainer = 
				new JpaRepositoryStateMachineContext[1]; 
		
		DefaultStateMachinePersister<String, String, String> persister
			= new DefaultStateMachinePersister<>(new StateMachinePersist<String,String, String>(){

				@Override
				public void write(StateMachineContext<String, String> context, String contextObj) throws Exception {
					jpaStateMachineContextContainer[0] = new JpaRepositoryStateMachineContext(context);
					jpaStateMachineContextContainer[0].setId(contextObj);
				}

				@Override
				public StateMachineContext<String, String> read(String contextObj) throws Exception {
					throw new NotYetImplementedException("Should not be called");
				}
				
			});
		
		try {
			persister.persist(stateMachine, proposalId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return jpaStateMachineContextContainer[0];
	}

}
