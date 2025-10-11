# ----------------------------------------------------
# 1단계: 빌드 스테이지 (Build Stage)
# 모든 소스 코드를 복사하고 Maven을 사용하여 애플리케이션 JAR 파일을 빌드합니다.
# ----------------------------------------------------
FROM maven:3.9.5-eclipse-temurin-17-focal AS build

# 컨테이너 내 작업 디렉터리 설정
WORKDIR /app

# Maven 캐시를 효율적으로 사용하기 위해 pom.xml만 먼저 복사
COPY pom.xml .
RUN mvn dependency:go-offline

# 나머지 소스 코드 복사
COPY src ./src

# 실제 빌드 수행 (테스트 생략)
RUN mvn package -DskipTests

# ----------------------------------------------------
# 2단계: 실행 스테이지 (Run Stage)
# 최소한의 JRE만 포함된 경량 이미지에 빌드된 JAR만 복사하여 실행합니다.
# ----------------------------------------------------
FROM eclipse-temurin:17-jre-focal

# 환경 변수 설정
ENV PORT 8080

# 최종 빌드된 JAR 파일 이름을 찾아 app.jar로 복사
# (target 폴더 내에 JAR 파일이 하나만 있다고 가정)
COPY --from=build /app/target/*.jar app.jar

# 컨테이너 실행 시 동작할 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 스프링 부트 기본 포트 (워크플로우에서 80:3000 -> 80:8080으로 수정 필요)
EXPOSE 8080
