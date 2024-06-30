package ru.aesaq.pastebin.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    public Post(Long user_id, String title, String hash, Long created_at, Long updated_at, Long destroyTime) {
        this.user_id = user_id;
        this.title = title;
        this.hash = hash;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.destroyTime = destroyTime;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "title")
    private String title;
    private String content;
    @Column(name = "created_at")
    private Long created_at;
    @Column(name = "updated_at")
    private Long updated_at;
    @Column(name = "destroy_time")
    private Long destroyTime;
    @Column(name = "hash")
    private String hash;

    public Post() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Long created_at) {
        this.created_at = created_at;
    }

    public Long getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Long updated_at) {
        this.updated_at = updated_at;
    }

    public Long getDestroyTime() {
        return destroyTime;
    }

    public void setDestroyTime(Long destroyTime) {
        this.destroyTime = destroyTime;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}


