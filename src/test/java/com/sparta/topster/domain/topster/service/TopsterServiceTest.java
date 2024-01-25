package com.sparta.topster.domain.topster.service;

import com.sparta.topster.domain.album.dto.req.AlbumInsertReq;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.album.service.AlbumService;
import com.sparta.topster.domain.topster.dto.req.TopsterCreateReq;
import com.sparta.topster.domain.topster.dto.res.TopsterCreateRes;
import com.sparta.topster.domain.topster.dto.res.TopsterGetRes;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster.repository.TopsterRepository;
import com.sparta.topster.domain.topster_album.repository.TopsterAlbumRepository;
import com.sparta.topster.domain.user.entity.User;
import com.sparta.topster.domain.user.entity.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TopsterServiceTest {
    @InjectMocks
    TopsterService topsterService;

    User userA;
    User userB;


    Album albumA;
    Album albumB;
    Album albumC;


    @Mock
    TopsterRepository topsterRepository;
    @Mock
    TopsterAlbumRepository topsterAlbumRepository;
    @Mock
    AlbumService albumService;


    @BeforeEach
    void init(){
        userA = User.builder()
                .nickname("test1")
                .email("aaa@aaa.com")
                .intro("testIntro1")
                .password("1234")
                .username("testUserA")
                .role(UserRoleEnum.USER)
                .build();
        userB = User.builder()
                .nickname("test2")
                .email("aaa@aaa.com")
                .intro("testIntro1")
                .password("1234")
                .username("testUserB")
                .role(UserRoleEnum.USER)
                .build();

        ReflectionTestUtils.setField(userA, "id", 1L);
        ReflectionTestUtils.setField(userB, "id", 2L);

    }

    @Test
    void Topster를_등록할_수_있다(){

        //given
         AlbumInsertReq albumReqA = AlbumInsertReq.builder().
                title("albumA 제목").
                image("albumA 이미지").
                artist("albumA 가수").
                releaseDate("albumA 발매일").
                build();

        AlbumInsertReq albumReqB = AlbumInsertReq.builder().
                title("albumB 제목").
                image("albumB 이미지").
                artist("albumB 가수").
                releaseDate("albumB 발매일").
                build();

        AlbumInsertReq albumReqC = AlbumInsertReq.builder().
                title("albumC 제목").
                image("albumC 이미지").
                artist("albumC 가수").
                releaseDate("albumC 발매일").
                build();

        albumA = Album.builder().title(albumReqA.getTitle()).image(albumReqA.getImage()).
                release(albumReqA.getReleaseDate()).artist(albumReqA.getArtist()).build();

        albumB = Album.builder().title(albumReqB.getTitle()).image(albumReqB.getImage()).
                release(albumReqB.getReleaseDate()).artist(albumReqB.getArtist()).build();

        albumC = Album.builder().title(albumReqC.getTitle()).image(albumReqC.getImage()).
                release(albumReqC.getReleaseDate()).artist(albumReqC.getArtist()).build();

        List<AlbumInsertReq> albumInsertReqList = new ArrayList<>();
        albumInsertReqList.add(albumReqA);
        albumInsertReqList.add(albumReqB);
        albumInsertReqList.add(albumReqC);

        TopsterCreateReq topsterCreateReq = TopsterCreateReq.builder().
                title("탑스터 제목").
                albums(albumInsertReqList).build();

        Topster topster = Topster.builder().title("탑스터 제목").
                user(userA).
                content("없어").
                build();

        given(albumService.getAlbumByTitleOrCreate(albumReqA.getTitle(),
                albumReqA.getImage(),
                albumReqA.getArtist(),
                albumReqA.getReleaseDate())).willReturn(albumA);

        given(albumService.getAlbumByTitleOrCreate(albumReqB.getTitle(),
                albumReqB.getImage(),
                albumReqB.getArtist(),
                albumReqB.getReleaseDate())).willReturn(albumB);

        given(albumService.getAlbumByTitleOrCreate(albumReqC.getTitle(),
                albumReqC.getImage(),
                albumReqC.getArtist(),
                albumReqC.getReleaseDate())).willReturn(albumC);


        //when
        TopsterCreateRes topsterCreateRes = topsterService.createTopster(topsterCreateReq, userA);

        //then
        assertThat(topsterCreateRes.getTitle()).isEqualTo("탑스터 제목");
        assertThat(topsterCreateRes.getAlbums().get(0).getTitle()).
                isEqualTo("albumA 제목");

        assertThat(topsterCreateRes.getTitle()).isEqualTo("탑스터 제목");
        assertThat(topsterCreateRes.getAlbums().get(1).getTitle()).
                isEqualTo("albumB 제목");

        assertThat(topsterCreateRes.getTitle()).isEqualTo("탑스터 제목");
        assertThat(topsterCreateRes.getAlbums().get(2).getTitle()).
                isEqualTo("albumC 제목");
    }


    @Test
    @DisplayName("getTopsterService 성공")
    void test2() {
        //given
        Topster topster = Topster.builder().title("탑스터 제목").
                user(userA).
                content("없어").
                build();

        when(topsterRepository.findById(any())).thenReturn(Optional.of(topster));
        //when
        TopsterGetRes topsterGetRes = topsterService.getTopsterService(any());
        //then
        assertThat(topsterGetRes.getTitle()).isEqualTo(topster.getTitle());
        assertThat(topsterGetRes.getAuthor()).isEqualTo(topster.getUser().getNickname());
    }


}
