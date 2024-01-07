package com.sparta.topster.domain.user.controller;

import com.sparta.topster.domain.user.service.mail.MailRegister;
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
    MailRegister mailRegister;

    private final Map<String,String> getCode = new HashMap<>();

    @PostMapping("/mail")
    public String mailConfirm(@RequestParam String email) throws Exception{
        String code = mailRegister.sendSimpleMessage(email);
        getCode.put(email,code);
        System.out.println("사용자에게 발송한 인증코드 ==> " + code);

        return code;
    }

    public String returnGetCode(String email){
        return getCode.get(email);
    }
}