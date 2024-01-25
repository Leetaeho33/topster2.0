package com.sparta.topster.domain.album.service;

import com.sparta.topster.domain.album.dto.req.AlbumInsertReq;
import com.sparta.topster.domain.album.dto.res.AlbumRes;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.album.entity.CacheAlbum;
import com.sparta.topster.domain.album.repository.AlbumRepository;
import com.sparta.topster.domain.album.repository.CacheAlbumRepository;
import com.sparta.topster.domain.openApi.service.OpenApiService;
import com.sparta.topster.global.exception.ServiceException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sparta.topster.domain.album.exception.AlbumException.NOT_EXIST_ALBUM;

@Slf4j(topic = "AlbumService")
@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final OpenApiService openApiService;
    private final CacheAlbumRepository cacheAlbumRepository;

    public String getRawArtistData(String query) {
        return openApiService.getRawArtistData(query);
    }


    public List<AlbumRes> getAlbumsByArtist(String query) {

        Optional<CacheAlbum> cacheAlbum = cacheAlbumRepository.findById(query);

        if (cacheAlbum.isPresent()) {
            log.info("캐싱된 자료 사용");
            return cacheAlbum.get().getAlbums();
        }

        List<AlbumRes> albumResList = openApiService.getAlbums(query);

        CacheAlbum saveCache = CacheAlbum.builder()
            .artist(query)
            .albums(albumResList)
            .build();
        cacheAlbumRepository.save(saveCache);
        log.info("캐싱이 안 된 자료 사용 후 캐싱");
        return albumResList;
    }


    public List<Album> create(List<AlbumInsertReq> reqList) {

        /**
         * reqList 안에 앨범 이름 중복 제거
         * set 또는 map으로 key에 앨범이름, value에 req를 넣어줌
         */

        Map<String, AlbumInsertReq> albumReqMap = new HashMap<>();
        reqList.forEach(req -> albumReqMap.put(req.getTitle(), req));

        /**
         * 앨범이 이미 DB안에 있다면 reqMap에서 제거하는 로직
         */
        List<Album> findAlbums = albumRepository.findAllByAlbumTitleList(albumReqMap.keySet());
        findAlbums.forEach(album -> albumReqMap.remove(album.getTitle()));

        /**
         * map의 values를 album으로 변환해서 저장
         */
        albumReqMap.values().forEach(album ->
            findAlbums.add(Album.builder()
                .title(album.getTitle())
                .image(album.getImage())
                .release(album.getReleaseDate())
                .artist(album.getArtist())
                .build()));
        return albumRepository.saveAll(findAlbums);
    }
}
