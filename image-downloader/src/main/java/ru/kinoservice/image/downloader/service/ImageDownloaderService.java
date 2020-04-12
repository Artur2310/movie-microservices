package ru.kinoservice.image.downloader.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.kinoservice.image.downloader.exception.ImageDownloadException;
import ru.kinoservice.image.downloader.exception.ValidateUrlException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ImageDownloaderService implements DownloaderSerice {

    private static Logger logger = LoggerFactory.getLogger(ImageDownloaderService.class);

    @Override
    public byte[] download(String url) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){

            BufferedImage bufferedImage = ImageIO.read(new URL(url));
            ImageIO.write(bufferedImage, getFormat(url), baos);
            return baos.toByteArray();

        } catch (MalformedURLException e) {
            logger.error("Create URL from : " + url, e);
            throw new ValidateUrlException();
        } catch (IOException e) {
            logger.error("Download error : " + url, e);
            throw new ImageDownloadException();
        }
    }

    private String getFormat(String url) {
        return url.substring(url.lastIndexOf('.') + 1, url.length());
    }
}
