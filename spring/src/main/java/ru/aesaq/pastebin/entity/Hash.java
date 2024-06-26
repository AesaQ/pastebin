package ru.aesaq.pastebin.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hashes")
public class Hash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "hash")
    private String hash;
    @Column(name = "is_used")
    private boolean isUsed;

    public Hash(String hash) {
        this.hash = hash;
    }

    public Hash() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
