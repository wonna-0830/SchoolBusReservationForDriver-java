# SchoolBusReservationForDriver-java

스쿨버스 운행을 위한 **운전자 전용 안드로이드 애플리케이션(Java 버전)** 입니다.  
운전자가 예약 현황을 확인하고, 운행 정보를 관리할 수 있도록 설계되었습니다.
- **[🟦 운전자 앱 시연 영상 보기](https://youtube.com/shorts/awHSWGFRi0o?feature=share)**
- 2024 대구가톨릭대학교 캡스톤디자인 🥉장려상 수상작입니다.

---

## 주요 기능

### 1. 로그인 / 회원가입
- 이메일, 비밀번호를 통한 계정 로그인
- Firebase Authentication 연동

### 2. 노선 및 시간 선택
- Spinner를 통해 노선과 시간 선택
- 선택한 노선/시간 정보를 다음 화면으로 전달

### 3. 예약 현황 확인
- Firebase에서 예약 데이터를 가져와 정류장별로 정리
- 예약자 수를 각 정류장별로 출력
- 예약 수에 따라 인원 실시간 변경

### 4. 운행 기록 확인
- RecyclerView를 이용해 과거 운행 기록 목록 출력
- 각 기록 별 삭제 버튼 클릭 시 삭제 가능

### 5. 기타 기능
- 로그아웃
- 예약 데이터 삭제
- 기본 테마 적용 (라이트 모드)

---

## 사용 기술

| 항목 | 내용 |
|------|------|
| 언어 | Java |
| UI | Android XML |
| 백엔드 | Firebase Firestore |
| 인증 | Firebase Authentication |
| 컴포넌트 | RecyclerView, Spinner, Intent 등 |

---

> 📌 이 프로젝트는 실제 스쿨버스 운행 업무에 필요한 기능을 운전자의 입장에서 구현한 앱입니다.
>  
> Android Studio 및 Firebase를 기반으로 구성되어 있습니다.
