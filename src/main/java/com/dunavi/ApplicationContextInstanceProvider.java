package com.dunavi;

import com.sap.cloud.sdk.service.prov.api.internal.InstanceProvider;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextInstanceProvider implements InstanceProvider, ApplicationContextAware {

    private static ApplicationContext context;

    public static <T> T getInstanceTyped(Class<T> clazz) {

        return context.getBean(clazz);
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object getInstance(Class clazz) {

        return context.getBean(clazz);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        

        ApplicationContextInstanceProvider.context = applicationContext;
        
    }
    
}
