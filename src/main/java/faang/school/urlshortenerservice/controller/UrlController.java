package faang.school.urlshortenerservice.controller;

import faang.school.urlshortenerservice.dto.UrlDto;
import faang.school.urlshortenerservice.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/url")
public class UrlController {
    private final UrlService urlService;

    @PostMapping
    public void getShortUrl(UrlDto urlDto) {
        urlService.getShortUrl(urlDto);
    }

    @GetMapping("/{hash}")
    public void redirect(@PathVariable String hash, HttpServletResponse response) throws IOException {
        String longUrl = String.valueOf(urlService.getOriginalUrl(hash));
        if (longUrl != null) {
            response.sendRedirect(longUrl);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
