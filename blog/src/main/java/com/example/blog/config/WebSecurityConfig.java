package com.example.blog.config;

import com.example.blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    //Encoder 등록
    @Bean
    public PasswordEncoder passwordEncoder() { // 4
        return new BCryptPasswordEncoder();
    }

    //제외 패턴
    @Override
    public void configure(WebSecurity web) { // 5
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    //시큐어 등록
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 인증 요청
                .antMatchers("/signIn", "/signup").permitAll() // 누구나 접근 허용
                .antMatchers("/read").hasRole("USER") // USER만 접근 가능
                .antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
                .and()
                .formLogin() //로그인 폼 링크
                .loginPage("/signIn") // 로그인 페이지 링크
                .defaultSuccessUrl("/read")
                .usernameParameter("account")
                .passwordParameter("password")
                .and()
                .logout() //로그 아웃 폼 링크
                .logoutSuccessUrl("/signIn")
                .invalidateHttpSession(true) // 세션 날리기
        ;

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());

    }
}
