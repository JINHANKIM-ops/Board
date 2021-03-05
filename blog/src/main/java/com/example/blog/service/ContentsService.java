package com.example.blog.service;


import com.example.blog.model.domain.Contents;
import com.example.blog.model.dto.ContentsDto;
import com.example.blog.model.response.ContentsResponse;
import com.example.blog.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //초기화 되지 않은 final, @NonNull이 붙은 필드에 대해 생성자를 생성
public class ContentsService {

    private final ContentsRepository contentsRepository;

    //등록
    public Long save(ContentsDto contentsDto) {

        return contentsRepository.save(contentsDto.toEntity()).getId();
    }
    //전체 목록 조회
    @Transactional
    public Page<Contents> findAll(Pageable pageable) {

        return contentsRepository.findAll(pageable);
    }

    //수정
    @SneakyThrows //예외 클래스를 파라미터로 입력받을 때 사용
    @Transactional
    public void update(Long id, ContentsDto contentsDto) {
            Contents contents = contentsRepository.findById(id).orElseThrow(()->
                new IllegalAccessException("해달 게시글이 존쟈하지 않습니다." + id));
            contents.update(contentsDto.getTitle(), contentsDto.getContent(), contentsDto.getWriter());
    }

    //삭제
    @Transactional
    public void delete(Long id) {

        Contents contents = contentsRepository.findById(id)
                .orElseThrow(() -> new IllegalAccessError("[id=" + id + "] 번 게시글이 존재하지 않습니다."));

        contentsRepository.delete(contents);
    }

    //상세조회
    @Transactional
    public Contents detail(Long id) {

        Contents contents = contentsRepository.findById(id)
                .orElseThrow(() -> new IllegalAccessError("[id=" + id + "] 번 게시글이 존재하지 않습니다."));

        return contentsRepository.getOne(id);
    }

}
