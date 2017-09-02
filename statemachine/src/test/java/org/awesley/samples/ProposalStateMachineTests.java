package org.awesley.samples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.awesley.samples.domain.Proposal;
import org.awesley.samples.domain.ProposalStateMachineAdapter;
import org.awesley.samples.persistance.JpaProposalRepository;
import org.awesley.samples.persistance.jpa.JpaProposal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProposalStateMachineTests {
	@Autowired
	ConfigurableApplicationContext context;
	
	@Autowired
	StateMachineRepositoryInitializer stateMachineRepositoryInitializer;
	
	@Autowired
	ProposalStateMachineAdapter proposalStateMachineAdapter;
	
	@Autowired
	JpaProposalRepository proposalRepository;

	@Before
	public void setUpBefore() throws Exception {
		stateMachineRepositoryInitializer.initializeProposalStateMachine();
	}

	@After
	public void tearDownAfter() throws Exception {
		stateMachineRepositoryInitializer.clearProposalStateMachine();
	}

	@Test
	public void createNewProposalHasStateAndStateMachine() {
		Proposal p = context.getBean(Proposal.class);
		
		assertNotNull(p);
		assertEquals("S1", p.getStatus());
		
		assertNotNull(proposalStateMachineAdapter);
		StateMachine<String, String> stateMachine = p.getStateMachine();
		assertNotNull(stateMachine);
		
		assertEquals("S1", stateMachine.getState().getId());
	}
	
	@Test
	public void persistStateMachineContextWithProposal(){
		Proposal p = context.getBean(Proposal.class);
		
		assertNotNull(p);
		
		proposalRepository.save((JpaProposal)p);
		proposalStateMachineAdapter.saveStateMachine(p);
	}

	@org.springframework.boot.test.context.TestConfiguration
	static class TestConfiguration {
		
		@Bean
		@Primary
		public DataSource dataSource(){
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setUrl("jdbc:h2:tcp://localhost/~/test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
			dataSource.setUsername("sa");
			dataSource.setPassword("");
			dataSource.setDriverClassName("org.h2.Driver");
			return dataSource;
		}
		
		@Bean
		public StateMachineRepositoryInitializer stateMachineRepositoryInitializer()
		{
			return new StateMachineRepositoryInitializer();
		}
		
		@Bean
		public Action<String, String> S1ToS2TransitionAction(){
			return new ThreadIdAction();
		}
		
		@Bean
		public Action<String, String> TestClassInstanceAction(){
			return new TestClassInstanceAction();
		}
	}
	
	static class ThreadIdAction implements Action<String, String> {

		private long actionThreadId = 0;
		
		@Override
		public void execute(StateContext<String, String> context) {
			actionThreadId = Thread.currentThread().getId();
		}

		public long getActionThreadId() {
			return actionThreadId;
		}
	}
	
	static class TestClassInstanceAction implements Action<String, String> {

		private StateMachineRepositoryTests testClassInstance = null;
		
		@Override
		public void execute(StateContext<String, String> context) {
			if (context.getExtendedState().getVariables().containsKey("testClass")){
				testClassInstance = (StateMachineRepositoryTests)
						context.getExtendedState().getVariables().get("testClass");
			}
		}

		public StateMachineRepositoryTests getTestClassInstance() {
			return testClassInstance;
		}
	}
}
