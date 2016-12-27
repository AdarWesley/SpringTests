package org.awesley.samples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.data.RepositoryStateMachineModelFactory;
import org.springframework.statemachine.data.StateRepository;
import org.springframework.statemachine.data.TransitionRepository;
import org.springframework.statemachine.data.jpa.JpaRepositoryState;
import org.springframework.statemachine.data.jpa.JpaRepositoryTransition;

@Configuration
@EnableStateMachineFactory(name = { "stateMachineRepositoryFactory" })
public class StateMachineRepositoryConfig extends StateMachineConfigurerAdapter<String, String> {

    @Autowired
    private StateRepository<JpaRepositoryState> stateRepository;

    @Autowired
    private TransitionRepository<JpaRepositoryTransition> transitionRepository;

    @Override
    public void configure(StateMachineModelConfigurer<String, String> model) throws Exception {
        model
            .withModel()
                .factory(modelFromRepositoryFactory());
    }

    @Bean
	public StateMachineModelFactory<String, String> modelFromRepositoryFactory() {
        return new RepositoryStateMachineModelFactory(stateRepository, transitionRepository);
    }
}