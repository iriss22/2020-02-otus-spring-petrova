package ru.otus.spring.petrova.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.spring.petrova.dto.Language;

import java.util.Collections;
import java.util.List;

@Service
public class HHClientService {
  private final static Logger LOG = LoggerFactory.getLogger(HHClientService.class);
  private final static String LANGUAGE_URL = "https://api.hh.ru/languages";

  private RestOperations rest = new RestTemplate();
  private final RandomWaitService randomWaitService;

  public HHClientService(RandomWaitService randomWaitService) {
    this.randomWaitService = randomWaitService;
  }

  @HystrixCommand(commandKey="getRentsKey", fallbackMethod="buildFallbackLanguages")
  public List<Language> getLanguages() {
    randomWaitService.randomWait();
    RequestEntity request = new RequestEntity(
        HttpMethod.GET,
        UriComponentsBuilder.fromHttpUrl(LANGUAGE_URL).build().toUri()
    );

    LOG.info( request.getUrl().toString() );

    ResponseEntity<List<Language>> response = rest.exchange(request, new ParameterizedTypeReference<>(){});

    return response.getBody();
  }

  public List<Language> buildFallbackLanguages() {
    LOG.info("it's timeout!");
    return Collections.emptyList();
  }
}
