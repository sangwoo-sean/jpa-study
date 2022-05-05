package com.study.repository;


import com.study.model.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void testMember() {
        //g
        Member member = new Member();
        member.setName("member");

        //w
        Long save = memberRepository.save(member);
        Member member1 = memberRepository.findById(save);

        //t
        Assertions.assertThat(member1.getId()).isEqualTo(member.getId());
    }
}