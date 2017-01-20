package org.awesley.samples.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.uml.UmlStateMachineModelFactory;

@Configuration
@EnableStateMachineFactory(name = { "factoryBasedOnUml" })
public class StateMachineFromUmlConfig extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineModelConfigurer<String, String> model) throws Exception {
        model
            .withModel()
                .factory(modelFromUmlFactory());
    }

    @Bean
    public StateMachineModelFactory<String, String> modelFromUmlFactory() {
        return new UmlStateMachineModelFactory(new ClassPathResource("StateMachine2.uml")); //"classpath:org/springframework/statemachine/uml/docs/simple-machine.uml");
    }
}