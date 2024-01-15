package com.sparta.topster.domain.topster.service;

import com.sparta.topster.domain.album.dto.req.AlbumInsertReq;
import com.sparta.topster.domain.album.dto.res.AlbumRes;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.album.service.AlbumService;
import com.sparta.topster.domain.like.entity.Like;
import com.sparta.topster.domain.like.service.LikeService;
import com.sparta.topster.domain.topster.dto.req.TopsterCreateReq;
import com.sparta.topster.domain.topster.dto.res.TopsterCreateLoginRes;
import com.sparta.topster.domain.topster.dto.res.TopsterGetLoginRes;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster.repository.TopsterRepository;
import com.sparta.topster.domain.topster_album.entity.TopsterAlbum;
import com.sparta.topster.domain.topster_album.repository.TopsterAlbumRepository;
import com.sparta.topster.domain.user.entity.User;
import com.sparta.topster.global.exception.ServiceException;
import com.sparta.topster.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.topster.domain.topster.exception.TopsterException.*;

@Service
@Slf4j(topic = "TopsterService")
@RequiredArgsConstructor
public class TopsterService {
    private final TopsterAlbumRepository topsterAlbumRepository;
    private final AlbumService albumService;
    private final TopsterRepository topsterRepository;
    private final LikeService likeService;

    @Transactional
    public TopsterCreateLoginRes createTopster(TopsterCreateReq topsterCreateReq, User user) {
        log.info("Topster 등록 시작");
        List<AlbumInsertReq> albumInsertReqList = topsterCreateReq.getAlbums();
        String title = topsterCreateReq.getTitle();
        String content = topsterCreateReq.getContent();
        Topster topster = Topster.builder().
                title(title).
                content(content).
                user(user).
                build();
        log.info("빈 Topster save");
        topster = topsterRepository.save(topster);

        log.info("Topster AlbumLlist에 Album추가 시작");
        for(AlbumInsertReq albumInsertReq : albumInsertReqList){
            String albumTitle = albumInsertReq.getTitle();
            String albumReleaseDate = albumInsertReq.getReleaseDate();
            String albumImage = albumInsertReq.getImage();
            String albumArtist = albumInsertReq.getArtist();

            Album album = albumService.getAlbumByTitleOrCreate(albumTitle,
                        albumImage,
                        albumArtist,
                        albumReleaseDate);

            TopsterAlbum topsterAlbum = TopsterAlbum.builder().
                    topster(topster).
                    album(album).build();
            topsterAlbumRepository.save(topsterAlbum);

            topster.getTopsterAlbumList().add(topsterAlbum);
            }
        log.info("Topster AlbumLlist에 Album추가 완료");
        List<AlbumRes> albumResList  = new ArrayList<>();
        log.info("Topster Entity -> TopsterCreateLoginRes");
        for(TopsterAlbum topsterAlbum : topster.getTopsterAlbumList()){
            albumResList.add(
                    AlbumRes.builder().
                            title(topsterAlbum.getAlbum().getTitle()).
                            artist(topsterAlbum.getAlbum().getArtist()).
                            image(topsterAlbum.getAlbum().getImage()).
                            release(topsterAlbum.getAlbum().getReleaseDate()).
                            build());
        }
        return TopsterCreateLoginRes.builder().
                id(topster.getId()).
                title(topster.getTitle()).
                content(topster.getContent()).
                author(topster.getUser().getNickname()).
                albums(albumResList).
                createdAt(topster.getCreatedAt()).
                build();
    }


    public TopsterGetLoginRes getTopsterService(Long topsterId){
        Topster topster = getTopster(topsterId);
        return fromTopsterToTopsterGetNotLoginRes(topster);
    }


    public Object getTopsterByUserService(Long userId) {
        List<Topster> topsterList = getTopsterByUser(userId);
        List<TopsterGetLoginRes> topsterGetResList = new ArrayList<>();
        for(Topster topster:topsterList){
            topsterGetResList.add(fromTopsterToTopsterGetLoginRes(topster, userId));
        }
        return topsterGetResList;
    }


