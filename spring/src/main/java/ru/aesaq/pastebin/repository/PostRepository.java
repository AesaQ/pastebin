package ru.aesaq.pastebin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aesaq.pastebin.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostByHash(String hash);

    @Query("SELECT p FROM Post p ORDER BY p.views DESC LIMIT 30")
    List<Post> findTop30ByViews();

    void deleteByDestroyTimeLessThan(Long currentTime);
}
