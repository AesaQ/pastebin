package ru.aesaq.pastebin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aesaq.pastebin.entity.Post;
import ru.aesaq.pastebin.service.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/get/{hash}")
    public ResponseEntity<Post> getPost(@PathVariable String hash) {
        Post result = postService.findPostByHash(hash);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public String addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removePost(@PathVariable Long id) {
        boolean deleted = postService.removePost(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Post> editPost(@PathVariable Long id, @RequestBody Post post) {
        if (postService.editPost(id, post)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
