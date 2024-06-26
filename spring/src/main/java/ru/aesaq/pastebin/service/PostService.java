package ru.aesaq.pastebin.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import ru.aesaq.pastebin.entity.Post;
import ru.aesaq.pastebin.entity.User;
import ru.aesaq.pastebin.repository.PostRepository;
import ru.aesaq.pastebin.validator.Validator;

import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final Validator validator;
    private final HttpSession session;

    public PostService(PostRepository postRepository, Validator validator, HttpSession session) {
        this.postRepository = postRepository;
        this.validator = validator;
        this.session = session;
    }

    public String addPost(Post newPost) {
        String validateResult = validator.checkPost(newPost);

        if (!validateResult.equals("ok")) {
            return validateResult;
        }

        Long destroyTime = 7889400000L; //3 месяца
        if (newPost.getDestroyTime() != null) {
            destroyTime = newPost.getDestroyTime();
        }
        Post resultPost = new Post(
                ((User) session.getAttribute("user")).getId(),
                newPost.getTitle(),
                newPost.getContent(),
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                System.currentTimeMillis() + destroyTime
        );
        postRepository.save(resultPost);

        return "the new post has been successfully created";
    }

    public boolean removePost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            return false;
        }
        Post existingPost = optionalPost.get();
        User sessionUser = (User) session.getAttribute("user");
        if (!existingPost.getUserId().equals(sessionUser.getId())) {
            return false;
        }
        postRepository.deleteById(id);
        return true;
    }

    public boolean editPost(Long id, Post postUpdate) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            return false;
        }
        Post existingPost = optionalPost.get();
        User sessionUser = (User) session.getAttribute("user");
        if (!existingPost.getUserId().equals(sessionUser.getId())) {
            return false;
        }
        postUpdate.setId(id);
        postUpdate.setUserId(existingPost.getUserId());
        postUpdate.setCreatedAt(existingPost.getCreatedAt());
        postUpdate.setUpdatedAt(System.currentTimeMillis());
        postUpdate.setDestroyTime(existingPost.getDestroyTime());
        postRepository.save(postUpdate);
        return true;

    }
}
