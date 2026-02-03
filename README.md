# Jacoco 활용 프로젝트

이 프로젝트는 Spring Boot 애플리케이션에서 Jacoco를 사용하여 코드 커버리지를 측정하는 방법을 보여줍니다. GitHub Actions를 통해 다양한 커버리지 프로파일을 선택하여 실행할 수 있습니다.

## 주요 기능

- **Spring Boot 3.5.10** 기반 애플리케이션.
- **Jacoco**를 이용한 코드 커버리지 분석.
- **JUnit 5**와 태그(Tag) 기능을 활용한 테스트 시나리오 분리.
- **GitHub Actions** 워크플로우를 통한 수동 실행 및 커버리지 프로파일 선택.
- **Docker** 이미지 빌드 및 GitHub Container Registry (GHCR) 배포.

## 커버리지 프로파일

이 프로젝트는 JUnit 5의 태그 기능을 사용하여 세 가지 커버리지 프로파일을 구성했습니다.

1.  **20% 프로파일**: `base` 태그가 붙은 테스트만 실행합니다.
    -   목표: 낮은 커버리지 상황 시뮬레이션 (빌드 실패 예상).
2.  **55% 프로파일**: `base` 및 `medium-add` 태그가 붙은 테스트를 실행합니다.
    -   목표: 중간 수준의 커버리지 (50% 기준 통과 목표).
3.  **80% 프로파일**: `base`, `medium-add`, `high-add` 태그가 붙은 모든 테스트를 실행합니다.
    -   목표: 높은 커버리지 달성.

## 빌드 및 테스트

### 로컬 테스트 방법

로컬 환경에서 특정 프로파일로 테스트를 실행하려면 다음 명령어를 사용하세요:

```bash
# 20% 프로파일 실행
./gradlew test -DcoverageProfile=20 jacocoTestReport

# 55% 프로파일 실행
./gradlew test -DcoverageProfile=55 jacocoTestReport

# 80% 프로파일 실행
./gradlew test -DcoverageProfile=80 jacocoTestReport
```

### 커버리지 검증

코드 커버리지가 **50%** 미만일 경우 빌드가 실패하도록 설정되어 있습니다.

```bash
./gradlew jacocoTestCoverageVerification
```

## GitHub Actions 워크플로우

`Jacoco Coverage Check` 워크플로우는 `workflow_dispatch`를 통해 수동으로 트리거됩니다.

1.  GitHub 저장소의 **Actions** 탭으로 이동합니다.
2.  **Jacoco Coverage Check** 워크플로우를 선택합니다.
3.  **Run workflow** 버튼을 클릭합니다.
4.  원하는 **Coverage Profile** (20, 55, 80 중 하나)을 선택합니다.

### 워크플로우 단계

1.  **Checkout**: 소스 코드를 체크아웃합니다.
2.  **Set up JDK 17**: Java 17 환경을 설정합니다.
3.  **Test with Coverage Profile**: 선택한 프로파일에 따라 테스트를 실행합니다.
4.  **Verify Coverage**: 커버리지가 50% 이상인지 검증합니다.
    -   50% 미만이면 여기서 워크플로우가 실패하고 중단됩니다.
5.  **Build with Gradle**: 애플리케이션 JAR를 빌드합니다 (테스트는 이미 수행했으므로 생략).
6.  **Log in to GHCR**: GitHub Container Registry에 로그인합니다.
7.  **Build and push Docker image**: Docker 이미지를 빌드하고 `ghcr.io/<owner>/<repo>:latest`로 푸시합니다.

## Docker

Dockerfile은 `eclipse-temurin:17-jdk-alpine`을 기반으로 하여 경량화된 이미지를 생성합니다.

```dockerfile
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```
