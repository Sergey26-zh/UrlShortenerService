package faang.school.urlshortenerservice.repository;

import faang.school.urlshortenerservice.model.Hash;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface UrlCacheRepository extends CrudRepository<Hash, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO url_table (url, hash) VALUES (:url, :hash)", nativeQuery = true)
    void saveUrlWithHash(String url, String hash);
}
