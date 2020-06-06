package ru.otus.spring.petrova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.repository.AuthorRepository;
import ru.otus.spring.petrova.repository.BookRepository;
import ru.otus.spring.petrova.repository.GenreRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class Main {

  public static void main(String str[]) {
    ApplicationContext context = SpringApplication.run(Main.class, str);
    AuthorRepository repository = context.getBean(AuthorRepository.class);
    repository.deleteAll();
    repository.save(new Author("test"));
  }

  @Bean
  public RouterFunction<ServerResponse> composedRoutes(AuthorRepository authorRepository, BookRepository bookRepository,
                                                       GenreRepository genreRepository) {
    return route()
        .GET("/authors", accept(APPLICATION_JSON),
            request -> ok().contentType(APPLICATION_JSON).body(authorRepository.findAll(), Author.class)
        )
        .GET("/authors", accept(APPLICATION_JSON),
            request -> authorRepository.findByName(request.queryParam("name").orElse(""))
                .flatMap(author -> ok().contentType(APPLICATION_JSON).body(fromValue(author)))
                .switchIfEmpty(ServerResponse.notFound().build())
        )
        .POST("/authors", accept(APPLICATION_JSON),
            request -> request.bodyToMono(Author.class)
                .flatMap(authorToSave -> authorRepository.findByName(authorToSave.getName())
                    .map(r -> Tuples.of(false, new Author(null)))
                    .defaultIfEmpty(Tuples.of(true, authorToSave)))
                .flatMap(r -> {
                  if (r.getT1()) {
                    return authorRepository.save(r.getT2());
                  }
                  return Mono.empty();
                })
                .flatMap(author -> ServerResponse.status(HttpStatus.CREATED).bodyValue(author))
                .switchIfEmpty(ServerResponse.badRequest().bodyValue("author already exist"))
        )

        .GET("/genres", accept(APPLICATION_JSON),
            request -> ok().contentType(APPLICATION_JSON).body(genreRepository.findAll(), Genre.class)
        )
        .POST("/genres", accept(APPLICATION_JSON),
            request -> request.bodyToMono(Genre.class)
                .flatMap(genreToSave -> genreRepository.findByName(genreToSave.getName())
                    .map(r -> Tuples.of(false, new Genre(null)))
                    .defaultIfEmpty(Tuples.of(true, genreToSave)))
                .flatMap(r -> {
                  if (r.getT1()) {
                    return genreRepository.save(r.getT2());
                  }
                  return Mono.empty();
                })
                .flatMap(author -> ServerResponse.status(HttpStatus.CREATED).bodyValue(author))
                .switchIfEmpty(ServerResponse.badRequest().bodyValue("genre already exist"))
        )

        .GET("/books", accept(APPLICATION_JSON),
            request -> ok().contentType(APPLICATION_JSON).body(bookRepository.findAll(), Book.class)
        )
        .GET("/books/{id}", accept(APPLICATION_JSON),
            request -> bookRepository.findById(request.pathVariable("id"))
                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book)))
                .switchIfEmpty(ServerResponse.notFound().build())
        )
        .POST("/books", accept(APPLICATION_JSON),
            request ->
                request.bodyToMono(Book.class)
                .flatMap(bookToSave ->
                    Mono.zip(
                        genreRepository.findById(bookToSave.getGenreId()).map(g -> "").defaultIfEmpty("genre not found"),
                        authorRepository.findById(bookToSave.getAuthorId()).map(g -> "").defaultIfEmpty("author not found"),
                        bookRepository.findByName(bookToSave.getName()).map(g -> "book already exist").defaultIfEmpty("")
                    ).flatMap(checkResult -> {
                      if (checkResult.getT1().isEmpty() && checkResult.getT2().isEmpty() && checkResult.getT3().isEmpty()) {
                        return Mono.just(Tuples.of("", bookToSave));
                      } else {
                        return Mono.just(Tuples.of(
                            Stream.of(checkResult.getT1(), checkResult.getT2(), checkResult.getT3())
                                .filter(v -> !v.isEmpty())
                                .collect(Collectors.joining(", ")),
                            bookToSave));
                      }
                    })
                )
                .flatMap(checkResult -> {
                  if (checkResult.getT1().isEmpty()) {
                    return bookRepository.save(checkResult.getT2()).map(v -> "saved").defaultIfEmpty("error to save");
                  }
                  return Mono.just(checkResult.getT1());
                })
                .flatMap(result -> {
                  if (!result.equals("saved")) {
                    return ServerResponse.badRequest().bodyValue(result);
                  } else {
                    return ServerResponse.status(HttpStatus.CREATED).bodyValue(result);
                  }
                })
        )
        .PUT("/books", accept(APPLICATION_JSON),
            request ->
                request.bodyToMono(Book.class)
                .flatMap(bookToUpdate -> bookRepository.findByName(bookToUpdate.getName())
                    .map(r -> Tuples.of(false, bookToUpdate))
                    .defaultIfEmpty(Tuples.of(true, bookToUpdate)))
                .flatMap(bookResult -> {
                  if (bookResult.getT1()) {
                    return bookRepository.save(bookResult.getT2());
                  }
                  return Mono.empty();
                })
                .flatMap(book -> ServerResponse.status(HttpStatus.OK).bodyValue(book))
                .switchIfEmpty(ServerResponse.badRequest().bodyValue("book with this name already exist"))
        )
        .DELETE("/books/{id}", accept(APPLICATION_JSON),
            request ->
                bookRepository.deleteById(request.pathVariable("id"))
                .then(ServerResponse.status(HttpStatus.OK).build())
        )
        .build();
  }
}
