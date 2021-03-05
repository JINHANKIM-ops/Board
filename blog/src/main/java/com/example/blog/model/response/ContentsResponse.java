package com.example.blog.model.response;

import com.example.blog.model.domain.Contents;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ContentsResponse {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private int count;

    public ContentsResponse(Contents contents){
        this.id = contents.getId();
        this.title = contents.getTitle();
        this.content = contents.getContent();
        this.writer = contents.getWriter();
        this.registeredAt = contents.getRegisteredAt();
        this.updatedAt = LocalDateTime.now();
        this.count = getCount();

    }

}
