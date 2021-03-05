package com.example.blog.controller;

import com.example.blog.generator.MD5Generator;
import com.example.blog.model.domain.Contents;
import com.example.blog.model.domain.Files;
import com.example.blog.model.dto.ContentsDto;
import com.example.blog.model.dto.FilesDto;
import com.example.blog.repository.ContentsRepository;
import com.example.blog.service.ContentsService;
import com.example.blog.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContentsController {

    @Autowired
    private ContentsService contentsService;
    @Autowired
    private ContentsRepository contentsRepository;
    @Autowired
    private FileService fileService;

    //URL 주소에 데이터 값이 붙어 적은 양의 데이터를 전송
    //example.com/list.html?value='111'
    @GetMapping("/read")
    public String read(Model model,
                      @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC)
                               Pageable pageable){

        Page<Contents> findContents = contentsService.findAll(pageable);
        model.addAttribute("contents", findContents);

        return "/contents/indexform";
    }

    @GetMapping("/create")
    public String createform(){
        return "/contents/createform";
    }
    //body 태그안에 숨거셔 많은 양의 데이터를 전송
    @PostMapping("/create")
    public String create(ContentsDto contentsDto, @RequestParam("files")MultipartFile files){
        //RequestParam - url 뒤에  붙는 파라메터의 값을 가져올 때 사용
        //ex) read?no=1 경우 사용
        contentsService.save(contentsDto);
        //파일 업로드
        try{
            String oriFilename = files.getOriginalFilename();
            String filename = new MD5Generator(oriFilename).toString();
            //실행되는 위치의 'files' 폴더에 파일이 저장됨
            String savePath = System.getProperty("user.dir") + "\\files";
            //파일이 저장되는 폴더가 없으면 폴더를 생성함
            if(!new File(savePath).exists()){
                try{
                    new File(savePath).mkdir();
                }
                catch (Exception e){
                    e.getStackTrace();
                }
            }

            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            FilesDto filesDto = new FilesDto();
            filesDto.setFileOriName(oriFilename);
            filesDto.setFileName(filename);
            filesDto.setFileUrl(filePath);

            Long fileId = fileService.saveFile(filesDto);
        } catch (Exception e){
            e.printStackTrace();
        }

        return "/contents/createform";
    }

    @GetMapping("/update/{id}")
    public String updateform(@PathVariable("id") Long id, Model model){
        //@PathVariable - url에서 각 구분자에 들어오는 값을 처리해야 할 때 사용
        Contents contents = contentsRepository.getOne(id);
        model.addAttribute("update", contents);
        return "/contents/updateform";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, ContentsDto contentsDto){
        contentsService.update(id, contentsDto);
        return "/contents/indexform";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id){
        contentsService.delete(id);
        return "redirect:/read";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model){
        Contents contents = contentsService.detail(id);
        model.addAttribute("content", contents);

        Files files = fileService.filesDetail(id-41);
        model.addAttribute("files", files);

        return "/contents/detailform";
    }

    //파일 다운로드
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("id") Long id) throws IOException {
        FilesDto filesDto = fileService.getFile(id);
        Path path = Paths.get(filesDto.getFileUrl());
        Resource resource = new InputStreamResource(java.nio.file.Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filesDto.getFileOriName() + "\"")
                .body(resource);
    }

}
