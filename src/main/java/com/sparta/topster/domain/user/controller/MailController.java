package com.sparta.topster.domain.user.controller;

import com.sparta.topster.domain.user.service.mail.MailService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
public class MailController {

    @Autowired
    MailService mailService;

    private final Map<String,String> getCode = new HashMap<>();

    @PostMapping("/mail")
    public String mailConfirm(@RequestParam String email) throws Exception{
        String code = mailService.sendSimpleMessage(email);
        getCode.put(email,code);
        System.out.println("사용자에게 발송한 인증코드 ==> " + code);

        return "작성하신 메일에서 인증번호를 확인해주세요.";
    }

    public String returnGetCode(String email){
        return getCode.get(email);
    }
}