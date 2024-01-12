package com.sparta.topster.domain.like.service;

import com.sparta.topster.domain.like.entity.Like;
import com.sparta.topster.domain.like.repository.LikeRepository;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster.service.TopsterService;
import com.sparta.topster.global.security.UserDetailsImpl;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final TopsterService topsterService;

  @Transactional
  public boolean toggleLike(Long topsterId, UserDetailsImpl userDetails) {
    // 해당 id의 탑스터가 존재하는지 검증
    Topster topster = topsterService.getTopster(topsterId);

    // 탑스타 작성자 본인인지 검증
//    if(topster.getUser().getId().equals(userDetails.getUser().getId())) {
//      throw new IllegalArgumentException("본인의 게시물에 좋아요를 누를 수 없습니다.");
//    }

    // 해당 탑스타와 사용자 정보를 가지고 있는 좋아요 객체 생성
    Like like = new Like(topster, userDetails);

    // 해당 탑스타에 대한 모든 좋아요 정보를 조회
     Optional<Like> optionalLike = likeRepository.findByTopsterIdAndUserId(topsterId, userDetails.getUser()
        .getId());

    log.info("조회된 좋아요 목록을 순회");
      // 현재 사용자가 이미 해당 게시물에 좋아요를 눌렀는지 확인
      if(optionalLike.isPresent()) {
        // 이미 좋아요를 눌렀다면 해당 좋아요 삭제
        likeRepository.delete(optionalLike.get());
        topster.upAndDownLikeCount(-1);
        return false;
      }
     log.info("좋아요 누르지 않았다면 실행");
    // likeCount + 1
    topster.upAndDownLikeCount(1);
    // 사용자가 아직 좋아요를 누르지 않았다면 좋아요 정보 추가
    likeRepository.save(like);

    return true;
  }

}
