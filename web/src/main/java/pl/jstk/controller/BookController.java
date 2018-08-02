package pl.jstk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

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
    @Secured("ROLE_ADMIN")
    @GetMapping("/books/remove/{bookId}")
    public String removeBookById(@RequestParam("id") Long bookId, Model model) {
        bookService.deleteBook(bookId);
        model.addAttribute("bookRemoved", "You remove the book");
        return getBooks(model);
    }

/*    @GetMapping("/find/{bookId}")
    public String getBookByParam(@RequestParam("id")String title, String author, Model model) {
        model.addAttribute("find", bookService.findBookByTitleAndAuthor(title,author));
        return "book";
    }*/

/*    @GetMapping("/books/find")
    public String findBook(Model model) {
        model.addAttribute("newBook", new BookTo());
        return "find";
    }

    @PostMapping("/books/find")
    public String find() {

        return "find";
    }

    */


    @GetMapping(value = "/books/find")
    public String findBook(Model model) {
        model.addAttribute("findBook", new BookTo());
        return "find";
    }


    @GetMapping(value = "/foundBooks")
    public String getFindResult(@RequestParam("authors") String author,
                                @RequestParam("title") String title,
                                Model model) {

        if(bookService.findBookByTitleAndAuthor(title, author).isEmpty()){
            return "thisBookDontExist";
        }
        else
        {
            model.addAttribute("findBookList", bookService.findBookByTitleAndAuthor(title, author));

            return "finded";}
    }


    @ExceptionHandler({AccessDeniedException.class})
    public String handleException(Model model) {
        model.addAttribute("error", "You can not delete the book");
        return "403";
    }

/*
//toDo
    @GetMapping(value = "/books/search")
    public String searchBook(Model model) {
        model.addAttribute("searchBook", new BookTo());
        return "search";
    }
//TODO
    @GetMapping("/findBook")
    public String getSearchResult(@RequestParam("authors") String author, @RequestParam("title") String title, Model model) {
        model.addAttribute("searchBookList", bookService.findBookByTitleAndAuthor(title, author));
        return "find";
    }

    @GetMapping("/books/find")
    public String findBook(Model model) {
        model.addAttribute("newBook", new BookTo());
        return "findBook";
    }

    @PostMapping("/books/find")
    public String find() {

        return "findBook";
    }
*/









/*    @RequestMapping(value = "/books/add", method = RequestMethod.POST)
    public ModelAndView addBook(@ModelAttribute("newBook") BookTo newBook) {
        ModelAndView modelAndView = new ModelAndView();

        if (newBook.getTitle() != null && newBook.getAuthors() != null) {
            modelAndView.addObject("newBook", bookService.saveBook(newBook));
        }
        modelAndView.setViewName(ViewNames.ADD_BOOK);
        return modelAndView;
    }

    @RequestMapping("/greeting")
    public String addBook2(Model model) {
        model.addAttribute("newBook", new BookTo());
        return ViewNames.ADD_BOOK;
    }*/


    }

