FROM openjdk:21-jdk-slim

# 빌드된 JAR 파일 복사
COPY /build/libs/hython-0.0.1-SNAPSHOT.jar app.jar

# Firebase 설정 파일 복사 (경로를 실제 위치에 맞게 조정)
COPY /path/to/hython.json /app/config/hython.json

# Firebase 설정 파일이 컨테이너에서 사용할 수 있도록 환경 변수 설정
ENV FIREBASE_CONFIG=/app/config/hython.json

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