    public void deleteTopster(Long topsterId, User user) {
        log.info("topster 삭제 시작");
        if(checkAuthor(user.getId(),topsterId)){
            topsterRepository.delete(getTopster(topsterId));
        }else{
            log.error(NOT_AUTHOR.getMessage());
            throw new ServiceException(NOT_AUTHOR);
        }
    }

    @Transactional
    public Object toggleTopsterLike(Long topsterId, User user){
        log.info("탑스터 좋아요 toggle 시작");
        Topster topster = getTopster(topsterId);
        Like like = likeService.getLike(user.getId(), topsterId);
        if(like == null){
            log.info("탑스터에 좋아요가 눌리지 않은 상태");
            topster.getTopsterLike().add(Like.builder().user(user).topster(topster).build());
            topster.upAndDownLikeCount(1);
        }else {
            log.info("탑스터에 좋아요가 눌린 상태");
            likeService.deleteLike(like);
            topster.upAndDownLikeCount(-1);
        }
        return fromTopsterToTopsterGetLoginRes(topster, user.getId());
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
            log.info("userId :" + userId + "로 topster 조회 완료");
            return userTopsterList;
        }else {
            log.error(NOT_FOUND_TOPSTER.getMessage());
            throw new ServiceException(NOT_FOUND_TOPSTER);
        }
    }


    private TopsterGetLoginRes fromTopsterToTopsterGetLoginRes(Topster topstesr, Long userId){
        log.info("Topster Entity -> TopsterGetLoginRes");
        List<AlbumRes> albumResList = new ArrayList<>();
        for(TopsterAlbum topsterAlbum : topstesr.getTopsterAlbumList()){
            albumResList.add(
                    AlbumRes.builder()
                    .id(topsterAlbum.getAlbum().getId())
                    .title(topsterAlbum.getAlbum().getTitle())
                    .artist(topsterAlbum.getAlbum().getArtist())
                    .release(topsterAlbum.getAlbum().getReleaseDate())
                    .image(topsterAlbum.getAlbum().getImage()).build());
        }

        return TopsterGetLoginRes.builder()
                .id(topstesr.getId())
                .title(topstesr.getTitle())
                .content(topstesr.getContent())
                .albums(albumResList)
                .author(topstesr.getUser().getNickname())
                .likeCount(topstesr.getLikeCount())
                .likeStatus(isLikePresent(userId, topstesr.getId()))
                .createdAt(topstesr.getCreatedAt())
                .build();
    }

    private TopsterGetLoginRes fromTopsterToTopsterGetNotLoginRes(Topster topstesr){
        log.info("Topster Entity -> TopsterGetNotLoginRes");
        List<AlbumRes> albumResList = new ArrayList<>();
        for(TopsterAlbum topsterAlbum : topstesr.getTopsterAlbumList()){
            albumResList.add(
                    AlbumRes.builder()
                            .id(topsterAlbum.getAlbum().getId())
                            .title(topsterAlbum.getAlbum().getTitle())
                            .artist(topsterAlbum.getAlbum().getArtist())
                            .release(topsterAlbum.getAlbum().getReleaseDate())
                            .image(topsterAlbum.getAlbum().getImage()).build());
        }

        return TopsterGetLoginRes.builder()
                .id(topstesr.getId())
                .title(topstesr.getTitle())
                .content(topstesr.getContent())
                .albums(albumResList)
                .author(topstesr.getUser().getNickname())
                .likeCount(topstesr.getLikeCount())
                .createdAt(topstesr.getCreatedAt())
                .build();
    }


    private boolean checkAuthor(Long userId, Long topsterId){
        Topster topster = getTopster(topsterId);
        return topster.getUser().getId().equals((userId));
    }



    public boolean isLikePresent(Long userId, Long topsterId){
        Like like = likeService.getLike(userId, topsterId);
        return like != null;
    }

}
