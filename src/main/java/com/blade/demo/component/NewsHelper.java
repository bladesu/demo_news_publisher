package com.blade.demo.component;

import com.blade.demo.model.message.News;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class NewsHelper {

    @SneakyThrows
    public News create(String source, String author, String title, String description) {
        News news = new News();
        news.setAuthor(author);
        news.setDescription(description);
        news.setTitle(title);
        news.setSource(source);
        news.setId(createSHAHash(news.toString()));
        return news;
    }

    private String createSHAHash(String input) throws NoSuchAlgorithmException {
        String hashText = null;
        // MessageDigest is not thread-safe
        byte[] messageDigest = MessageDigest.getInstance("SHA-256")
                .digest(input.getBytes(StandardCharsets.UTF_8));
        hashText = convertToHex(messageDigest);
        return hashText;
    }

    private String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }

}
