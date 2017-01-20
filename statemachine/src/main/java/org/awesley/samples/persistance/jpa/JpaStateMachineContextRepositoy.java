package org.awesley.samples.persistance.jpa;

import org.springframework.data.repository.CrudRepository;

public interface JpaStateMachineContextRepositoy extends 
	CrudRepository<JpaRepositoryStateMachineContext, String> {
}
