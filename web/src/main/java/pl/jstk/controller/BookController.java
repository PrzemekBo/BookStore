package pl.jstk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.jstk.constants.ViewNames;
import pl.jstk.mapper.BookMapper;
import pl.jstk.repository.BookRepository;
import pl.jstk.service.BookService;
import pl.jstk.service.impl.BookServiceImpl;
import pl.jstk.to.BookTo;

import java.util.List;

@Controller
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
/*
    @RequestMapping
    public ModelAndView allBooks() {
        ModelAndView mav = new ModelAndView();
       // modelAndView.setViewName(ViewNames.BOOKS);
      //  modelAndView.addObject("searchedBook", new BookTo());
        mav.addObject("bookList", bookService.findAllBooks());
        return mav;
    }


    @RequestMapping("/book")
    public ModelAndView bookByID(@RequestParam("id") Long id){
        ModelAndView mav= new ModelAndView();
        mav.addObject("book",bookService.findBookById(id));
        return mav;
    }
 */
/*   @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@RequestParam("newBook")BookTo newBook){
        ModelAndView mav= new ModelAndView();

        if (newBook.getTitle()!=null&&newBook.getTitle()!=null){
            mav.addObject("newBook",bookService.saveBook(newBook));
        }
        return mav;

    }*//*


    @RequestMapping(value = "/greeting", method = RequestMethod.POST)
    public ModelAndView addBook(@ModelAttribute("greeting") BookTo newBook) {
        ModelAndView modelAndView = new ModelAndView();

        if (newBook.getTitle() != null && newBook.getAuthors() != null) {
            modelAndView.addObject("greeting", bookService.saveBook(newBook));
        }
       // modelAndView.setViewName(ViewNames.ADDED);
        return modelAndView;
    }

*/
/*    @RequestMapping("/add")
    public String addBook2(Model model) {
        model.addAttribute("newBook", new BookTo());
        return ViewNames.ADD_BOOK;
    }*/

    @GetMapping("/books")
    public String getBooks(Model model) {
        model.addAttribute("bookList", bookService.findAllBooks());
        return "books";
    }

    @GetMapping("/books/{bookId}")
    public String getBookById(@RequestParam("id") Long bookId, Model model) {
        model.addAttribute("book", bookService.findBookById(bookId));
        return "book";
    }

    @GetMapping("/books/add")
    public String addBook(Model model) {
        model.addAttribute("newBook", new BookTo());
        return "addBook";
    }

    @PostMapping("/greeting")
    public String addBook(@ModelAttribute("newBook") BookTo book, Model model) {
        bookService.saveBook(book);
        return getBooks(model);
    }


    @GetMapping("/books/remove/{bookId}")
    public String removeBookById(@RequestParam("id") Long bookId, Model model) {
        bookService.deleteBook(bookId);
        model.addAttribute("bookRemoved", "Book was successfully removed.");
        return getBooks(model);
    }






}

