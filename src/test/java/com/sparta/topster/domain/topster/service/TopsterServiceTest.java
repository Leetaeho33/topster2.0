package com.sparta.topster.domain.topster.service;

import com.sparta.topster.domain.album.dto.req.AlbumInsertReq;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.album.service.AlbumService;
import com.sparta.topster.domain.topster.dto.req.TopsterCreateReq;
import com.sparta.topster.domain.topster.dto.res.TopsterCreateRes;
import com.sparta.topster.domain.topster.dto.res.TopsterGetRes;
import com.sparta.topster.domain.topster.dto.res.TopsterPageRes;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster.repository.TopsterRepository;
import com.sparta.topster.domain.topster_album.entity.TopsterAlbum;
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

        albumA = Album.builder()
                .title("albumA 제목")
                .image("albumA 이미지")
                .release("albumA 발매일")
                .artist("albumA가수")
                .build();

        albumB = Album.builder()
                .title("albumB 제목")
                .image("albumB 이미지")
                .release("albumB 발매일")
                .artist("albumB 가수")
                .build();


        ReflectionTestUtils.setField(userA, "id", 1L);
        ReflectionTestUtils.setField(userB, "id", 2L);

    }

    @Test
    @DisplayName("Topster를_등록할_수_있다")
    void testCreate(){

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

        given(topsterRepository.save(any())).willReturn(topster);

        //when
        Topster savedTopster = topsterService.create(topsterCreateReq, userA);

        //then
        assertThat(savedTopster.getTitle()).isEqualTo(topster.getTitle());
        assertThat(savedTopster.getUser().getId()).
                isEqualTo(1L);

    }


    @Test
    @DisplayName("getTopsterService 성공")
    void testGetTopsterService() {
        //given
        Topster topster = Topster.builder().title("탑스터 제목").
                user(userA).
                build();

        when(topsterRepository.findById(any())).thenReturn(Optional.of(topster));
        //when
        TopsterGetRes topsterGetRes = topsterService.getTopsterService(any());
        //then
        assertThat(topsterGetRes.getTitle()).isEqualTo(topster.getTitle());
        assertThat(topsterGetRes.getAuthor()).isEqualTo(topster.getUser().getNickname());
    }

    @Test
    @DisplayName("Topster Entity -> TopsterGet DTO")
    void testFromTopsterToTopsterGetRes(){

        //given 첫번째 유저의 Topster List
        //userA의 첫번째 탑스터
        Topster topsterA = Topster.builder()
                .user(userA)
                .title("topsterA")
                .build();
        //userA의 두번째 탑스터
        Topster topsterB = Topster.builder()
                .user(userA)
                .title("topsterB")
                .build();

        //유저 A의 첫번재 탑스터의 첫번재 TopsterAlbum
        TopsterAlbum topsterAlbumA = TopsterAlbum.builder()
                .album(albumA).topster(topsterA).build();
        //유저 A의 첫번재 탑스터의 두번재 TopsterAlbum
        TopsterAlbum topsterAlbumB = TopsterAlbum.builder()
                .album(albumB).topster(topsterA).build();
        //유저 A의 두번재 탑스터의 첫번재 TopsterAlbum
        TopsterAlbum topsterAlbumC = TopsterAlbum.builder()
                .album(albumA).topster(topsterB).build();
        //유저 A의 첫번재 탑스터의 두번재 TopsterAlbum
        TopsterAlbum topsterAlbumD = TopsterAlbum.builder()
                .album(albumB).topster(topsterB).build();

        topsterA.getTopsterAlbumList().add(topsterAlbumA);
        topsterA.getTopsterAlbumList().add(topsterAlbumB);

        topsterB.getTopsterAlbumList().add(topsterAlbumC);
        topsterB.getTopsterAlbumList().add(topsterAlbumD);

        List<Topster> topsterList = new ArrayList<>();
        topsterList.add(topsterA);
        topsterList.add(topsterB);
        when(topsterRepository.findByUserId(1L)).thenReturn(topsterList);

        //given 두번째 유저의 Topster List
        Topster topsterOtherUserA = Topster.builder()
                .user(userB)
                .title("topsterOtherUserA")
                .build();

        Topster topsterOtherUserB = Topster.builder()
                .user(userB)
                .title("topsterOtherUserB")
                .build();

        //Other 유저의 첫번재 탑스터의 두번째 TopsterAlbum
        TopsterAlbum topsterAlbumOtherUserA = TopsterAlbum.builder()
                .album(albumA).topster(topsterOtherUserA).build();
        //Other 유저의 첫번재 탑스터의 두번째 TopsterAlbum
        TopsterAlbum topsterAlbumOtherUserB = TopsterAlbum.builder()
                .album(albumB).topster(topsterOtherUserA).build();
        //Other 유저의 두번째 탑스터의 첫번재 TopsterAlbum
        TopsterAlbum topsterAlbumOtherUserC = TopsterAlbum.builder()
                .album(albumA).topster(topsterOtherUserB).build();
        //Other 유저의 두번째 탑스터의 두번재 TopsterAlbum
        TopsterAlbum topsterAlbumOtherUserD = TopsterAlbum.builder()
                .album(albumB).topster(topsterOtherUserB).build();

        topsterOtherUserA.getTopsterAlbumList().add(topsterAlbumOtherUserA);
        topsterOtherUserA.getTopsterAlbumList().add(topsterAlbumOtherUserB);

        topsterOtherUserB.getTopsterAlbumList().add(topsterAlbumOtherUserC);
        topsterOtherUserB.getTopsterAlbumList().add(topsterAlbumOtherUserD);

        List<Topster> topsterListOtherUser = new ArrayList<>();
        topsterListOtherUser.add(topsterOtherUserA);
        topsterListOtherUser.add(topsterOtherUserB);
        when(topsterRepository.findByUserId(2L)).thenReturn(topsterListOtherUser);

        //when
        List<TopsterGetRes> topsterGetResListA =
                topsterService.getTopsterByUserService(1L);

        List<TopsterGetRes> topsterGetResListB =
                topsterService.getTopsterByUserService(2L);
        //then
        assertThat(topsterA.getTitle()).isEqualTo(topsterGetResListA.get(0).getTitle());
        assertThat(topsterA.getUser().getNickname())
                .isEqualTo(topsterGetResListA.get(0).getAuthor());
        assertThat(topsterB.getTitle()).isEqualTo(topsterGetResListA.get(1).getTitle());
        assertThat(topsterB.getUser().getNickname())
                .isEqualTo(topsterGetResListA.get(1).getAuthor());

        assertThat(topsterOtherUserA.getTitle()).isEqualTo(topsterGetResListB.get(0).getTitle());
        assertThat(topsterOtherUserA.getUser().getNickname())
                .isEqualTo(topsterGetResListB.get(0).getAuthor());
        assertThat(topsterOtherUserB.getTitle()).isEqualTo(topsterGetResListB.get(1).getTitle());
        assertThat(topsterOtherUserB.getUser().getNickname())
                .isEqualTo(topsterGetResListB.get(1).getAuthor());
    }


}