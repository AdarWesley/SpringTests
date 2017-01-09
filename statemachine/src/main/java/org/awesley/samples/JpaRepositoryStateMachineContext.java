package org.awesley.samples;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.data.BaseRepositoryEntity;

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
	
	private Map<S,S> historyStates;
	
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

	public String getHistoryStates() {
		return mapToString(historyStates);
	}

	private <X,Y> String mapToString(Map<X,Y> map) {
		return map.entrySet().stream()
				.map(e -> e.getKey().toString() + "->" + e.getValue().toString())
				.collect(Collectors.joining(";"));
	}

	private <X, Y> Map<X, Y> stringToMap(String str){
		//Map<X, Y>
		Arrays.stream(str.split(";")).map(s -> s.split("->")).collect(Collectors.toMap(sa -> sa[0], sa -> sa[1]));
	}
	
	public void setHistoryStates(Map<S, S> historyStates) {
		this.historyStates = historyStates;
	}

	public Map<String, Object> getEventHeaders() {
		return null;
	}

	public ExtendedState getExtendedState() {
		// TODO Auto-generated method stub
		return null;
	}
}
