# ----------------------------------------------------
# 1단계: 빌드 스테이지 (Build Stage) - Gradle 환경
# ----------------------------------------------------
FROM gradle:8.5.0-jdk17 AS build

# 컨테이너 내 작업 디렉터리 설정
WORKDIR /app

# Gradle Wrapper 파일들을 복사 (빌드 환경 설정)
COPY gradlew .
COPY gradle gradle

# build.gradle과 settings.gradle 파일을 먼저 복사하여 종속성 캐시 활용
COPY build.gradle .
COPY settings.gradle .

# 종속성만 미리 다운로드하여 빌드 캐시 효율 높이기
RUN ./gradlew dependencies

# 나머지 소스 코드 복사
COPY src ./src

# 실제 빌드 수행
# 'bootJar' 태스크로 실행 가능한 JAR 파일 생성
RUN ./gradlew bootJar -x test

# ----------------------------------------------------
# 2단계: 실행 스테이지 (Run Stage) - JRE 환경
# ----------------------------------------------------
FROM eclipse-temurin:17-jre-focal

# 최종 빌드된 JAR 파일을 1단계에서 2단계로 복사
# build/libs 폴더에 생성된 JAR 파일을 app.jar로 복사
# *.jar 대신 build/libs/*.jar로 경로를 지정하는 것이 일반적입니다.
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 스프링 부트 기본 포트
EXPOSE 8080
