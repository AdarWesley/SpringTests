package org.awesley.samples.persistance;

import org.awesley.samples.persistance.jpa.JpaRepositoryStateMachineContext;
import org.awesley.samples.persistance.JpaStateMachineContextRepositoy;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachineContextRepository;

public class PersistStateMachineContextRepository implements StateMachineContextRepository<String, String, StateMachineContext<String, String>> {

	private JpaStateMachineContextRepositoy jpaRepository;
	
	public PersistStateMachineContextRepository(JpaStateMachineContextRepositoy jpaRepository){
		this.jpaRepository = jpaRepository;
	}
	
	@Override
	public void save(StateMachineContext<String, String> context, String id) {
		JpaRepositoryStateMachineContext jpaContext =
				new JpaRepositoryStateMachineContext(context);
		jpaContext.setId(id);
		jpaRepository.save(jpaContext);
	}

	@Override
	public StateMachineContext<String, String> getContext(String id) {
		return jpaRepository.findOne(id);
	}

}
