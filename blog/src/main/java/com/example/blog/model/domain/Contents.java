package com.example.blog.model.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String writer;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime updatedAt;
    private int count;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contents")
    private List<ContentsItems> contentsItemsList;

    @Builder
    public Contents(String title, String writer, String content,
                    LocalDateTime registeredAt, LocalDateTime updatedAt, LocalDateTime unregisteredAt,
                    int count) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.registeredAt = registeredAt;
        this.unregisteredAt = unregisteredAt;
        this.updatedAt = updatedAt;
        this.count = count;
    }

    public void update(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }
}
