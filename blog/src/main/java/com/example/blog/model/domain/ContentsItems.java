package com.example.blog.model.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ContentsItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;


    @JoinColumn(name = "contents_id")
    @ManyToOne
    private Contents contents;
}
