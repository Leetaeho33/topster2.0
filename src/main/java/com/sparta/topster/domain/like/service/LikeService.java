package com.sparta.topster.domain.like.service;

import com.sparta.topster.domain.like.entity.Like;
import com.sparta.topster.domain.like.repository.LikeRepository;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster.repository.TopsterRepository;
import com.sparta.topster.domain.topster.service.TopsterService;
import com.sparta.topster.global.exception.ServiceException;
import com.sparta.topster.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final TopsterRepository topsterRepository;

  public boolean toggleLike(Long topsterId, UserDetailsImpl userDetails) {

    Topster topster = topsterRepository.findById(topsterId);

//    if(topster.getUser().getId().equals(userDetails.getUser().getId())) {
//      throw new IllegalArgumentException("본인의 게시물에 좋아요를 누를 수 없습니다.");
//    }

    Like like = new Like(topster, userDetails);

    List<Like> likeList = likeRepository.findAllByTopster(topster);

    for(Like likes : likeList) {
      if(like.getUser().getId().equals(userDetails.getUser().getId())) {
        Like alreadyLike = likeRepository.findByTopsterAndUser(like.getTopster(), like.getUser());
        likeRepository.delete(alreadyLike);
        return false;
      }
    }
    // 사용자가 아직 좋아요를 누르지 않았다면 좋아요 정보 추가
    likeRepository.save(like);
    return true;
  }
}
