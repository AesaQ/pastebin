package ru.aesaq.pastebin.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.aesaq.pastebin.entity.Hash;
import ru.aesaq.pastebin.entity.Post;
import ru.aesaq.pastebin.entity.User;
import ru.aesaq.pastebin.manager.FileManager;
import ru.aesaq.pastebin.repository.HashRepository;
import ru.aesaq.pastebin.repository.PostRepository;
import ru.aesaq.pastebin.validator.Validator;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final HashRepository hashRepository;
    private final Validator validator;
    private final HttpSession session;
    private final FileManager fileManager;
    private final Logger log = LoggerFactory.getLogger(PostService.class);
    public PostService(PostRepository postRepository, HashRepository hashRepository, Validator validator, HttpSession session, FileManager fileManager) {
        this.postRepository = postRepository;
        this.hashRepository = hashRepository;
        this.validator = validator;
        this.session = session;
        this.fileManager = fileManager;
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
        System.out.println(newPost.getDestroyTime());
        Hash hash = hashRepository.findFirstByIsUsedFalse();

        Post resultPost = new Post(
                ((User) session.getAttribute("user")).getId(),
                newPost.getTitle(),
                hash.getHash(),
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                System.currentTimeMillis() + destroyTime
        );
        fileManager.savePostContent(hash.getHash(), newPost.getContent());
        postRepository.save(resultPost);

        hash.setUsed(true);
        hashRepository.save(hash);

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
        fileManager.deletePostContent(existingPost.getHash());
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
        postUpdate.setHash(existingPost.getHash());
        postUpdate.setUserId(existingPost.getUserId());
        postUpdate.setCreatedAt(existingPost.getCreatedAt());
        postUpdate.setUpdatedAt(System.currentTimeMillis());
        postUpdate.setDestroyTime(existingPost.getDestroyTime());
        postRepository.save(postUpdate);
        fileManager.editPostContent(existingPost.getHash(), postUpdate.getContent());
        return true;

    }
    public Post findPostByHash(String hash) {
        Optional<Post> optionalPost = postRepository.findPostByHash(hash);
        if (optionalPost.isPresent()) {

            Post result = optionalPost.get();
            String content = fileManager.getPostContent(result.getHash());
            result.setContent(content);

            if (result.getViews() == null) {
                result.setViews(0L);
                result.setDailyViews(0L);
            }
            result.setViews(result.getViews() + 1);
            result.setDailyViews(result.getDailyViews() + 1);
            postRepository.save(result);
            return result;
        } else {
            return null;
        }
    }

    public Post findTestPost() {
        return findPostByHash("NjM3NDNk");
    }

    @Transactional
    public void deleteByDestroyTimeLessThan(Long time) {
        postRepository.deleteByDestroyTimeLessThan(time);
    }

    public List<Post> getPopularPosts() {
        return postRepository.findTop30ByViews();
    }

    public void makePosts(int count) {
        for (Long i = 0L; i < count; i++) {
            Hash hash = hashRepository.findFirstByIsUsedFalse();
            Post newPost = new Post(
                    1L,
                    "test",
                    hash.getHash(),
                    System.currentTimeMillis(),
                    System.currentTimeMillis(),
                    System.currentTimeMillis() + 1000000000
            );
            newPost.setViews(i * 2L);
            postRepository.save(newPost);
            hash.setUsed(true);
            hashRepository.save(hash);
        }
    }
}
