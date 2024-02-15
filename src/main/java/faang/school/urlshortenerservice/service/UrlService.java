package faang.school.urlshortenerservice.service;

import faang.school.urlshortenerservice.dto.UrlDto;
import faang.school.urlshortenerservice.mapper.UrlMapper;
import faang.school.urlshortenerservice.repository.UrlCacheRepository;
import faang.school.urlshortenerservice.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final UrlCacheRepository urlCacheRepository;
    private final UrlMapper urlMapper;
    private final HashCache hashCache;

    @Transactional
    public void getShortUrl(UrlDto urlDto) {
        String url = urlDto.getUrl();
        String hash = hashCache.get(url);

        urlRepository.saveUrlWithHash(url, hash);
        urlCacheRepository.saveUrlWithHash(url, hash);
    }
}
