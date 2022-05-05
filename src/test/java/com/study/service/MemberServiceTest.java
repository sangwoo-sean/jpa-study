package com.study.service;

import com.study.model.domain.Member;
import com.study.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("박상우");

        //when
        Long id = memberService.join(member);

        //then
        Assertions.assertEquals(member, memberRepository.findById(id));
    }

    @Test
    void 중복_회원_예외() {
        //givne
        Member member1 = new Member();
        member1.setName("상우");

        Member member2 = new Member();
        member2.setName("상우");

        //when
        memberService.join(member1);

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }
}