package kz.academy.kemelacademy.controllers;

import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-20
 * @project kemelacademy
 */
public class MediaTypeUtils {
    
    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        // application/pdf
        // application/xml
        // image/gif, ...
        String mineType = servletContext.getMimeType(fileName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
    
}
