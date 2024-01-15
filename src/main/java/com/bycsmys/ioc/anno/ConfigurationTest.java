package com.bycsmys.ioc.anno;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ConfigurationTest {


    @Bean
    public DowJonesNewListenerAnno dowJonesNewListenerAnno() {
        return new DowJonesNewListenerAnno();
    }

    @Bean
    public DowJonesNewsPersisterAnno dowJonesNewsPersisterAnno() {
        dowJonesNewListenerAnno();
        return new DowJonesNewsPersisterAnno();
    }


}
