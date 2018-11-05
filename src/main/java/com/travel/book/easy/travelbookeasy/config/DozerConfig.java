package com.travel.book.easy.travelbookeasy.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfig {
    
    private static final Mapper mapper = new CustomMapper();
    
    public static Mapper getMapper() {
        return mapper;
    }
    
    @Bean
    Mapper configureDozerMapper() {
        return getMapper();
    }
    
    private static class CustomMapper extends DozerBeanMapper {
        @Override
        public <T> T map(Object source, Class<T> destinationClass) throws MappingException {
            if(source == null) {
                return null;
            }

            return super.map(source, destinationClass);
        }
    }

}


