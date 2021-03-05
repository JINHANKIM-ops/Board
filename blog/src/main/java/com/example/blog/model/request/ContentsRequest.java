package com.example.blog.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ContentsRequest {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private int count;


    @Builder
    public ContentsRequest(String title, String content, String writer,
                           LocalDateTime registeredAt, LocalDateTime updatedAt, int count){
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.registeredAt = registeredAt;
        this.updatedAt = updatedAt;
        this.count = count;

    }



}
