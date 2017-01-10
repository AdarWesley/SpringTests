package org.awesley.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.data.BaseRepositoryEntity;
import org.springframework.statemachine.support.DefaultExtendedState;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.StringIdGenerator.class)
public class JpaRepositoryStateMachineContext<S, E> extends BaseRepositoryEntity {

	@Id
	private String id;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<JpaRepositoryStateMachineContext<S, E>> childs;
	
	private S state;
	private E event;
	
	@Convert(converter = JpaConverterJson.class)
	private Map<S,S> historyStates;
	
	@Convert(converter = JpaConverterJson.class)
	private Map<String, Object> eventHeaders;
	
	@Convert(converter = JpaConverterJson.class)
	private ExtendedState extendedState;
	
	public JpaRepositoryStateMachineContext() {
	}

	public JpaRepositoryStateMachineContext(StateMachineContext<S,E> stateMachineContext){
		this.id = stateMachineContext.getId();
		this.childs = cloneChildsCollection(stateMachineContext);
		this.state = stateMachineContext.getState();
		this.event = stateMachineContext.getEvent();
		this.historyStates = cloneHistoryStates(stateMachineContext);
		this.eventHeaders = cloneEventHeaders(stateMachineContext);
		this.extendedState = cloneExtendedState(stateMachineContext);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<JpaRepositoryStateMachineContext<S, E>> getChilds() {
		return childs;
	}

	public void setChilds(List<JpaRepositoryStateMachineContext<S, E>> childs) {
		this.childs = childs;
	}

	public S getState() {
		return state;
	}

	public void setState(S state) {
		this.state = state;
	}

	public E getEvent() {
		return event;
	}

	public void setEvent(E event) {
		this.event = event;
	}

	public Map<S, S> getHistoryStates() {
		return historyStates;
	}

	public void setHistoryStates(Map<S, S> historyStates) {
		this.historyStates = historyStates;
	}

	public Map<String, Object> getEventHeaders() {
		return eventHeaders;
	}

	public void setEventHeaders(Map<String, Object> eventHeaders) {
		this.eventHeaders = eventHeaders;
	}

	public ExtendedState getExtendedState() {
		return extendedState;
	}

	public void setExtendedState(ExtendedState extendedState) {
		this.extendedState = extendedState;
	}
	
	private List<JpaRepositoryStateMachineContext<S, E>> cloneChildsCollection(
			StateMachineContext<S, E> stateMachineContext) {
		List<JpaRepositoryStateMachineContext<S, E>> childs;
		if (stateMachineContext.getChilds() == null){
			childs = null;
		} else {
			childs = new ArrayList<JpaRepositoryStateMachineContext<S,E>>();
			childs.addAll(stateMachineContext.getChilds().stream()
					.map(cc -> new JpaRepositoryStateMachineContext<>(cc))
					.collect(Collectors.toList()));
		}
		return childs;
	}

	private Map<S,S> cloneHistoryStates(StateMachineContext<S, E> stateMachineContext) {
		Map<S,S> historyStates;
		if (stateMachineContext.getHistoryStates() == null){
			historyStates = null;
		} else {
			historyStates = new HashMap<S,S>();
			historyStates.putAll(stateMachineContext.getHistoryStates());
		}
		return historyStates;
	}

	private HashMap<String, Object> cloneEventHeaders(StateMachineContext<S, E> stateMachineContext) {
		HashMap<String,Object> eventHeaders;
		if (stateMachineContext.getEventHeaders() == null){
			eventHeaders = null;
		} else {
			eventHeaders = new HashMap<String,Object>();
			eventHeaders.putAll(stateMachineContext.getEventHeaders());
		}
		return eventHeaders;
	}

	private ExtendedState cloneExtendedState(StateMachineContext<S, E> stateMachineContext) {
		ExtendedState extendedState;
		if (stateMachineContext.getExtendedState() == null){
			extendedState = null;
		} else {
			extendedState = new DefaultExtendedState(stateMachineContext.getExtendedState().getVariables());
		}
		return extendedState;
	}
}
