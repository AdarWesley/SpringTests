package org.awesley.samples;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.ObjectStateMachineFactory;
import org.springframework.statemachine.config.StateMachineBuilder;
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
//		ResourceSet resourceSet = new ResourceSetImpl();
//		UMLPackage umlPackage = UMLPackage.eINSTANCE;
//		resourceSet.getPackageRegistry().put(umlPackage.getNsURI(), umlPackage);
		
		UmlStateMachineModelFactory umlStateMachineModelFactory;
		
		umlStateMachineModelFactory = new UmlStateMachineModelFactory(new ClassPathResource("StateMachine2.uml"));
		StateMachineModel<String, String> stateMachineModel = umlStateMachineModelFactory.build();
		assertNotNull(stateMachineModel);
		
		ObjectStateMachineFactory<String, String> factory = new ObjectStateMachineFactory<>(stateMachineModel);
		
		StateMachine<String, String> stateMachine = factory.getStateMachine();
		assertNotNull(stateMachine);
	}
}
