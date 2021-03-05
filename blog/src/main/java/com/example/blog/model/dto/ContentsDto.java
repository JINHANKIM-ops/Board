package com.example.blog.model.dto;

import com.example.blog.model.domain.Contents;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ContentsDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime updatedAt;
    private int count;

    @Builder
    public ContentsDto(Contents contents) {
        this.id = contents.getId();
        this.title = contents.getTitle();
        this.content = contents.getContent();
        this.writer = contents.getWriter();
        this.registeredAt = contents.getRegisteredAt();
        this.unregisteredAt = contents.getUnregisteredAt();
        this.updatedAt = contents.getUpdatedAt();
        this.count = contents.getCount();
    }

    public ContentsDto(Long id, String title, String content, String writer,
                       LocalDateTime registeredAt, int count) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.registeredAt = registeredAt;
        this.count = count;
    }

    public Contents toEntity() {
        Contents build = Contents.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .registeredAt(LocalDateTime.now())
                .unregisteredAt(unregisteredAt)
                .updatedAt(updatedAt)
                .count(count)
                .build();
        return build;
    }

    public void updateDto(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

}
