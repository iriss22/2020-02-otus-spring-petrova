package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.dto.BookDto;
import ru.otus.spring.petrova.exception.AlreadyExist;
import ru.otus.spring.petrova.exception.BookNotFound;
import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.repository.BookRepository;
import ru.otus.spring.petrova.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

  private final AuthorService authorService;
  private final GenreService genreService;
  private final BookRepository bookRepository;
  private final CommentRepository commentRepository;

  @Transactional
  public BookDto addBook(String bookName, String authorName, String genreName) throws AlreadyExist {
    Author author = authorService.getOrCreate(authorName);
    Genre genre = genreService.getOrCreate(genreName);

    return saveOrUpdate(new Book(bookName, author, genre));
  }

  @Transactional(rollbackFor={DataNotFound.class, AlreadyExist.class})
  public BookDto updateBook(long bookId, String bookName) throws DataNotFound, AlreadyExist {
    Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFound(bookId));
    book.setName(bookName);
    return saveOrUpdate(book);
  }

  private BookDto saveOrUpdate(Book book) throws AlreadyExist {
    try {
      bookRepository.save(book);
      return convertToDto(book);
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
    commentRepository.deleteAllByBook_Id(id);
    bookRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public BookDto getBook(long id) throws DataNotFound {
    Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound(id));
    return convertToDto(book);
  }

  @Transactional
  public List<BookDto> getAllBooks() {
    List<Book> books = bookRepository.findAll();
    return books
        .stream()
        .map(book -> convertToDto(book))
        .collect(Collectors.toList());
  }

  private BookDto convertToDto(Book book) {
    return new BookDto(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
  }
}
