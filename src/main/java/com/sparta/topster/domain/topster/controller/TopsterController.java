package com.sparta.topster.domain.topster.controller;

import com.sparta.topster.domain.topster.service.TopsterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/topsters")
@Controller
@RequiredArgsConstructor
public class TopsterController {
    private final TopsterService topsterService;

}
