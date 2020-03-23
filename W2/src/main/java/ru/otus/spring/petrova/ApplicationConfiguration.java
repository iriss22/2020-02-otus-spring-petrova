package ru.otus.spring.petrova;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class ApplicationConfiguration {
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasename("/i18n/bundle");
    ms.setDefaultLocale(Locale.ENGLISH);
    ms.setDefaultEncoding("UTF-8");
    return ms;
  }
}
