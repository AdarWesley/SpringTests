package org.awesley.samples.persistance.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.awesley.samples.domain.Proposal;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "PROPOSALS")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class JpaProposal implements Proposal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private String id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "STATUS")
	private String status;
	
	//@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
//	@Transient
//	private JpaRepositoryStateMachineContext stateMachineContext;
	
	@Transient
	private StateMachine<String, String> stateMachine;
	
	@Override
	public String getID() {
		return id;
	}

	@Override
	public void setID(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public StateMachine<String, String> getStateMachine(){
		return stateMachine;
	}
	
	@Override
	public void setStateMachine(StateMachine<String, String> stateMachine) {
		this.stateMachine = stateMachine;
	}
}
