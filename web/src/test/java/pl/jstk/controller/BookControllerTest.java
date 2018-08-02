package pl.jstk.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.jstk.BookApplication;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.entity.UserEntity;
import pl.jstk.enumerations.BookStatus;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {


    @MockBean
    private BookService bookServiceMock;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void shouldfindBooksAsAdmin() throws Exception {
        //given
        List<BookTo> listOfBook = new ArrayList<>();
        listOfBook.add(new BookTo());

        //when
        when(bookServiceMock.findAllBooks()).thenReturn(listOfBook);
        ResultActions resultActions = mockMvc.perform(get("/books"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andExpect(model().attribute("bookList", listOfBook));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void shouldfindBooksAsAUser() throws Exception {
        //given
        List<BookTo> listOfBook = new ArrayList<>();
        listOfBook.add(new BookTo());

        //when
        when(bookServiceMock.findAllBooks()).thenReturn(listOfBook);
        ResultActions resultActions = mockMvc.perform(get("/books"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andExpect(model().attribute("bookList", listOfBook));
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void shouldFindBookAsAdmin() throws Exception {
        //given
        BookTo book = new BookTo();
        long id = 1;

        //when
        when(bookServiceMock.findBookById(id)).thenReturn(book);
        ResultActions resultActions = mockMvc.perform(get("/books/id").param("id", "1"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("book"))
                .andExpect(model().attribute("book", book));
    }

    @Test
    public void shouldDeleteBookInvalidUser() throws Exception {
        // given
        BookTo book1 = new BookTo((long) 1, "TEstName", "TestAuthor", BookStatus.FREE);


        // when
        ResultActions resultActions = mockMvc.perform(get("/books/delete?id=1"));
        resultActions.andExpect(status().isFound());

        // then
        verifyNoMoreInteractions(bookServiceMock);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void shouldAddBook() throws Exception {
        //given
        BookTo book = new BookTo();
        book.setId(6L);

        //when
        when(bookServiceMock.saveBook(book)).thenReturn(book);
        ResultActions resultActions = mockMvc.perform(post("/greeting").with(user("admin")).flashAttr("newBook", book));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(model().attribute("bookAdded", "Book was successfully added"))
                .andExpect(model().attribute("book", book));
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void shouldReturnAddBookView() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(get("/books/add"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("addBook"))
                .andExpect(model().attributeExists("newBook"));
    }


    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void shouldNotRemoveBookAsUser() throws Exception {
        //given
        List<BookTo> books = new ArrayList<>();
        long id = 1;
        books.add(new BookTo());

        //when
        //when(bookServiceMock.deleteBook(id)).thenReturn(books.get(0));
        ResultActions resultActions = mockMvc.perform(delete("books/remove/bookId").param("id", "1"));

        //then
        resultActions.andExpect(status().isForbidden());
              /*  .andExpect(view().name("books"))
                .andExpect(model().attribute("bookRemoved", "Book was successfully removed."));*/
    }



/*
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testSearchPagePost() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get("/books"));
        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testSearchPagedPost() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get("/greeting"));
        // then
        resultActions.andExpect(status().isMethodNotAllowed());
    }
*/


    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void shouldRequestGETonGetBookMethod()throws Exception{
        // when
        ResultActions resultActions = mockMvc.perform(get("/books"));
        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void shouldRequestGETongetBookById()throws Exception{
        // when
        ResultActions resultActions = mockMvc.perform(get("/books?id=1"));
        // then
        resultActions.andExpect(status().isOk());
    }




    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void shouldRequestGETonRemoveBookById()throws Exception{

        BookTo deleteBook= new BookTo(1L,"mama","tata",BookStatus.FREE);
        // when
        when(bookServiceMock.findBookById(1L)).thenReturn(deleteBook);
        ResultActions resultActions = mockMvc.perform(get("/books/remove?id=1"));
        // then
        resultActions.andExpect(status().isOk());
    }


        @Test
    @WithMockUser(username = "user", roles = "USER")
    public void shouldRequestGETonFindBook()throws Exception{
        // when
        ResultActions resultActions = mockMvc.perform(get("/books/find"));
        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void shouldRequestGETongetBookBywIds()throws Exception{
        // when
        List<BookTo>listoOFbook= new ArrayList<>();
        BookTo bookToFind=new BookTo(1L,"mama","tata",BookStatus.FREE);
        listoOFbook.add(bookToFind);
        when(bookServiceMock.findBookByTitleAndAuthor("mama","tata")).thenReturn(listoOFbook);
        ResultActions resultActions = mockMvc.perform(get("/foundBooks?title=mama&authors=tata"));
        // then
        resultActions.andExpect(status().isOk());
    }






    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void shouldRequestPOSTonAddBook() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get("/greeting"));
        // then
        resultActions.andExpect(status().isMethodNotAllowed());
    }






}



