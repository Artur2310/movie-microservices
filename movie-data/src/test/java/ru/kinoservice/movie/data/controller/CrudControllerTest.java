package ru.kinoservice.movie.data.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.kinoservice.movie.data.MovieDataApplication;
import ru.kinoservice.movie.data.model.Movie;
import org.junit.jupiter.api.Assertions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = MovieDataApplication.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class CrudControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Autowired
    CRUDController controller;

    private static String PATH_ADD = "/add";
    private static String PATH_GET_BY_ID = "/get-by-sourceId";
    private static String PATH_COUNT = "/count";

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void addMovieTest() throws Exception {
        Movie movie = Movie.builder()
                .description("0")
                .genre("genre")
                .imageUrl("imageUrl")
                .title("Test")
                .imdb(5.5f)
                .sourceId(0)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(movie);

        mockMvc.perform(post(PATH_ADD).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test"))
                .andExpect(jsonPath("$.imageUrl").value("imageUrl"))
                .andExpect(jsonPath("$.imdb").value(5.5f))
                .andExpect(jsonPath("$.genre").value("genre"))
                .andExpect(jsonPath("$.sourceId").value(0));

      /*  mockMvc.perform(get(PATH_GET_BY_ID + '/' + movie.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test"))
                .andExpect(jsonPath("$.imageUrl").value("imageUrl"))
                .andExpect(jsonPath("$.imdb").value(5.5f))
                .andExpect(jsonPath("$.genre").value("genre"))
                .andExpect(jsonPath("$.sourceId").value(0));*/

        mockMvc.perform(get(PATH_COUNT))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        mockMvc.perform(post(PATH_ADD).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH_COUNT))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    public void getPageTest() throws Exception {
        int count = 10;

        for (int i = 0; i < count; i++) {
            Movie movie = Movie.builder()
                    .id(i)
                    .title(String.valueOf(i))
                    .build();

            mockMvc.perform(post(PATH_ADD).contentType(MediaType.APPLICATION_JSON_VALUE).content(getJsonMovie(movie)))
                    .andExpect(status().isOk());
        }

        mockMvc.perform(get(PATH_COUNT))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(count)));

        Pageable pagination = PageRequest.of(0, 3, Sort.by("title").descending());

        ResponseEntity<Page<Movie>> page = controller.getPage(pagination);

        Assertions.assertNotNull(page);

    }

    private String getJsonMovie(Movie movie) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(movie);
    }
}
