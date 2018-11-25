package ru.otus.khitrov.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.khitrov.db.CachedDBService;
import ru.otus.khitrov.db.CachedDBServiceHibernateImpl;
import ru.otus.khitrov.messageSystem.Address;
import ru.otus.khitrov.messageSystem.MessageSystem;
import ru.otus.khitrov.messageSystem.MessageSystemContext;

@Configuration
public class SpringConfiguration {

    @Bean
    public MessageSystem messageSystem() {
        return new MessageSystem();
    }

    @Bean
    public MessageSystemContext messageSystemContext(){

        MessageSystemContext context = new MessageSystemContext(  messageSystem() );

        Address frontAddress = new Address("Frontend");
        context.setFrontAddress(frontAddress);
        Address dbAddress = new Address("DB");
        context.setDbAddress(dbAddress);

        return context;
    }

    @Bean
    public CachedDBService cachedDBService() {
        return new CachedDBServiceHibernateImpl( messageSystemContext());
    }


}
