package com.seran.configuration;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.seran.service.factory.BookmarkSearchServiceFactory;

@Configuration
public class BookmarkConfig {

    @Bean
    public FactoryBean serviceLocatorFactoryBean() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(BookmarkSearchServiceFactory.class);
        return factoryBean;
    }
    
}
