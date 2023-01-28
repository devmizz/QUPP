# QUPP

### 담당한 부분

- 팀 리딩 및 코드 리뷰 총괄
- Rest API를 통한 질문, 답변 CRUD
- AWS S3를 이용하여 질의 및 답변에 사진을 업로드 기능 추가
- React.js를 이용하여 프론트 작업

### 시연영상

https://user-images.githubusercontent.com/92210823/215261570-a08c696b-d597-445d-9d7f-6cb7eb20bea5.mp4

## 🔥 QUPP란?

QUPP는 기존 질의응답 서비스들의 타겟이 되지 못했던 이들을 위해 기획된 서비스입니다.

QUPP는 Question University Problem Platform의 약어로, 대학생들의 학업상 궁금증을 해소해주겠다는 의미를 담고 있습니다.

[QUPP 소개 PDF 확인 가능](https://github.com/likelion-backendschool/QUPP/blob/main/QUPP.pdf)

### 기존 서비스의 한계점
- 연령층
  - 콴다: 고등학생 이하를 대상으로 한다.
- 영역
  - Chegg: 공학
  - StackOverFlow: 개발

### 개선안
- (연령층) 학부 수준에 관심이 있는 누구나
- (영역) 모든 전공을 커버

## 😎 프로젝트 세부 사항

### 로드맵 및 진행상황

[노션 페이지](https://www.notion.so/qupp/328abb459b894647b1ad8cc35d7ad4da) 를 통해 확인 가능

## ⭐️️ Git 규칙

### ❗️ Branch

#### main
  - 배포
#### feature
  - 기능 개발
  - ex. User의 login 관련 기능을 개발하는 경우: feature-user-login
####  hotfix
  - 버그 수정
  - ex. User의 login 관련 버그를 수정하는 경우: hotfix-user-login
#### others
  - 그 외의 것
  - 리팩토링, 문서작성, 설정변경 등

### ❗️ Commit

- commit 규칙은 [다음](https://jason-api.tistory.com/89) 을 참조하되, 완화시켜 진행

- 예시
> 타입(Type): 제목(Title)
> 
> 본문

#### 타입(Type)

- FEAT: 기능개발
- FIX: 버그수정
- DOCS: 문서수정
- STYLE: 스타일수정 (들여쓰기, 세미콜론 등)
- REFACTOR: 리팩토링
- TEST: 테스트코드
- CHORE: 빌드, 패키지매니저 수정 (gitignore 등)

#### 제목(Title)

- 첫글자는 대문자
- 마침표(.) 없이 종결

#### 본문

- 제목과 본문 사이에는 한 줄 공백
- 본문에는 '어떻게'가 아닌 '무엇'을 '왜' 했는지 기입

## 팀회고

### 💡테크톡처럼 해보기

- 현재의 수업 스타일에서 어떠한 개념을 구체적으로 복습하기란 어렵다.
- 테크톡처럼 주제를 한 가지 정하여, 어느정도 깊이 있게 공부를 해보자.
- 면접 질문에도 대비하고 포토폴리오도 될 수 있는, 두 마리의 토끼를 잡아보자.
- 주제 선정은 '해당 주차에 발표하는 사람끼리 주제가 겹치지 않으면서 & 전 주에 다뤘던 내용 중 하나'
- ex. 8/18 발표: 8/8 ~ 8/12의 수업 내용 중 하나를 선택

|회차|날짜|주제|담당자| 진행사항 |
|---|---|---|---|:----:|
|1|22.08.18|Restful API<br>쿠키/세션/토큰|김세은<br>민찬기|✅<br>✅|
|2|22.08.25|Bean<br>Maven&Gradle|송용호<br>임소망|✅<br>✅|
|3|22.09.01|Transaction<br>Properties|정영환<br>김세은|✅<br>✅|
|4|22.09.08|Singleton<br>Stream|민찬기<br>송용호|✅<br>✅|
|5|22.09.15|HTTP&HTTPS<br>Interface|임소망<br>정영환|✅<br>✅|
|6|22.09.22|OAuth<br>CORS|김세은<br>민찬기|✅<br>✅|
