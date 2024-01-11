package com.sparta.topster.domain.like.service;

import com.sparta.topster.domain.like.entity.Like;
import com.sparta.topster.domain.like.repository.LikeRepository;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster.service.TopsterService;
import com.sparta.topster.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final TopsterService topsterService;

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
    List<Like> likeList = likeRepository.findAllByTopster(topster);

    log.info("조회된 좋아요 목록을 순회");
    for(Like likes : likeList) {
      // 현재 사용자가 이미 해당 게시물에 좋아요를 눌렀는지 확인
      if(likes.getUser().getId().equals(userDetails.getUser().getId())) {
        // 이미 좋아요를 눌렀다면 해당 좋아요 정보를 찾은 후 삭제

        likeRepository.delete(likes);
        return false;
      }
    }
     log.info("좋아요 누르지 않았다면 실행");
    // likeCount + 1
    like.upAndDownLikeCount(like.getLikeCount() + 1);
    // 사용자가 아직 좋아요를 누르지 않았다면 좋아요 정보 추가
    likeRepository.save(like);

    return true;
  }
}
