package ru.kinoservice.image.downloader.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.kinoservice.image.downloader.ImageDownloaderApplication;
import ru.kinoservice.image.downloader.exception.ImageDownloadException;
import ru.kinoservice.image.downloader.service.DownloaderSerice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ImageDownloaderApplication.class)
@WebAppConfiguration
public class ImageDownloaderControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @MockBean
    DownloaderSerice downloaderSerice;

    @Autowired
    @InjectMocks
    ImageDownloadController imageDownloadController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Mockito.when(downloaderSerice.download(CORRECT_IMAGE_URL)).thenReturn(new byte[1]);
        Mockito.when(downloaderSerice.download(ERROR_IMAGE_URL)).thenThrow(new ImageDownloadException());
        Mockito.when(downloaderSerice.download(EMPTY_IMAGE_URL)).thenReturn(null);
    }

    private static String CORRECT_IMAGE_URL = "https://test.com/test.jpg";
    private static String ERROR_IMAGE_URL = "https://error.com/error.jpg";
    private static String INCORRECT_IMAGE_URL = "https://test.com/image";
    private static String EMPTY_IMAGE_URL = "https://empty.com/image.png";
    private static Integer OK_STATUS = 200;
    private static Integer INTERNAL_SERVER_ERROR_STATUS = 500;
    private static Integer BAD_REQUEST_STATUS = 400;

    @Test
    public void controllerMustReturnSuccessResult() throws Exception {
        mockMvc.perform(get("/download").param("url", CORRECT_IMAGE_URL))
                .andExpect(status().is(OK_STATUS));
    }

    @Test
    public void controllerMustReturnException() throws Exception {
        mockMvc.perform(get("/download?url="+ERROR_IMAGE_URL))
                .andExpect(status().is(INTERNAL_SERVER_ERROR_STATUS));

        mockMvc.perform(get("/download").param("url",EMPTY_IMAGE_URL))
                .andExpect(status().is(INTERNAL_SERVER_ERROR_STATUS));
    }

    @Test
    public void controllerMustReturnValidateException() throws Exception {
        mockMvc.perform(get("/download").param("url", INCORRECT_IMAGE_URL))
                .andExpect(status().is(BAD_REQUEST_STATUS));
    }

}
