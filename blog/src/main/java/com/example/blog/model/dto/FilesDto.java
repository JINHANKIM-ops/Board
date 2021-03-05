package com.example.blog.model.dto;

import com.example.blog.model.domain.Files;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Date - 클래스 안의 모든 private 필드에 대해 Setter&Getter를 생성해 주며
//        클래스 내에 @ToString 과 @EqualsAndHashCode를 적용시켜 오버라이드 해준다
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString //JSON형식으로 출력
@Builder
public class FilesDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileOriName;
    private String fileUrl;

    public Files toEntity(){
        Files build = Files.builder()
                .id(id)
                .fileOriName(fileOriName)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .build();
        return build;
    }

    public FilesDto(Files files){
        this.id = files.getId();
        this.fileOriName = files.getFileOriName();
        this.fileName = files.getFileName();
        this.fileUrl = files.getFileUrl();
    }
}
