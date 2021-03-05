package com.example.blog.service;

import com.example.blog.model.domain.Contents;
import com.example.blog.model.domain.Files;
import com.example.blog.model.dto.FilesDto;
import com.example.blog.repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    private FilesRepository filesRepository;

    //파일 정보 저장
    @Transactional
    public Long saveFile(FilesDto filesDto){
        return filesRepository.save(filesDto.toEntity()).getId();
    }
    //파일 정보 가져오기
    @Transactional
    public FilesDto getFile(Long id) {
        Files files = filesRepository.findById(id).get();

        FilesDto filesDto = FilesDto.builder()
                .id(id)
                .fileOriName(files.getFileOriName())
                .fileName(files.getFileName())
                .fileUrl(files.getFileUrl())
                .build();
        return filesDto;
    }
    //파일 목록
    @Transactional
    public List<FilesDto> findAll(){
        return filesRepository.findAll()
                .stream()
                .map(FilesDto::new)
                .collect(Collectors.toList());
    }
    //상세조회
    @Transactional
    public Files filesDetail(Long id) {

        Files files = filesRepository.findById(id)
                .orElseThrow(() -> new IllegalAccessError("[id=" + id + "] 번 게시글이 존재하지 않습니다."));

        return filesRepository.getOne(id);
    }

}
