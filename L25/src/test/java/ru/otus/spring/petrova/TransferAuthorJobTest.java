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
import ru.otus.spring.petrova.repository.h2.AuthorRepositoryH2;
import ru.otus.spring.petrova.repository.mongo.AuthorRepositoryM;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.spring.petrova.config.AuthorJobConfig.TRANSFER_AUTHOR_JOB_NAME;

@SpringBootTest
class TransferAuthorJobTest {

    @Autowired
    @Qualifier(value = "transferAuthorJob")
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
    private AuthorRepositoryH2 authorRepositoryH2;

    @Autowired
    private AuthorRepositoryM authorRepositoryM;

    @Test
    void testJob() throws Exception {
        String authorName = "Test Author";

        authorRepositoryH2.save(new AuthorH2(authorName));
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(TRANSFER_AUTHOR_JOB_NAME);

        JobParameters parameters = new JobParametersBuilder().toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        assertThat(authorRepositoryM.findByName(authorName)).isNotNull().isPresent();
    }
}