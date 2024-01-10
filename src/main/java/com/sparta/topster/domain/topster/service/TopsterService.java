package com.sparta.topster.domain.topster.service;

import com.sparta.topster.domain.album.dto.req.AlbumInsertReq;
import com.sparta.topster.domain.album.dto.res.AlbumRes;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.album.service.AlbumService;
import com.sparta.topster.domain.topster.dto.req.TopsterCreateReq;
import com.sparta.topster.domain.topster.dto.res.TopsterCreateRes;
import com.sparta.topster.domain.topster.dto.res.TopsterGetRes;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster.repository.TopsterRepository;
import com.sparta.topster.domain.topster_album.entity.TopsterAlbum;
import com.sparta.topster.domain.topster_album.repository.TopsterAlbumRepository;
import com.sparta.topster.domain.user.entity.User;
import com.sparta.topster.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.oauth2.resourceserver.OpaqueTokenDsl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.topster.domain.topster.exception.TopsterException.NOT_EXIST_TOPSTER;

@Service
@Slf4j(topic = "TopsterService")
@RequiredArgsConstructor
public class TopsterService {
    private final TopsterAlbumRepository topsterAlbumRepository;
    private final AlbumService albumService;
    private final TopsterRepository topsterRepository;

    public TopsterGetRes getTopsterService(Long topsterId){
        Topster topster = getTopster(topsterId);
        return fromTopsterToTopsterGetRes(topster);
    }

    @Transactional
    public TopsterCreateRes createTopster(TopsterCreateReq topsterCreateReq, User user) {
        List<AlbumInsertReq> albumInsertReqList = topsterCreateReq.getAlbumInsertReqList();
        String title = topsterCreateReq.getTitle();
        String content = topsterCreateReq.getContent();
        Topster topster = Topster.builder().
                title(title).
                content(content).
                user(user).
                build();
        topster = topsterRepository.save(topster);

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
        List<AlbumRes> albumResList  = new ArrayList<>();
        for(TopsterAlbum topsterAlbum : topster.getTopsterAlbumList()){
            albumResList.add(
                    AlbumRes.builder().
                            title(topsterAlbum.getAlbum().getTitle()).
                            artist(topsterAlbum.getAlbum().getArtist()).
                            image(topsterAlbum.getAlbum().getImage()).
                            release(topsterAlbum.getAlbum().getReleaseDate()).
                            build());
        }
        return TopsterCreateRes.builder().
                title(topster.getTitle()).
                content(topster.getContent()).
                albumResList(albumResList).build();
    }

    public Topster getTopster(Long topsterId){
        log.info("topster 조회 시작");
        Optional<Topster> optionalTopster = topsterRepository.findById(topsterId);
        if(optionalTopster.isPresent()){
            return optionalTopster.get();
        }else {
            log.error(NOT_EXIST_TOPSTER.getMessage());
            throw new ServiceException(NOT_EXIST_TOPSTER);
        }
    }
    public TopsterGetRes fromTopsterToTopsterGetRes(Topster topstesr){
        List<AlbumRes> albumResList = new ArrayList<>();
        for(TopsterAlbum topsterAlbum : topstesr.getTopsterAlbumList()){
            albumResList.add(AlbumRes.builder()
                    .title(topsterAlbum.getAlbum().getTitle())
                    .artist(topsterAlbum.getAlbum().getArtist())
                    .release(topsterAlbum.getAlbum().getReleaseDate())
                    .image(topsterAlbum.getAlbum().getImage()).build());
        }
        return TopsterGetRes.builder().title(topstesr.getTitle())
                .content(topstesr.getContent())
                .albumResList(albumResList).build();
    }
}
