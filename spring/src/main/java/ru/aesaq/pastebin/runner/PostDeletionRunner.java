package ru.aesaq.pastebin.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.aesaq.pastebin.service.PostService;

@Component
public class PostDeletionRunner implements ApplicationRunner {
    private final PostService postService;

    public PostDeletionRunner(PostService postService) {
        this.postService = postService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            postService.deleteByDestroyTimeLessThan(System.currentTimeMillis());
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
