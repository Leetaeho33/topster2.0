package com.sparta.topster.domain.topsterAlbum.service;

import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.topster.dto.req.TopsterCreateReq;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topsterAlbum.entity.TopsterAlbum;
import com.sparta.topster.domain.topsterAlbum.repository.TopsterAlbumRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopsterAlbumService {

    private final TopsterAlbumRepository topsterAlbumRepository;

    public List<TopsterAlbum> create(Topster topster, List<Album> albums, TopsterCreateReq req) {

        /**
         * albums를 key 에 앨범 제목, value에 album을 넣는다
         */
        Map<String, Album> albumNameAndAlbumMap = new HashMap<>();
        albums.forEach(album -> albumNameAndAlbumMap.put(album.getTitle(), album));

        List<TopsterAlbum> topsterAlbums = new ArrayList<>();

        /**
         * req의 albumsInsertReq의 리스트를 반복문 돌면서 TopsterAlbum을 배열에 넣어주는데
         * album 값에는 위에서 만들어둔 map에 key(앨범 제목)를 통해 value값에 있는 album을 저장해 준다.
         */
        req.getAlbums().forEach(albumReq -> {
            TopsterAlbum topsterAlbum = TopsterAlbum.builder()
                .topster(topster)
                .album(albumNameAndAlbumMap.get(albumReq.getTitle()))
                .build();
            topsterAlbums.add(topsterAlbum);
            topster.getTopsterAlbumList().add(topsterAlbum);
        });
        return topsterAlbumRepository.saveAll(topsterAlbums);

    }

}
