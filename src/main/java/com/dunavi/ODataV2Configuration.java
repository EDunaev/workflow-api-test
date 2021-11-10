package com.dunavi;

import com.sap.cloud.sdk.service.prov.v2.rt.core.web.EndPointsList;
import com.sap.cloud.sdk.service.prov.v2.web.ServiceInitializer;
import org.apache.olingo.odata2.core.servlet.ODataServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextListener;
import java.util.Collections;

@Configuration
@SuppressWarnings("rawtypes")
public class ODataV2Configuration {
    
    private static final String ODATA_BASE_URL = "/api/v1/"; // TODO - adjust the base url

    @Bean
    public ServletContextListener initializer() {

        return wrap(new ServiceInitializer());
    }

    @Bean
    public ServletRegistrationBean<ODataServlet> servlet(
            ApplicationContext context) {

        ServletRegistrationBean<ODataServlet> bean =
                new ServletRegistrationBean<>(new ODataServlet());
        bean.setUrlMappings(Collections.singletonList(ODATA_BASE_URL + "*"));
        bean.addInitParameter("org.apache.olingo.odata2.service.factory",
                ODataServiceFactory.class.getName());
        bean.addInitParameter("org.apache.olingo.odata2.path.split", "1");
        bean.setLoadOnStartup(1);
        return bean;
    }

    @Bean
    public ServletRegistrationBean<EndPointsList> endpoints() {

        ServletRegistrationBean<EndPointsList> bean =
                new ServletRegistrationBean<>(new EndPointsList());
        bean.addUrlMappings(ODATA_BASE_URL);
        return bean;
    }

    private ServletContextListener wrap(ServletContextListener listener) {

        return new CustomODataServletContextListener(listener,
                "com.dunavi"); // TODO - adjust the package 
    }
}
