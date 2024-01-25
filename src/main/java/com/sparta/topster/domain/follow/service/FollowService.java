package com.sparta.topster.domain.follow.service;

import com.sparta.topster.domain.follow.dto.FollowerGetRes;
import com.sparta.topster.domain.follow.entity.Follow;
import com.sparta.topster.domain.follow.repository.FollowRepository;
import com.sparta.topster.domain.user.entity.User;
import com.sparta.topster.domain.user.service.user.UserService;
import com.sparta.topster.domain.user.service.user.UserServiceImpl;
import com.sparta.topster.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.topster.domain.follow.exception.FollowException.*;

@Service
@Slf4j(topic = "FollowService")
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserServiceImpl userService;

    public void toggleFollow(Long userId, User user){

        if(user.getId().equals(userId)){
            log.error(CANT_FOLLOW_MYSELF.getMessage());
            throw new ServiceException(CANT_FOLLOW_MYSELF);
        }

        User toFollowUser = userService.findByUser(userId);
        Optional<Follow> follow =
                followRepository.findByToFollowAndFromFollow(toFollowUser, user);
        if(follow.isEmpty()){
            followRepository.save(Follow.builder().
                    toFollow(toFollowUser).fromFollow(user).build());
        }else{
            followRepository.delete(follow.get());
        }
    }

    public List<FollowerGetRes> getMyFollower(User user) {
        List<Follow> followList = followRepository.findAllByToFollow(user);
        if(followList.isEmpty()){
            log.error(NOT_FOUND_FOLLOWER.getMessage());
            throw  new ServiceException(NOT_FOUND_FOLLOWER);
        }
        return followList.stream().
                map(follow ->
                        FollowerGetRes
                                .builder()
                                .userId(follow.getFromFollow().getId())
                                .nickname(follow.getFromFollow().getNickname())
                                .build()).toList();
    }

    public List<FollowerGetRes> getMyFollowing(User user) {
        List<Follow> followList = followRepository.findAllByFromFollow(user);
        if(followList.isEmpty()){
            log.error(NOT_FOUND_FOLLOWING.getMessage());
            throw  new ServiceException(NOT_FOUND_FOLLOWING);
        }
        return followList.stream().
                map(follow ->
                        FollowerGetRes
                                .builder()
                                .userId(follow.getToFollow().getId())
                                .nickname(follow.getToFollow().getNickname())
                                .build()).toList();
    }
}
