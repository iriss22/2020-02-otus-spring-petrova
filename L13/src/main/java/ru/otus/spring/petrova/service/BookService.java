package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Service
@RequiredArgsConstructor
public class BookService {

  private final AuthorRepository authorRepository;
  private final GenreRepository genreRepository;
  private final BookRepository bookRepository;

  public void addBook(String bookName, long authorId, long genreId) throws DataNotFound, AlreadyExist {
    Author author = authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFound(authorId));
    Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new GenreNotFound(genreId));

    saveOrUpdate(new Book(bookName, author, genre));
  }

  public void updateBook(long bookId, String bookName) throws DataNotFound, AlreadyExist {
    Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFound(bookId));
    book.setName(bookName);
    saveOrUpdate(book);
  }

  private void saveOrUpdate(Book book) throws AlreadyExist {
    try {
      bookRepository.save(book);
    } catch (RuntimeException e) {
      if (e.getCause() instanceof ConstraintViolationException) {
        throw new AlreadyExist("book", e);
      }
      throw e;
    }
  }

  @Transactional
  public void deleteBook(long id) throws DataNotFound {
    bookRepository.findById(id).orElseThrow(() -> new BookNotFound(id));
    bookRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public Book getBook(long id) throws DataNotFound {
    Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound(id));
    return book;
  }

  @Transactional(readOnly = true)
  public String getBookInfo(long id) throws DataNotFound {
    Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound(id));
    return book.toString();
  }
}
