package faang.school.urlshortenerservice.service;

import faang.school.urlshortenerservice.encoder.Base62Encoder;
import faang.school.urlshortenerservice.repository.HashRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

@Component
public class HashGenerator {
    private final HashRepository hashRepository;
    private final Base62Encoder base62Encoder;

    @Value("${batch.size}")
    private final int batchSize;
    private final Executor customThreadPool;

    @Autowired
    public HashGenerator(HashRepository hashRepository, Base62Encoder base62Encoder,
                         @Value("${batch.size}") int batchSize, Executor customThreadPool) {
        this.hashRepository = hashRepository;
        this.base62Encoder = base62Encoder;
        this.batchSize = batchSize;
        this.customThreadPool = customThreadPool;
    }

    @Async("customThreadPool")
    public void generateBatch() {
        List<Long> uniqueNumbers = hashRepository.getUniqueNumbers(batchSize);
        List<String> hashes = base62Encoder.encode(uniqueNumbers);
        hashRepository.saveAllHashes(hashes);
    }
}
