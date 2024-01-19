package com.sparta.topster.domain.user.controller;

import com.sparta.topster.domain.user.service.mail.MailService;
import com.sparta.topster.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail")
    public String mailConfirm(@RequestParam String email) throws Exception {
        String code = mailService.sendSimpleMessage(email);
        System.out.println("사용자에게 발송한 인증코드 ==> " + code);

        return "작성하신 메일에서 인증번호를 확인해주세요.";
    }
}