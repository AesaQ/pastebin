package ru.aesaq.pastebin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aesaq.pastebin.entity.Hash;

import java.util.Optional;

@Repository
public interface HashRepository extends JpaRepository<Hash, Long> {
    Optional<Hash> findByHash(String hash);

    Long countByIsUsedFalse();

    Hash findFirstByIsUsedFalse();
}
