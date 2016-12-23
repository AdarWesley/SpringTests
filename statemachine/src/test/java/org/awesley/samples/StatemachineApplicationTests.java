package org.awesley.samples;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.model.StateMachineModel;
import org.springframework.statemachine.uml.UmlStateMachineModelFactory;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatemachineApplicationTests {
	
	@Autowired
	StateMachineFactory<String, String> proposalStateMachineFactory;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void loadStateMachine() {
		StateMachine<String, String> tempStateMachin;
		
		tempStateMachin = proposalStateMachineFactory.getStateMachine();
		assertNotNull(tempStateMachin);
	}

	@Test
	public void loadStateMachineModel(){
		UmlStateMachineModelFactory umlStateMachineModelFactory;
		
		umlStateMachineModelFactory = new UmlStateMachineModelFactory(new ClassPathResource("model.uml"));
		StateMachineModel<String, String> stateMachineModel = umlStateMachineModelFactory.build();
		
		assertNotNull(stateMachineModel);
	}
}
