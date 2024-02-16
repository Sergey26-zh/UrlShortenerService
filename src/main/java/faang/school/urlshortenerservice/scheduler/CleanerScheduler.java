package faang.school.urlshortenerservice.scheduler;

import faang.school.urlshortenerservice.repository.HashRepository;
import faang.school.urlshortenerservice.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
@EnableScheduling
@RequiredArgsConstructor
public class CleanerScheduler {
    private final HashRepository hashRepository;
    private final UrlRepository urlRepository;

    @Scheduled(cron = "${cron.expression}")
    public void cleanAndMoveUrls() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        List<String> hashesToDelete = urlRepository.findOldUrlsHashes(oneYearAgo);
        if (!hashesToDelete.isEmpty()) {
            urlRepository.deleteOldUrls(oneYearAgo);
            hashRepository.saveAllHashes(hashesToDelete);
        }
    }
}
