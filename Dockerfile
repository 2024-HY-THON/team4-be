FROM openjdk:21-jdk-slim

# 빌드된 JAR 파일 복사
COPY /build/libs/hython-0.0.1-SNAPSHOT.jar app.jar

# Firebase 설정 파일 복사 (컨테이너 내 실제 경로로 복사)
COPY src/main/resources/hython.json /app/config/hython.json

# Firebase 설정 파일이 컨테이너에서 사용할 수 있도록 환경 변수 설정
ENV FIREBASE_CONFIG=/app/config/hython.json

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
