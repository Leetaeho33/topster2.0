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
![22](https://github.com/GyungKu/topster2.0/assets/148296128/a73bafcc-8dad-4fd9-b7e6-51fdd76c322록 했습니다.
    3. 데이터 수정이 일어나지 않기 때문에 NoSql인 Redis에 적합합니다.
- **TTL을 레디스에서 설정할 수 있음**
    1. Refresh Token은 만료기한이 7일.
    2. Refresh Token이 만료될 때 따로 삭제 로직을 작성할 필요 없습니다.
- **Key-Value로 이루어진 Redis**
    1. Key값으로 Refresh Token을, Value로 유저의 Id를 넣는 방식으로 구현했습니다.
  </div>
</details>
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
저희는 기존에 서버간 HTTP 통신을 위해 RestTemplate를 사용하고 있었습니다. 하지만, RestTemplate은 오래된 기술이며, 더 이상 업데이트가 이루어지지 않는 것으로 알려져 있습니다. 심지어, 클래스 파일 내에서는 RestClient와 WebClient의 사용을 권장하고 있습니다.


따라서, 시스템을 더 최신의 기술로 마이그레이션하는 것이 필요하다고 판단하였습니다. 여기서 두 가지 선택지, WebClient와 RestClient가 있었습니다.

WebClient는 비동기식의 처리 방식을 제공하지만, 이를 사용하기 위해서는 WebFlux 의존성을 추가해야 하는 단점이 있었습니다. 이는 우리 시스템에 추가적인 변경을 요구하므로, 이를 선택하지 않았습니다.

대신, RestClient를 선택하였습니다. RestClient는 Spring 6.1(또는 Spring Boot 3.2)부터 지원되는 최신 기술로, 앞으로 지속적인 지원이 이루어질 것으로 예상되었습니다.

RestClient를 선택한 후, 장점을 명확하게 느낄 수 있었습니다. 특히, 메소드 체이닝 방식을 사용함으로써 코드의 가독성이 크게 향상되었습니다.

- **RestTemplate을 사용할 때 코드**
<pre><code>
  //query는 앨범 검색시 사용한 검색어
RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-type", "application/json");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest
                .exchange("https://api.spotify.com/v1/search?type=album&q="
                        + query + "&limit=30", HttpMethod.GET, requestEntity, String.class);
</code></pre>

- **RestClient를 사용할 때 코드**
<pre><code>
//query는 앨범 검색시 사용한 검색어
Consumer<HttpHeaders> headersConsumer = (headers) -> {
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add("Host", "api.spotify.com");
            headers.add("Content-type", "application/json");
        };

				
        ResponseEntity<String> responseEntity = restClient.get()
                .uri("https://api.spotify.com/v1/search?type=album&q=" + query + "&limit=30")
                .headers(headersConsumer)
                .retrieve()
                .toEntity(String.class);
</code></pre>

- **최종 코드**
<pre><code>
//query는 앨범 검색시 사용한 검색어
URI uri = UriComponentsBuilder
                .fromUriString("https://api.spotify.com/v1/search")
                .queryParam("type", "album")
                .queryParam("q", query) 
                .queryParam("limit", "30")
                .encode()
                .build()
                .toUri();

        Consumer<HttpHeaders> headersConsumer = (headers) -> {
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add("Host", "api.spotify.com");
            headers.add("Content-type", "application/json");
        };

        ResponseEntity<String> responseEntity = restClient.get()
                .uri(uri)
                .headers(headersConsumer)
                .retrieve()
                .toEntity(String.class
</code></pre>

</div>
</details>



# 디렉토리 구조

---
<details>
<summary>디렉토리 구조</summary>
	
    └─src
        ├─main
        │  ├─java
        │  │  └─com
        │  │      └─sparta
        │  │          └─topster
        │  │              ├─domain
        │  │              │  ├─album
        │  │              │  │  ├─controller
        │  │              │  │  ├─dto
        │  │              │  │  │  ├─req
        │  │              │  │  │  └─res
        │  │              │  │  ├─entity
        │  │              │  │  ├─exception
        │  │              │  │  ├─repository
        │  │              │  │  └─service
        │  │              │  ├─comment
        │  │              │  │  ├─controller
        │  │              │  │  ├─dto
        │  │              │  │  │  ├─req
        │  │              │  │  │  └─res
        │  │              │  │  ├─entity
        │  │              │  │  ├─exception
        │  │              │  │  ├─repository
        │  │              │  │  └─service
        │  │              │  ├─follow
        │  │              │  │  ├─controller
        │  │              │  │  ├─dto
        │  │              │  │  ├─entity
        │  │              │  │  ├─exception
        │  │              │  │  ├─repository
        │  │              │  │  └─service
        │  │              │  ├─like
        │  │              │  │  ├─controller
        │  │              │  │  ├─dto
        │  │              │  │  ├─entity
        │  │              │  │  ├─repository
        │  │              │  │  └─service
        │  │              │  ├─openApi
        │  │              │  │  ├─exception
        │  │              │  │  └─service
        │  │              │  │      ├─maniadb
        │  │              │  │      └─spotify
        │  │              │  ├─post
        │  │              │  │  ├─controller
        │  │              │  │  ├─dto
        │  │              │  │  │  ├─request
        │  │              │  │  │  └─response
        │  │              │  │  ├─entity
        │  │              │  │  ├─exception
        │  │              │  │  ├─repository
        │  │              │  │  └─service
        │  │              │  ├─song
        │  │              │  │  ├─entity
        │  │              │  │  └─repository
        │  │              │  ├─sse
        │  │              │  ├─topster
        │  │              │  │  ├─controller
        │  │              │  │  ├─dto
        │  │              │  │  │  ├─req
        │  │              │  │  │  └─res
        │  │              │  │  ├─entity
        │  │              │  │  ├─exception
        │  │              │  │  ├─facade
        │  │              │  │  ├─repository
        │  │              │  │  └─service
        │  │              │  ├─topsterAlbum
        │  │              │  │  ├─entity
        │  │              │  │  ├─repository
        │  │              │  │  └─service
        │  │              │  └─user
        │  │              │      ├─config
        │  │              │      ├─controller
        │  │              │      ├─dto
        │  │              │      │  ├─deleteDto
        │  │              │      │  ├─getUser
        │  │              │      │  ├─kakao
        │  │              │      │  ├─login
        │  │              │      │  ├─modifyPassword
        │  │              │      │  ├─signup
        │  │              │      │  └─update
        │  │              │      ├─entity
        │  │              │      ├─excepetion
        │  │              │      ├─repository
        │  │              │      └─service
        │  │              │          ├─google
        │  │              │          ├─kakao
        │  │              │          ├─mail
        │  │              │          └─user
        │  │              └─global
        │  │                  ├─config
        │  │                  ├─exception
        │  │                  ├─filter
        │  │                  ├─response
        │  │                  ├─security
        │  │                  └─util
        │  └─resources
        │      └─templates
        └─test
            ├─java
            │  └─com
            │      └─sparta
            │          └─topster
            │              └─domain
            │                  ├─album
            │                  │  ├─entity
            │                  │  └─service
            │                  ├─comment
            │                  │  ├─entity
            │                  │  └─service
            │                  ├─post
            │                  │  ├─entity
            │                  │  ├─repository
            │                  │  └─service
            │                  ├─topster
            │                  │  └─service
            │                  └─user
            └─resources
    
    
</details>
