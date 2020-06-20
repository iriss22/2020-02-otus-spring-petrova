package ru.otus.spring.petrova.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.petrova.domain.mongo.AuthorM;
import ru.otus.spring.petrova.domain.mongo.BookM;
import ru.otus.spring.petrova.domain.mongo.GenreM;
import ru.otus.spring.petrova.repository.mongo.AuthorRepositoryM;
import ru.otus.spring.petrova.repository.mongo.BookRepositoryM;
import ru.otus.spring.petrova.repository.mongo.GenreRepositoryM;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {
    private final Job transferAuthorJob;
    private final Job transferGenreJob;
    private final Job transferBookJob;
    private final JobLauncher jobLauncher;
    private final AuthorRepositoryM authorRepositoryM;
    private final GenreRepositoryM genreRepositoryM;
    private final BookRepositoryM bookRepositoryM;

    @SneakyThrows
    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "t")
    public void startMigrationJobWithJobLauncher() {
        JobExecution executionAuthor = jobLauncher.run(transferAuthorJob, new JobParametersBuilder().toJobParameters());
        System.out.println(executionAuthor);
        JobExecution executionGenre = jobLauncher.run(transferGenreJob, new JobParametersBuilder().toJobParameters());
        System.out.println(executionGenre);
        JobExecution executionBook = jobLauncher.run(transferBookJob, new JobParametersBuilder().toJobParameters());
        System.out.println(executionBook);
    }

    @ShellMethod(value = "getMongoAuthor", key = "am")
    public void getMongoAuthor() {
       List<AuthorM> authorList = authorRepositoryM.findAll();
        authorList.stream().forEach(author ->System.out.println(author.getName()));
    }

    @ShellMethod(value = "getMongoGenre", key = "gm")
    public void getMongoGenre() {
        List<GenreM> genreList = genreRepositoryM.findAll();
        genreList.stream().forEach(genre ->System.out.println(genre.getName()));
    }

    @ShellMethod(value = "getMongoBook", key = "bm")
    public void getMongoBook() {
        List<BookM> bookList = bookRepositoryM.findAll();
        bookList.stream().forEach(book ->System.out.println(book.getName() + ". Comments: " + book.getComments()));
    }
}
