package ru.kinoservice.image.downloader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kinoservice.image.downloader.annotation.UrlFormatConstraint;
import ru.kinoservice.image.downloader.exception.ImageDownloadException;
import ru.kinoservice.image.downloader.service.DownloaderSerice;

import java.awt.image.BufferedImage;
import java.util.Optional;

@RestController
@RequestMapping(value = "/download")
@Validated
public class ImageDownloadController {

    @Autowired
    private DownloaderSerice downloaderSerice;

    @GetMapping(produces = "image/png")
    ResponseEntity<byte []> download(@UrlFormatConstraint @RequestParam(name = "url", required = false) String url) {
        return Optional.ofNullable(downloaderSerice.download(url))
                .map(image -> ResponseEntity.ok().body(image)
                ).orElseThrow(() -> new ImageDownloadException());
    }

}
