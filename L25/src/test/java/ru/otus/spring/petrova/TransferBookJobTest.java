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
import ru.otus.spring.petrova.domain.h2.AuthorH2;
import ru.otus.spring.petrova.domain.h2.BookH2;
import ru.otus.spring.petrova.domain.h2.GenreH2;
import ru.otus.spring.petrova.repository.h2.AuthorRepositoryH2;
import ru.otus.spring.petrova.repository.h2.BookRepositoryH2;
import ru.otus.spring.petrova.repository.h2.GenreRepositoryH2;
import ru.otus.spring.petrova.repository.mongo.BookRepositoryM;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.spring.petrova.config.BookJobConfig.TRANSFER_BOOK_JOB_NAME;

@SpringBootTest
public class TransferBookJobTest {

  @Autowired
  @Qualifier(value = "transferBookJob")
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
  private BookRepositoryH2 bookRepositoryH2;

  @Autowired
  private AuthorRepositoryH2 authorRepositoryH2;

  @Autowired
  private GenreRepositoryH2 genreRepositoryH2;

  @Autowired
  private BookRepositoryM bookRepositoryM;

  @Test
  void testJob() throws Exception {
    String bookName = "Test Book";

    bookRepositoryH2.save(new BookH2(bookName,
        authorRepositoryH2.save(new AuthorH2("Test Author2")),
        genreRepositoryH2.save(new GenreH2("Test Genre2")))
    );
    Job job = jobLauncherTestUtils.getJob();
    assertThat(job).isNotNull()
        .extracting(Job::getName)
        .isEqualTo(TRANSFER_BOOK_JOB_NAME);

    JobParameters parameters = new JobParametersBuilder().toJobParameters();
    JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

    assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

    assertThat(bookRepositoryM.findByName(bookName)).isNotNull().isPresent();
  }
}
