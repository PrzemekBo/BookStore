package pl.jstk.controller;


import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

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
}

