package com.example.severdemo.configuration;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAspectJAutoProxy
@EnableAsync
public class MapperConfig implements WebMvcConfigurer {
    @Bean
    MapperFacade mapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
                .mapNulls(false)
                .useBuiltinConverters(false).build();
        return mapperFactory.getMapperFacade();
    }

}
