package pl.jstk.controller;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jstk.constants.ModelConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.jstk.enumerations.BookStatus;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HomeControllerTest {

    @Autowired
    private BookService bookService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();
    }

    @Test
    public void testHomePage() throws Exception {
        // given when
        ResultActions resultActions = mockMvc.perform(get("/"));
        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("welcome"))
                     .andDo(print())
                     .andExpect(model().attribute(ModelConstants.MESSAGE, HomeController.WELCOME))
                     .andExpect(content().string(containsString("")));

    }


/*    @Test
    public void testShowAllBooksPage() throws Exception {
        // given when
        List<BookTo> testBooks = new ArrayList<BookTo>();
        testBooks.add(new BookTo(1L, "Test title", "Test Author", BookStatus.FREE));
        Mockito.when(bookService.findAllBooks()).thenReturn(testBooks);

        ResultActions resultActions = mockMvc.perform(get("/books").flashAttr("bookList", testBooks));
        // then
        resultActions.andExpect(view().name("books"))
                .andExpect(model().attribute("bookList", new ArgumentMatcher<Object>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public boolean matches(Object argument) {
                        List<BookTo> books = (List<BookTo>) argument;
                        return !books.isEmpty() && books.get(0).getTitle().equals(testBooks.get(0).getTitle());
                    }
                }));
    }*/

}
