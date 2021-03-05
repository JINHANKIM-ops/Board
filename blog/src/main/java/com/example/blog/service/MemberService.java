package com.example.blog.service;

import com.example.blog.model.domain.Member;
import com.example.blog.model.dto.MemberDto;
import com.example.blog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    //Spring Security 메서드 필수 구현
    @Override
    public Member loadUserByUsername(String account) throws UsernameNotFoundException {
        return memberRepository.findByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException((account)));
    }
    //회원 가입
    public Long save(MemberDto memberDto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        memberDto.setPassword(encoder.encode(memberDto.getPassword()));

        return memberRepository.save(Member.builder()
                .account(memberDto.getAccount())
                .password(memberDto.getPassword())
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .registeredAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .auth(memberDto.getAuth())
                .build()).getId();
    }
}
