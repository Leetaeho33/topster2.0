package com.sparta.topster.domain.topster.service;

import static com.sparta.topster.domain.topster.exception.TopsterException.NOT_AUTHOR;
import static com.sparta.topster.domain.topster.exception.TopsterException.NOT_EXIST_TOPSTER;
import static com.sparta.topster.domain.topster.exception.TopsterException.NOT_FOUND_TOPSTER;

import com.sparta.topster.domain.album.dto.res.AlbumRes;
import com.sparta.topster.domain.topster.dto.req.TopsterCreateReq;
import com.sparta.topster.domain.topster.dto.res.TopsterGetRes;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster.repository.TopsterRepository;
import com.sparta.topster.domain.topsterAlbum.entity.TopsterAlbum;
import com.sparta.topster.domain.user.entity.User;
import com.sparta.topster.global.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "TopsterService")
@RequiredArgsConstructor
public class TopsterService {

    private final TopsterRepository topsterRepository;


    /**
     * "탑스터"를 저장할 때 "앨범"과 "탑스터 앨범"도 저장한다.
     * "탑스터 서비스"가 "앨범 서비스"와 "탑스터 앨범 레포지토리"를 주입받아서
     * "탑스터"를 저장하고 "앨범 서비스"를 통해 "앨범"을 저장하고 "앨범"을 받아서
     * "탑스터 앨범"을 저장한다.
     * "탑스터 서비스"의 역할이 너무 많은 것 같다.
     * 하나의 흐름?을 묶어 주고 각각 본인의 역할만 수행 한다면?
     */

    @Transactional
    public Topster create(TopsterCreateReq req, User user) {
        log.info("Topster 등록 시작");
        Topster topster = Topster.builder()
            .title(req.getTitle())
            .user(user)
            .build();

        return topsterRepository.save(topster);
    }


    public TopsterGetRes getTopsterService(Long topsterId) {
        Topster topster = getTopster(topsterId);
        return fromTopsterToTopsterGetRes(topster);
    }


    public List<TopsterGetRes> getTopsterByUserService(Long userId) {
        List<Topster> topsterList = getTopsterByUser(userId);
        if(topsterList.isEmpty()){
            log.error(NOT_FOUND_TOPSTER.getMessage());
            throw new ServiceException(NOT_FOUND_TOPSTER);
        }
        List<TopsterGetRes> topsterGetResList = new ArrayList<>();
        for (Topster topster : topsterList) {
            topsterGetResList.add(fromTopsterToTopsterGetRes(topster));
        }
        return topsterGetResList;
    }


    public Slice<TopsterGetRes> getTopstersService(Integer pageNum) {
        Pageable pageable = PageRequest.
                of(pageNum-1,9,
                        Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<Topster> topsterPage = topsterRepository.findAll(pageable);
        if(topsterPage.isEmpty()){
            log.error(NOT_EXIST_TOPSTER.getMessage());
            throw new ServiceException(NOT_EXIST_TOPSTER);
        }
        return topsterPage.map(this::fromTopsterToTopsterGetRes);
    }


    public void deleteTopster(Long topsterId, User user) {
        log.info("topster 삭제 시작");
        if(isAuthor(user.getId(),topsterId)){
            topsterRepository.delete(getTopster(topsterId));
        }else{
            log.error(NOT_AUTHOR.getMessage());
            throw new ServiceException(NOT_AUTHOR);
        }
    }


    public Topster getTopster(Long topsterId){
        log.info("topsterId :" + topsterId +"로 topster 조회 시작");
        Optional<Topster> optionalTopster = topsterRepository.findById(topsterId);
        if(optionalTopster.isPresent()){
            return optionalTopster.get();
        }else {
            log.error(NOT_EXIST_TOPSTER.getMessage());
            throw new ServiceException(NOT_EXIST_TOPSTER);
        }
    }


    public List<Topster> getTopsterByUser(Long userId) {
        log.info("userId :" + userId + "로 topster 조회 시작");
        List<Topster> userTopsterList = topsterRepository.findByUserId(userId);
        if(!userTopsterList.isEmpty()){
            return userTopsterList;
        }else {
            log.error(NOT_FOUND_TOPSTER.getMessage());
            throw new ServiceException(NOT_FOUND_TOPSTER);
        }
    }


    public List<TopsterGetRes> getTopsterTopThree() {
        List<Topster> topsterList = topsterRepository.findTop3ByOrderByLikeCountDesc();
        List<TopsterGetRes> topsterGetResList = new ArrayList<>();
        for(Topster topster : topsterList){
            topsterGetResList.add(fromTopsterToTopsterGetRes(topster));
        }
        return topsterGetResList ;
    }


    private TopsterGetRes fromTopsterToTopsterGetRes(Topster topstesr) {

        List<AlbumRes> albumResList = new ArrayList<>();
        for (TopsterAlbum topsterAlbum : topstesr.getTopsterAlbumList()) {
            albumResList.add(
                AlbumRes.builder()
                    .id(topsterAlbum.getAlbum().getId())
                    .title(topsterAlbum.getAlbum().getTitle())
                    .artist(topsterAlbum.getAlbum().getArtist())
                    .releaseDate(topsterAlbum.getAlbum().getReleaseDate())
                    .image(topsterAlbum.getAlbum().getImage())
                    .build());
        }
 
        return TopsterGetRes.builder()
            .id(topstesr.getId())
            .title(topstesr.getTitle())
            .content(topstesr.getContent())
            .albums(albumResList)
            .author(topstesr.getUser().getNickname())
            .createdAt(topstesr.getCreatedAt())
            .build();
    }

    public boolean isAuthor(Long userId, Long topsterId) {
        Topster topster = getTopster(topsterId);
        if(!topster.getUser().getId().equals((userId))){
            log.error(NOT_AUTHOR.getMessage());
            throw new ServiceException(NOT_AUTHOR);
        }
        return true;
    }



}
