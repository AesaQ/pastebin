package ru.aesaq.pastebin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aesaq.pastebin.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
