//package com.sparta.topster.domain.album.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.sparta.topster.domain.album.repository.AlbumRepository;
//import com.sparta.topster.domain.open_api.service.OpenApiService;
//import com.sparta.topster.domain.open_api.service.maniadb.ManiadbService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.client.RestTemplate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(MockitoExtension.class)
//public class AlbumServiceTest {
//
//    @Mock
//    OpenApiService openApiService;
//    @Mock
//    AlbumRepository albumRepository;
//    AlbumService albumService;
//    @BeforeEach
//    void init(){
//        openApiService = new ManiadbService(new RestTemplate());
//        albumService = new AlbumService(albumRepository, openApiService);
//    }
//
//    @DisplayName("rawdata가 rss를 포함하는지?")
//    @Test
//    void rawdataTest() throws JsonProcessingException {
//        //given
//        String query = "kanye";
//
//        //when
//        albumService.getRawArtistData(query);
//
//        //then
//        assertThat(albumService.getRawArtistData(query).contains("rss"));
//    }
//
//    @DisplayName("rawdata가 쿼리를 포함하고 있는지?")
//    @Test
//    void rawdataQueryTest() throws JsonProcessingException {
//        //given
//        String query = "kanye";
//
//        //when
//        albumService.getRawArtistData(query);
//
//        //then
//        assertThat(albumService.getRawArtistData(query).contains(query));
//    }
//
//    @DisplayName("rawdata에서 앨범 뽑아오기 틀이 맞는지 확인")
//    @Test
//    void rawdataToAlbumsCheckFormet() throws JsonProcessingException {
//        //given
//        String query = "kanye";
//
//        //when
//        albumService.getAlbumsByArtist(query);
//
//        //then
//        assertThat(albumService.getAlbumsByArtist(query).contains("title"));
//    }
//
//    @DisplayName("rawdata에서 앨범 뽑아오기")
//    @Test
//    void rawdataToAlbums() throws JsonProcessingException {
//        //given
//        String query = "kanye";
//
//        //when
//        albumService.getAlbumsByArtist(query);
//
//        //then
//        assertThat(albumService.getAlbumsByArtist(query).contains(query));
//    }
//}
