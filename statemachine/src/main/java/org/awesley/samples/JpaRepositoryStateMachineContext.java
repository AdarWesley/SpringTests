package org.awesley.samples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.data.BaseRepositoryEntity;
import org.springframework.statemachine.support.DefaultExtendedState;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "STATE_MACHINE_CONTEXT")
@JsonIdentityInfo(generator=ObjectIdGenerators.StringIdGenerator.class)
public class JpaRepositoryStateMachineContext extends BaseRepositoryEntity implements StateMachineContext<String,String> {

	@Id
	@Column(name = "ID")
	private String id;
	
	@ManyToOne(targetEntity = JpaRepositoryStateMachineContext.class)
	@JoinColumn(name = "PARENT_ID")
	private JpaRepositoryStateMachineContext parent;
	
	@OneToMany(targetEntity = JpaRepositoryStateMachineContext.class, mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<JpaRepositoryStateMachineContext> jpaChilds;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "EVENT")
	private String event;
	
	@Convert(converter = JpaHashMapStringStringConverterJson.class)
	@Column(name = "HISTORY_STATES")
	private Map<String,String> historyStates;
	
	@Convert(converter = JpaHashMapStringObjectConverterJson.class)
	@Column(name = "EVENT_HEADERS")
	private Map<String, Object> eventHeaders;
	
	@Convert(converter = JpaExtendedStateConverterJson.class)
	@Column(name = "EXTENDED_STATE")
	private ExtendedState extendedState;
	
	public JpaRepositoryStateMachineContext() {
	}

	public JpaRepositoryStateMachineContext(StateMachineContext<String,String> stateMachineContext,
			JpaRepositoryStateMachineContext parent){
		this(stateMachineContext);
		this.parent = parent;
	}

	public JpaRepositoryStateMachineContext(StateMachineContext<String,String> stateMachineContext){
		this.id = stateMachineContext.getId();
		this.jpaChilds = cloneChildsCollection(stateMachineContext);
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

	public List<StateMachineContext<String, String>> getChilds() {
		List<StateMachineContext<String,String>> childs = new ArrayList<>();
		childs.addAll(jpaChilds);
		return childs;
	}

	public void setChilds(List<StateMachineContext<String, String>> childs) {
		this.jpaChilds = new ArrayList<>();
		for (final StateMachineContext<String,String> child : childs){
			JpaRepositoryStateMachineContext jpaChild;
			if (JpaRepositoryStateMachineContext.class.isAssignableFrom(child.getClass())){
				jpaChild = (JpaRepositoryStateMachineContext)child;
				jpaChild.parent = this;
			} else {
				jpaChild = new JpaRepositoryStateMachineContext(child, this);
			}
			this.jpaChilds.add(jpaChild);
		}
	}

	public List<JpaRepositoryStateMachineContext> getJpaChilds() {
		return jpaChilds;
	}

	public void setJpaChilds(List<JpaRepositoryStateMachineContext> jpaChilds) {
		this.jpaChilds = jpaChilds;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Map<String, String> getHistoryStates() {
		return historyStates;
	}

	public void setHistoryStates(Map<String, String> historyStates) {
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
	
	private List<JpaRepositoryStateMachineContext> cloneChildsCollection(
			StateMachineContext<String, String> stateMachineContext) {
		List<JpaRepositoryStateMachineContext> childs;
		if (stateMachineContext.getChilds() == null){
			childs = null;
		} else {
			childs = new ArrayList<JpaRepositoryStateMachineContext>();
			childs.addAll(stateMachineContext.getChilds().stream()
					.map(cc -> new JpaRepositoryStateMachineContext(cc, this))
					.collect(Collectors.toList()));
		}
		return childs;
	}

	private Map<String,String> cloneHistoryStates(StateMachineContext<String, String> stateMachineContext) {
		Map<String,String> historyStates;
		if (stateMachineContext.getHistoryStates() == null){
			historyStates = null;
		} else {
			historyStates = new HashMap<>();
			historyStates.putAll(stateMachineContext.getHistoryStates());
		}
		return historyStates;
	}

	private HashMap<String, Object> cloneEventHeaders(StateMachineContext<String, String> stateMachineContext) {
		HashMap<String,Object> eventHeaders;
		if (stateMachineContext.getEventHeaders() == null){
			eventHeaders = null;
		} else {
			eventHeaders = new HashMap<>();
			eventHeaders.putAll(stateMachineContext.getEventHeaders());
		}
		return eventHeaders;
	}

	private ExtendedState cloneExtendedState(StateMachineContext<String, String> stateMachineContext) {
		ExtendedState extendedState;
		if (stateMachineContext.getExtendedState() == null){
			extendedState = null;
		} else {
			extendedState = new DefaultExtendedState(stateMachineContext.getExtendedState().getVariables());
		}
		return extendedState;
	}
}
