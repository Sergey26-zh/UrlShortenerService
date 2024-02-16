package faang.school.urlshortenerservice.repository;

import faang.school.urlshortenerservice.model.Hash;
import feign.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashRepository extends CrudRepository<Hash, String> {
    @Query(nativeQuery = true, value = "SELECT nextval('unique_number_sequence') + generate_series(1, :limit) - 1")
    List<Long> getUniqueNumbers(@Param("limit") int limit);

    @Query(value = "DELETE FROM hash WHERE id IN (SELECT id FROM hash ORDER BY random() LIMIT :n) RETURNING hash_value", nativeQuery = true)
    List<String> getHashBatch(@Param("n") int n);

    List<String> saveAllHashes(List<String> hashes);
}
