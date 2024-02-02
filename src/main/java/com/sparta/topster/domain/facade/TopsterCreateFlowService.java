package com.sparta.topster.domain.facade;

import com.sparta.topster.domain.album.dto.res.AlbumRes;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.album.service.AlbumService;
import com.sparta.topster.domain.topster.dto.req.TopsterCreateReq;
import com.sparta.topster.domain.topster.dto.res.TopsterCreateRes;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster.service.TopsterService;
import com.sparta.topster.domain.topsterAlbum.entity.TopsterAlbum;
import com.sparta.topster.domain.topsterAlbum.service.TopsterAlbumService;
import com.sparta.topster.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class TopsterCreateFlowService {

    private final TopsterService topsterService;
    private final AlbumService albumService;
    private final TopsterAlbumService topsterAlbumService;


    public TopsterCreateRes createFlow(TopsterCreateReq req, User user) {
        Topster topster = topsterService.create(req, user);
        List<Album> albums = albumService.create(req.getAlbums());
        List<TopsterAlbum> topsterAlbums = topsterAlbumService.create(topster, albums, req);

        List<AlbumRes> albumResList = getAlbumResList(topsterAlbums);

        return TopsterCreateRes.builder()
            .id(topster.getId())
            .title(topster.getTitle())
            .author(user.getNickname())
            .albums(albumResList)
            .createdAt(topster.getCreatedAt())
            .build();
    }


    private List<AlbumRes> getAlbumResList(List<TopsterAlbum> topsterAlbums) {
        return topsterAlbums.stream().map(topsterAlbum -> AlbumRes.builder()
            .id(topsterAlbum.getAlbum().getId())
            .title(topsterAlbum.getAlbum().getTitle())
            .image(topsterAlbum.getAlbum().getImage())
            .artist(topsterAlbum.getAlbum().getArtist())
            .releaseDate(topsterAlbum.getAlbum().getReleaseDate())
            .build()).toList();
    }

}
