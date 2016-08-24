package com.yqboots.project.fss.web.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Administrator on 2016-08-15.
 */
public class FssWebUtils {
    public static HttpEntity<byte[]> downloadFile(Path file, MediaType mediaType) throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(mediaType);
        header.set("Content-Disposition", "attachment; filename=" + file.getFileName());
        header.setContentLength(Files.size(file));

        return new HttpEntity<>(Files.readAllBytes(file), header);
    }
}
