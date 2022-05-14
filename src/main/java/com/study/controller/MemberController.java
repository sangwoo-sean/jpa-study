package com.study.controller;

import com.study.model.domain.Address;
import com.study.model.domain.Member;
import com.study.model.form.MemberForm;
import com.study.service.MemberService;
import com.study.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createForm(@Valid MemberForm memberForm, BindingResult result, Model model) {
        memberValidator.validate(memberForm, result);
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

//        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        String encoded_password = passwordEncoder.encode(memberForm.getPassword());
        memberForm.setPassword(encoded_password);
        Member member = Member.createMember(memberForm);
//        member.setAddress(address);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
