# chmod +x gradlew

# Gradle 명령어 정의
GRADLE := ./gradlew

# 기본 목표 정의
.DEFAULT_GOAL := help

# 도움말
help:
	@echo "사용 가능한 명령어:"
	@echo "  make test      - 모든 테스트 실행"
	@echo "  make build     - 프로젝트 빌드"
	@echo "  make clean     - 빌드 파일 삭제"
	@echo "  make run       - 애플리케이션 실행"

# 테스트 실행
test:
	$(GRADLE) test

# 프로젝트 빌드
build:
	$(GRADLE) build

# 빌드 파일 삭제
clean:
	$(GRADLE) clean

# 애플리케이션 실행 (bootRun 태스크가 있다고 가정)
run:
	$(GRADLE) bootRun

# .PHONY 선언
.PHONY: help test build clean run
