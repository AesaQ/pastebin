package ru.aesaq.pastebin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aesaq.pastebin.entity.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostByHash(String hash);

    void deleteByDestroyTimeLessThan(Long currentTime);
}
