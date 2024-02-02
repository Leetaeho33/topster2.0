# Topster 2.0

# 프로젝트 소개

주제에 맞게 자신이 생각하는 Best 음악 앨범 List를 뽑아 Topster를 만들어요. 이 Topster를 커뮤니티 회원들과 공유합니다.

- 기존의 탑스터는 앨범 표지들만 모아놓은 사진에 불과했습니다.
- Topster2.0은 나만의 탑스터를 만들 수 있고, 그 앨범의 디테일(가수, 앨범 이름, 설명 등등..)들을 볼 수 있습니다.
- 나의 탑스터를 게시판에 공유하고, 댓글을 달고 좋아요를 누를 수 있습니다. 좋아요 수가 높은 탑스터는 상위에 노출됩니다.

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
