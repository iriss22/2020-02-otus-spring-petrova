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
import ru.otus.spring.petrova.domain.h2.GenreH2;
import ru.otus.spring.petrova.domain.mongo.GenreM;
import ru.otus.spring.petrova.service.GenreService;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GenreJobConfig {

  private static final int CHUNK_SIZE = 5;
  public static final String TRANSFER_GENRE_JOB_NAME = "transferGenreJob";
  private final Logger logger = LoggerFactory.getLogger("Batch");

  private final EntityManagerFactory emf;
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final GenreService genreService;

  @StepScope
  @Bean
  public JpaPagingItemReader<GenreH2> genreReader() {
    return new JpaPagingItemReaderBuilder<GenreH2>()
        .name("genreItemReader")
        .entityManagerFactory(emf)
        .queryString("select a from GenreH2 a")
        .pageSize(10)
        .build();
  }

  @StepScope
  @Bean
  public ItemProcessor genreProcessor() {
    return (ItemProcessor<GenreH2, GenreM>) genreService::map;
  }

  @StepScope
  @Bean
  public MongoItemWriter<GenreM> genreWriter(MongoTemplate mongoTemplate) {
    return new MongoItemWriterBuilder<GenreM>()
        .template(mongoTemplate)
        .collection("genre")
        .build();
  }

  @Bean
  public Job transferGenreJob(Step transferGenreStep) {
    return jobBuilderFactory.get(TRANSFER_GENRE_JOB_NAME)
        .incrementer(new RunIdIncrementer())
        .flow(transferGenreStep)
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
  public Step transferGenreStep(JpaPagingItemReader<GenreH2> genreReader,
                                ItemProcessor<GenreH2, GenreM> genreProcessor,
                                MongoItemWriter<GenreM> genreWriter) {
    return stepBuilderFactory.get("transferGenreStep")
        .<GenreH2, GenreM>chunk(CHUNK_SIZE)
        .reader(genreReader)
        .processor(genreProcessor)
        .writer(genreWriter)
        .listener(new ItemReadListener<>() {
          public void beforeRead() { logger.info("Начало чтения"); }
          public void afterRead(GenreH2 o) { logger.info("Конец чтения"); }
          public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
        })
        .listener(new ItemWriteListener<>() {
          public void beforeWrite(List list) { logger.info("Начало записи"); }
          public void afterWrite(List list) { logger.info("Конец записи"); }
          public void onWriteError(Exception e, List list) { logger.info("Ошибка записи"); }
        })
        .listener(new ItemProcessListener<>() {
          public void beforeProcess(GenreH2 o) {logger.info("Начало обработки");}
          public void afterProcess(GenreH2 o, GenreM o2) {logger.info("Конец обработки");}
          public void onProcessError(GenreH2 o, Exception e) {logger.info("Ошбка обработки");}
        })
        .listener(new ChunkListener() {
          public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
          public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
          public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
        })
        .build();
  }
}
