package ru.kinoservice.image.downloader.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.kinoservice.image.downloader.exception.ImageDownloadException;

public class ImageDownloaderServiceTest {

    DownloaderSerice service = new ImageDownloaderService();

    private static final String TEST_CORRECT_IMAGE = "https://homepages.cae.wisc.edu/~ece533/images/airplane.png";
    private static final String TEST_INCORRECT_IMAGE = "https://test.ru/~ece533/images/airplane.png";

    @Test
    public void serviceDownloadImage(){
        Assertions.assertNotNull(service.download(TEST_CORRECT_IMAGE));
    }

    @Test
    public void serviceDownloadImageException(){
        Assertions.assertThrows(ImageDownloadException.class, () -> service.download(TEST_INCORRECT_IMAGE));
    }
}
