# 기존의 openjdk 대신 AWS에서 만든 Java 17 (Amazon Corretto) 사용
FROM amazoncorretto:17

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일을 컨테이너 내부로 복사
COPY build/libs/arto-be-0.0.1-SNAPSHOT.jar app.jar

# 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]