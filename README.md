# Topster 2.0

# 프로젝트 소개

주제에 맞게 자신이 생각하는 Best 음악 앨범 List를 뽑아 Topster를 만들어요. 이 Topster를 커뮤니티 회원들과 공유합니다.

- 기존의 탑스터는 앨범 표지들만 모아놓은 사진에 불과했습니다.
- Topster2.0은 나만의 탑스터를 만들 수 있고, 그 앨범의 디테일(가수, 앨범 이름, 설명 등등..)들을 볼 수 있습니다.
- 나의 탑스터를 게시판에 공유하고, 댓글을 달고 좋아요를 누를 수 있습니다. 좋아요 수가 높은 탑스터는 상위에 노출됩니다.

- # 서비스 예시
---

# 메인 페이지
![11](https://github.com/GyungKu/topster2.0/assets/148296128/c73e1e5b-97d7-4c5d-8478-4fb2494bdbae)

# 탑스터 등록 페이지
![스크린샷 2024-02-02 140631](https://github.com/GyungKu/topster2.0/assets/148296128/4902bd40-b871-4d13-a3fd-8f750715ccb3)

# 탑스터 조회 페이지
![3](https://github.com/GyungKu/topster2.0/assets/148296128/6314dc5c-085e-42ae-bd61-402f1237af6f)

# 게시글 조회 페이지
![44](https://github.com/GyungKu/topster2.0/assets/148296128/6bd02e7f-d013-46bf-92e8-b65fcb410f8a)


# 서비스 아키텍쳐

---
![아키텍처](https://github.com/GyungKu/topster2.0/assets/148296128/055ae083-d157-47a2-8a55-b6e40a4f61e0)



# 기술스택

---

| 기술스택 | 활용 |
| --- | --- |
| 음악 검색 API | 스포티 파이 API, maniaDB API |
| CI/CD | GitHub Action |
| Docker | Back End Server, Front End Server, Nginx를 image로 관리 |
| Data Caching | Redis로 외부 API 데이터 캐싱 |
| OAuth2.0 | 카카오톡 소셜 로그인 |

# 개발환경

---

- JAVA 17 : LTS, Springboot 3.2.1과 연동
- Spring 3.2.1 : 스프링은 버전 업데이트가 자주 됨. Spring의 3.2부터 자바의 17 호환이 됨
- Swagger : 로컬 환경에서 API 테스트
- JMeter : 로컬 환경 서버 부하 테스트

# ERD

---
![topster 2 0 (1)](https://github.com/GyungKu/topster2.0/assets/148296128/7510869f-7756-4e50-822e-eddfa1487f86)

# API명세서

---

# 서비스 플로우

---
![서비스 플로우 drawio](https://github.com/GyungKu/topster2.0/assets/148296128/7e357fe2-3819-4ec1-8158-0fc16718b00f)


# 기술적 의사결정

---

<details>
<summary>Redis</summary>
<div markdown="1">
음악 검색 API Data Caching
  
Refresh Token
</div>
</details>

<details>
<summary>Elastic Beanstalks</summary>
<div markdown="1">
 1. **쉬운 배포 및 관리**: 저희는 Docker compose를 이용하여 Nginx, Spring Boot, Vue.js의 멀티 컨테이너 환경을 구성하고 있었고 배포 과정에서 ECS와 EB 중에서 EB를 선택했습니다. 그 이유는 ECS를 통해 배포를 하기 위해서는 Docker와 AWS의 지식이 더 많이 필요했습니다. 반면 EB는 Docker 컨테이너를 지원하며, 여러 컨테이너로 구성된 환경을 손쉽게 배포 및 관리할 수 있었고, 저희는 시간적 여유가 많지 않아서 더 간단한 EB를 선택했습니다.
  
    2. **확장성 고려의 편리성**: 현재는 EC2 인스턴스를 한 개만 띄우기에 장점이 아닐 수도 있지만, 애플리케이션의 트래픽이 증가하거나 서비스가 확장될 가능성을 고려한다면 EB를 사용하는 것이 장점이 될 수 있습니다. EB는 필요에 따라 자동으로 리소스를 추가하거나 제거하는 오토 스케일링 기능을 제공하므로, 서비스의 성장에 따른 인프라 관리 부담을 줄일 수 있습니다. 또한, 로드 밸런서를 통해 여러 EC2 인스턴스간의 트래픽을 자동으로 분산시킴으로써, 서비스의 가용성을 높이는 역할도 합니다.
    
    3. **환경 설정의 간편성**: EB는 각 환경의 설정을 쉽게 관리하고, 변경할 수 있습니다.
</div>
</details>

<details>
<summary>RestTemplate vs WebClient vs RestClient</summary>
<div markdown="1">

</div>
</details>
    
