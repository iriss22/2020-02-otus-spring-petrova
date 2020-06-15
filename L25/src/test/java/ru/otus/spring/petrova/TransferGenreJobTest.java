package ru.otus.spring.petrova;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.petrova.domain.h2.GenreH2;
import ru.otus.spring.petrova.repository.h2.GenreRepositoryH2;
import ru.otus.spring.petrova.repository.mongo.GenreRepositoryM;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.spring.petrova.config.GenreJobConfig.TRANSFER_GENRE_JOB_NAME;

@SpringBootTest
public class TransferGenreJobTest {
  @Autowired
  @Qualifier(value = "transferGenreJob")
  private Job job;

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private JobRepository jobRepository;

  private JobLauncherTestUtils jobLauncherTestUtils;

  private void initializeJobLauncherTestUtils() {
    this.jobLauncherTestUtils = new JobLauncherTestUtils();
    this.jobLauncherTestUtils.setJobLauncher(jobLauncher);
    this.jobLauncherTestUtils.setJobRepository(jobRepository);
    this.jobLauncherTestUtils.setJob(job);
  }

  @BeforeEach
  public void setUp() {
    this.initializeJobLauncherTestUtils();
  }

  @Autowired
  private GenreRepositoryH2 genreRepositoryH2;

  @Autowired
  private GenreRepositoryM genreRepositoryM;

  @Test
  void testJob() throws Exception {
    String genreName = "Test Genre";

    genreRepositoryH2.save(new GenreH2(genreName));
    Job job = jobLauncherTestUtils.getJob();
    assertThat(job).isNotNull()
        .extracting(Job::getName)
        .isEqualTo(TRANSFER_GENRE_JOB_NAME);

    JobParameters parameters = new JobParametersBuilder().toJobParameters();
    JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

    assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

    assertThat(genreRepositoryM.findByName(genreName)).isNotNull().isPresent();
  }
}
