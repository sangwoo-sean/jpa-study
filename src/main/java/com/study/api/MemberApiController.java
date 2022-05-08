package com.study.api;

import com.study.model.domain.Address;
import com.study.model.domain.Member;
import com.study.model.dto.ApiResult;
import com.study.model.dto.MemberDto;
import com.study.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PutMapping("/api/member")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        member.setAddress(request.getAddress());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    private static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    private static class CreateMemberRequest {
        @NotEmpty(message = "유저 이름은 필수입니다.")
        private String name;
        private Address address;
    }

    @PatchMapping("/api/member/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long memberId, @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(memberId, request.getName());
        Member member = memberService.findById(memberId);
        return new UpdateMemberResponse(member);
    }

    @Data
    private static class UpdateMemberResponse {
        private Long id;
        private String name;

        public UpdateMemberResponse(Member member) {
            this.id = member.getId();
            this.name = member.getName();
        }
    }

    @Data
    private static class UpdateMemberRequest {
        @NotEmpty(message = "이름을 입력해주세요.")
        private String name;
    }

    @GetMapping("/api/members")
    public ApiResult memberList() {
        List<MemberDto> members = memberService.findMembers()
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
        return new ApiResult(members);
    }
}