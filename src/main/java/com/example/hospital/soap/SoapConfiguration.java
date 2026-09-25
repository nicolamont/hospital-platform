package com.example.hospital.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/** Configurazione dell'infrastruttura SOAP del progetto. */
@EnableWs
@Configuration
public class SoapConfiguration {

  @Bean
  public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
      ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean<>(servlet, "/ws/*");
  }

  @Bean(name = "medical-records")
  public DefaultWsdl11Definition medicalRecordsWsdl(XsdSchema medicalRecordsSchema) {
    DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
    definition.setPortTypeName("MedicalRecordsPort");
    definition.setLocationUri("/ws");
    definition.setTargetNamespace("http://example.com/hospital/medical-records");
    definition.setSchema(medicalRecordsSchema);
    return definition;
  }

  @Bean
  public XsdSchema medicalRecordsSchema() {
    return new SimpleXsdSchema(new ClassPathResource("medical-records.xsd"));
  }
}
