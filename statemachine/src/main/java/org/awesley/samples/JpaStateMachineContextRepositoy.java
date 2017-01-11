package org.awesley.samples;

import org.springframework.data.repository.CrudRepository;

public interface JpaStateMachineContextRepositoy extends 
	CrudRepository<JpaRepositoryStateMachineContext, String> {
}
