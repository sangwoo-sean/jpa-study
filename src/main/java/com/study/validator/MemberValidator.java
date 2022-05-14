package com.study.validator;

import com.study.model.domain.Member;
import com.study.model.form.MemberForm;
import com.study.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class MemberValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberForm form = (MemberForm) target;

        ValidationUtils.rejectIfEmpty(errors, "name","empty", "이름을 입력해주세요.");
        if (errors.hasErrors()) return;

        Pattern name_pattern = Pattern.compile("^[\\wㄱ-ㅎㅏ-ㅣ가-힣]{2,20}$", Pattern.CASE_INSENSITIVE);
        if (!name_pattern.matcher(form.getName()).matches()) {
            errors.rejectValue("name","invalid", "이름은 2자 이상 20자 이하로 입력해주세요.");
            return;
        }
        List<Member> members = memberRepository.findByName(form.getName());
        if (!members.isEmpty()) {
            errors.rejectValue("name","duplicated", "이미 존재하는 회원 이름입니다.");
            return;
        }

        ValidationUtils.rejectIfEmpty(errors, "password", "empty", "비밀번호를 입력해주세요.");
        if (errors.hasErrors()) return;

        Pattern pw_pattern = Pattern.compile("^(?=.*[a-z])(?=.*\\d)[a-z\\d]{8,}$", Pattern.CASE_INSENSITIVE);
        if (!pw_pattern.matcher(form.getPassword()).matches()) {
            errors.rejectValue("password","invalid", "비밀번호는 8자 이상, 문자와 숫자를 포함해야합니다.");
            return;
        }

        if (!form.getPassword().equals(form.getPassword_check())) {
            errors.rejectValue("password_check", "invalid", "비밀번호와 확인이 일치하지 않습니다.");
        }
    }
}
