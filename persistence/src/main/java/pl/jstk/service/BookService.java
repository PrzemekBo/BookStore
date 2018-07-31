package pl.jstk.service;

import java.util.List;

import pl.jstk.to.BookTo;

public interface BookService {

    BookTo findBookById(Long id);
    List<BookTo> findAllBooks();
    List<BookTo> findBooksByTitle(String title);
    List<BookTo> findBooksByAuthor(String author);

    BookTo saveBook(BookTo book);
    void deleteBook(Long id);

    public List<BookTo> findBookByTitleAndAuthor(String title, String author);
}
