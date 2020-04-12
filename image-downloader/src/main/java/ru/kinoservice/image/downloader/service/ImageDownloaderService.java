package ru.kinoservice.image.downloader.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ImageDownloaderService implements DownloaderSerice {

    private static Logger logger = LoggerFactory.getLogger(ImageDownloaderService.class);

    @Override
    public byte [] download(String url) {
        logger.error("Errortest");
        logger.warn("Warn");
        return null;
    }
}
