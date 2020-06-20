package ru.otus.spring.petrova.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.petrova.domain.h2.AuthorH2;
import ru.otus.spring.petrova.domain.mongo.AuthorM;
import ru.otus.spring.petrova.service.AuthorService;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthorJobConfig {

  private static final int CHUNK_SIZE = 5;
  public static final String TRANSFER_AUTHOR_JOB_NAME = "transferAuthorJob";
  private final Logger logger = LoggerFactory.getLogger("Batch");

  private final EntityManagerFactory emf;
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  private final AuthorService authorService;

  @StepScope
  @Bean
  public JpaPagingItemReader<AuthorH2> authorReader() {
    return new JpaPagingItemReaderBuilder<AuthorH2>()
        .name("authorItemReader")
        .entityManagerFactory(emf)
        .queryString("select a from AuthorH2 a")
        .pageSize(10)
        .build();
  }

  @StepScope
  @Bean
  public ItemProcessor authorProcessor() {
    return (ItemProcessor<AuthorH2, AuthorM>) authorService::map;
  }

  @StepScope
  @Bean
  public MongoItemWriter<AuthorM> authorWriter(MongoTemplate mongoTemplate) {
    return new MongoItemWriterBuilder<AuthorM>()
        .template(mongoTemplate)
        .collection("author")
        .build();
  }

  @Bean
  public Job transferAuthorJob(Step transferAuthorStep) {
    return jobBuilderFactory.get(TRANSFER_AUTHOR_JOB_NAME)
        .incrementer(new RunIdIncrementer())
        .flow(transferAuthorStep)
        .end()
        .listener(new JobExecutionListener() {
          @Override
          public void beforeJob(JobExecution jobExecution) {
            logger.info("Начало job");
          }

          @Override
          public void afterJob(JobExecution jobExecution) {
            logger.info("Конец job");
          }
        })
        .build();
  }

  @Bean
  public Step transferAuthorStep(JpaPagingItemReader<AuthorH2> authorReader,
                                 ItemProcessor<AuthorH2, AuthorM> authorProcessor,
                                 MongoItemWriter<AuthorM> authorWriter) {
    return stepBuilderFactory.get("transferAuthorStep")
        .<AuthorH2, AuthorM>chunk(CHUNK_SIZE)
        .reader(authorReader)
        .processor(authorProcessor)
        .writer(authorWriter)
        .listener(new ItemReadListener<>() {
          public void beforeRead() { logger.info("Начало чтения"); }
          public void afterRead(AuthorH2 o) { logger.info("Конец чтения"); }
          public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
        })
        .listener(new ItemWriteListener<>() {
          public void beforeWrite(List list) { logger.info("Начало записи"); }
          public void afterWrite(List list) { logger.info("Конец записи"); }
          public void onWriteError(Exception e, List list) { logger.info("Ошибка записи"); }
        })
        .listener(new ItemProcessListener<>() {
          public void beforeProcess(AuthorH2 o) {logger.info("Начало обработки");}
          public void afterProcess(AuthorH2 o, AuthorM o2) {logger.info("Конец обработки");}
          public void onProcessError(AuthorH2 o, Exception e) {logger.info("Ошбка обработки");}
        })
        .listener(new ChunkListener() {
          public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
          public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
          public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
        })
        .build();
  }
}
