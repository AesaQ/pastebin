package ru.aesaq.pastebin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.aesaq.pastebin.entity.Post;

import java.util.List;

@Service
public class CacheService {
    private final PostService postService;
    private final Logger log = LoggerFactory.getLogger(PostService.class);

    public CacheService(PostService postService) {
        this.postService = postService;
    }

    @Cacheable("popularPosts")
    public List<Post> getPopularPosts() {
        int i = 1;
        for (Post post : postService.getPopularPosts()) {
            log.info("get post: id = " + post.getId() + "; views = " + post.getViews());
            i++;
        }
        return postService.getPopularPosts();
    }

    @CacheEvict("popularPosts")
    public List<Post> updatePopularPosts() {
        int i = 1;
        for (Post post : postService.getPopularPosts()) {
            log.info("update post: id = " + post.getId() + "; views = " + post.getViews() + "; index = " + i);
            i++;
        }
        return postService.getPopularPosts();
    }
}
