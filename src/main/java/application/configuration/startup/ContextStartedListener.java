package application.configuration.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Context Start
 */
@Component
public class ContextStartedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    DataInject injector;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        injector.insertUsers();
    }


}