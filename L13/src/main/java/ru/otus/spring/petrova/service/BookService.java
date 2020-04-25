package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.petrova.dto.AuthorDto;
import ru.otus.spring.petrova.dto.BookDto;
import ru.otus.spring.petrova.dto.CommentDto;
import ru.otus.spring.petrova.dto.GenreDto;
import ru.otus.spring.petrova.repository.AuthorRepository;
import ru.otus.spring.petrova.exception.AlreadyExist;
import ru.otus.spring.petrova.exception.AuthorNotFound;
import ru.otus.spring.petrova.exception.BookNotFound;
import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.repository.BookRepository;
import ru.otus.spring.petrova.repository.GenreRepository;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.exception.GenreNotFound;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

  private final AuthorRepository authorRepository;
  private final GenreRepository genreRepository;
  private final BookRepository bookRepository;

  public String addBook(String bookName, String authorId, String genreId) throws DataNotFound, AlreadyExist {
    checkBookExist(bookName);
    Author author = authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFound(authorId));
    Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new GenreNotFound(genreId));

    return bookRepository.save(new Book(bookName, author.getId(), genre.getId())).getId();
  }

  public void updateBook(String bookId, String bookName) throws DataNotFound, AlreadyExist {
    checkBookExist(bookName);
    Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFound(bookId));
    book.setName(bookName);
    bookRepository.save(book);
  }

  private void checkBookExist(String name) throws AlreadyExist {
    Optional<Book> book = bookRepository.findByName(name);
    if (book.isPresent()) {
      throw new AlreadyExist("book");
    }
  }

  @Transactional
  public void deleteBook(String id) throws DataNotFound {
    bookRepository.findById(id).orElseThrow(() -> new BookNotFound(id));
    bookRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public BookDto getBook(String id) throws DataNotFound {
    Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound(id));
    Author author = authorRepository.findById(book.getAuthorId()).orElseThrow(() -> new AuthorNotFound(id));
    Genre genre = genreRepository.findById(book.getGenreId()).orElseThrow(() -> new GenreNotFound(id));

    return new BookDto(book.getId(), book.getName(), new AuthorDto(author.getId(), author.getName()), new GenreDto(genre.getId(),
        genre.getName()), null);
  }

  @Transactional(readOnly = true)
  public String getBookInfo(String id) throws DataNotFound {
    Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound(id));
    return book.toString();
  }

  @Transactional
  public void addComment(String bookId, String comment) throws DataNotFound {
    Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFound(bookId));
    book.addComment(comment);
    bookRepository.save(book);
  }

  @Transactional(readOnly = true)
  public List<String> getComments(String bookId) throws DataNotFound {
    Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFound(bookId));
    return book.getComments();
  }
}
