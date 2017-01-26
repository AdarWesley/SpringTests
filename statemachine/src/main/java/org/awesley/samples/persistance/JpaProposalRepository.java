package org.awesley.samples.persistance;

import org.awesley.samples.persistance.jpa.JpaProposal;
import org.springframework.data.repository.CrudRepository;

public interface JpaProposalRepository extends CrudRepository<JpaProposal, String> {

}
