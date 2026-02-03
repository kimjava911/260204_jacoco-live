# 1단계: 빌드 (Builder)
FROM gradle:jdk17-alpine AS builder
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .
# 테스트 없이 빌드 (테스트는 CI 단계에서 이미 수행됨)
RUN gradle bootJar -x test

# 2단계: 실행 (Runner)
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
# 빌드 단계에서 생성된 JAR 파일만 복사
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
