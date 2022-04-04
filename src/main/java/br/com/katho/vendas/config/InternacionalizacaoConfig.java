package br.com.katho.vendas.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class InternacionalizacaoConfig {

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");// LOCAL ONDE ESTA NOSSO messages.properties
        messageSource.setDefaultEncoding("ISO-8859-1");// RECONHECE O TIPO TEXTO QUE SÃO AS STRINGS NO NOSSO messages.properties
        messageSource.setDefaultLocale(Locale.getDefault());// PEGA O LOCAL DA APLICAÇÃO (EX: SE TIVER EM INGLES, PORTUGUÊS, ESPANHOL E ETC)
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
