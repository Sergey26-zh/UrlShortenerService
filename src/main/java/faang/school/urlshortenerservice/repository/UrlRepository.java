package faang.school.urlshortenerservice.repository;

import faang.school.urlshortenerservice.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UrlRepository extends CrudRepository<Url, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO url_table (url, hash) VALUES (:url, :hash)", nativeQuery = true)
    void saveUrlWithHash(String url, String hash);


    @Query("SELECT u.hash FROM Url u WHERE u.createdAt < ?1")
    List<String> findOldUrlsHashes(LocalDateTime date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Url u WHERE u.createdAt < ?1")
    void deleteOldUrls(LocalDateTime date);
}
