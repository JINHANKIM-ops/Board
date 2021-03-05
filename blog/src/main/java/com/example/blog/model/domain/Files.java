package com.example.blog.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileOriName;
    private String fileUrl;

    @Builder
    public Files(Long id, String fileOriName, String fileName, String fileUrl){
        this.id = id;
        this.fileOriName = fileOriName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

}
