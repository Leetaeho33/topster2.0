package com.sparta.topster.domain.user.controller;

import com.sparta.topster.domain.user.service.google.GoogleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users", produces = "application/json")
public class GoogleController {

    GoogleService googleService;

    public GoogleController(GoogleService googleService) {
        this.googleService = googleService;
    }

    @GetMapping("/{registrationId}")
    public String googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        googleService.socialLogin(code, registrationId);

        return "redirect:/";
    }
}