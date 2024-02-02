package com.sparta.topster.domain.topster.controller;

import com.sparta.topster.domain.topster.facade.TopsterCreateFlowService;
import com.sparta.topster.domain.topster.dto.req.TopsterCreateReq;
import com.sparta.topster.domain.topster.dto.res.TopsterCreateRes;
import com.sparta.topster.domain.topster.dto.res.TopsterGetRes;
import com.sparta.topster.domain.topster.service.TopsterService;
import com.sparta.topster.global.response.RootNoDataRes;
import com.sparta.topster.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
@Tag(name = "탑스터 API")
public class TopsterController {
    private final TopsterService topsterService;
    private final TopsterCreateFlowService topsterCreateFlowService;

    @PostMapping("/topsters")
    public ResponseEntity<TopsterCreateRes> create(@RequestBody TopsterCreateReq topsterCreateReq,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(topsterCreateFlowService.createFlow(topsterCreateReq, userDetails.getUser()));
    }


    @GetMapping("/topsters/{topsterId}")
    public ResponseEntity<TopsterGetRes> getTopster(@PathVariable Long topsterId){
        return ResponseEntity.ok(topsterService.getTopsterService(topsterId));
    }


    @GetMapping("/topsters")
    public ResponseEntity<Slice<TopsterGetRes>> getTopsters(Integer page){
        return ResponseEntity.ok(topsterService.getTopstersService(page));
    }


    @GetMapping("/users/{userId}/topsters")
    public ResponseEntity<List<TopsterGetRes>> getTopsterByUser(@PathVariable Long userId){
        return ResponseEntity.ok(topsterService.getTopsterByUserService(userId));
    }


    @GetMapping("/topsters/my")
    public ResponseEntity<List<TopsterGetRes>> getMyTopster(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(topsterService.getTopsterByUserService(userDetails.getUser()
                .getId()));
    }


    @GetMapping("/topsters/top-three")
    public ResponseEntity<List<TopsterGetRes>> getTopThreeTopster() {
        return ResponseEntity.ok(topsterService.getTopsterTopThree());
    }


    @DeleteMapping("/topsters/{topsterId}")
    public ResponseEntity<RootNoDataRes> deleteTopstesr(@PathVariable Long topsterId,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails){
        topsterService.deleteTopster(topsterId, userDetails.getUser());
        return ResponseEntity.ok(RootNoDataRes.builder().
                code(HttpStatus.OK.toString()).
                message("탑스터가 정상적으로 삭제 되었습니다.").build());
    }


    @GetMapping("/topsters/{id}/isAuthor")
    public ResponseEntity<RootNoDataRes> isAuthor(@PathVariable Long id,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {

        topsterService.isAuthor(userDetails.getUser().getId(), id);
        return ResponseEntity.ok(RootNoDataRes.builder()
                .code("200")
                .message("본인의 탑스터가 맞습니다")
                .build());
    }

}
