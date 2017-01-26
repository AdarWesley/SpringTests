package org.awesley.samples.persistance;

import org.awesley.samples.persistance.jpa.JpaRepositoryStateMachineContext;
import org.springframework.data.repository.CrudRepository;

public interface JpaStateMachineContextRepositoy extends 
	CrudRepository<JpaRepositoryStateMachineContext, String> {
}
