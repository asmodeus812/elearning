# -----------------------------
# Optional test selection knobs
# -----------------------------
TEST   ?=   # single test (meaning depends on project type)
SUITE  ?=   # suite/class/module (meaning depends on project type)
METHOD ?=   # method (mainly maven/gradle)
REGEX  ?=   # regex/name filter (pytest/go/ctest/cargo)
PKG    ?=   # package target (go)

MVN_TEST_SELECTOR     := $(if $(TEST),-Dtest=$(TEST)$(if $(METHOD),#$(METHOD),),$(if $(SUITE),-Dtest=$(SUITE),))
GRADLE_TEST_SELECTOR  := $(if $(TEST),--tests '$(TEST)$(if $(METHOD),.$(METHOD),)',$(if $(SUITE),--tests '$(SUITE)',))
NPM_TEST_SELECTOR     := $(if $(TEST),-- $(TEST),$(if $(SUITE),-- $(SUITE),$(if $(REGEX),-- $(REGEX),)))

CTEST_SELECTOR        := $(if $(REGEX),-R '$(REGEX)',)

GO_TARGET             ?= ./...
GO_TEST_SELECTOR      := $(if $(REGEX),-run '$(REGEX)',$(if $(TEST),-run '^$(TEST)$$',))

CARGO_TEST_SELECTOR   := $(if $(TEST),$(TEST),$(if $(REGEX),$(REGEX),))

PYTEST_SELECTOR       := $(if $(TEST),$(TEST),$(if $(SUITE),$(SUITE),$(if $(REGEX),-k '$(REGEX)',)))

DOTNET_TEST_SELECTOR  := $(if $(TEST),--filter "FullyQualifiedName~$(TEST)",$(if $(SUITE),--filter "FullyQualifiedName~$(SUITE)",$(if $(REGEX),--filter "$(REGEX)",)))

# ------------------------------------------------------------
# Project detection + commands
# ------------------------------------------------------------
ifneq ($(wildcard pom.xml),)
PROJECT_TYPE := maven
BUILD_CMD := mvn compile
CLEAN_CMD := mvn clean
TEST_CMD := mvn test $(MVN_TEST_SELECTOR)

else ifneq ($(wildcard build.gradle),)
PROJECT_TYPE := gradle
BUILD_CMD := ./gradlew classes
CLEAN_CMD := ./gradlew clean
TEST_CMD := ./gradlew test $(GRADLE_TEST_SELECTOR)

else ifneq ($(wildcard package.json),)
PROJECT_TYPE := npm
BUILD_CMD := npm run build
CLEAN_CMD := rm -rf node_modules dist
TEST_CMD := npm test $(NPM_TEST_SELECTOR)

else ifneq ($(wildcard CMakeLists.txt),)
PROJECT_TYPE := cmake
BUILD_CMD := mkdir -p build && cd build && cmake .. && make
CLEAN_CMD := rm -rf build
TEST_CMD := cd build && ctest $(CTEST_SELECTOR)

# NOTE: Don't match "Makefile" here, because this wrapper itself is a Makefile.
# If you want delegation to another make-based project, name it Makefile.project.
else ifneq ($(wildcard Makefile.project),)
PROJECT_TYPE := make
BUILD_CMD := $(MAKE) -f Makefile.project build
CLEAN_CMD := $(MAKE) -f Makefile.project clean
TEST_CMD := $(MAKE) -f Makefile.project test TEST="$(TEST)" SUITE="$(SUITE)" METHOD="$(METHOD)" REGEX="$(REGEX)" PKG="$(PKG)"

else ifneq ($(wildcard go.mod),)
PROJECT_TYPE := go
BUILD_CMD := go build ./...
CLEAN_CMD := go clean
TEST_CMD := go test $(if $(PKG),$(PKG),$(GO_TARGET)) $(GO_TEST_SELECTOR)

else ifneq ($(wildcard Cargo.toml),)
PROJECT_TYPE := cargo
BUILD_CMD := cargo build
CLEAN_CMD := cargo clean
TEST_CMD := cargo test $(CARGO_TEST_SELECTOR)

else ifneq ($(wildcard setup.py),)
PROJECT_TYPE := python
BUILD_CMD := python -m compileall .
CLEAN_CMD := find . -type d -name "__pycache__" -exec rm -rf {} + && find . -type f -name "*.pyc" -delete
TEST_CMD := python -m pytest $(PYTEST_SELECTOR)

else ifneq ($(wildcard requirements.txt),)
PROJECT_TYPE := python
BUILD_CMD := pip install -r requirements.txt
CLEAN_CMD := find . -type d -name "__pycache__" -exec rm -rf {} + && find . -type f -name "*.pyc" -delete
TEST_CMD := python -m pytest $(PYTEST_SELECTOR)

else ifneq ($(wildcard *.sln),)
PROJECT_TYPE := dotnet
BUILD_CMD := dotnet build
CLEAN_CMD := dotnet clean
TEST_CMD := dotnet test $(DOTNET_TEST_SELECTOR)

else ifneq ($(wildcard *.csproj),)
PROJECT_TYPE := dotnet
BUILD_CMD := dotnet build
CLEAN_CMD := dotnet clean
TEST_CMD := dotnet test $(DOTNET_TEST_SELECTOR)

else ifneq ($(wildcard *.vcxproj),)
PROJECT_TYPE := msbuild
BUILD_CMD := msbuild
CLEAN_CMD := msbuild /t:Clean
TEST_CMD := msbuild /t:Test

else ifneq ($(wildcard *.xcodeproj),)
PROJECT_TYPE := xcode
BUILD_CMD := xcodebuild -scheme $(PROJECT_NAME) build
CLEAN_CMD := xcodebuild -scheme $(PROJECT_NAME) clean
TEST_CMD := xcodebuild -scheme $(PROJECT_NAME) test

else ifneq ($(wildcard *.rb),)
PROJECT_TYPE := ruby
BUILD_CMD := bundle install
CLEAN_CMD := bundle clean --force
TEST_CMD := bundle exec rake test

else ifneq ($(wildcard Gemfile),)
PROJECT_TYPE := ruby
BUILD_CMD := bundle install
CLEAN_CMD := bundle clean --force
TEST_CMD := bundle exec rake test

else ifneq ($(wildcard Rakefile),)
PROJECT_TYPE := ruby
BUILD_CMD := bundle install
CLEAN_CMD := bundle clean --force
TEST_CMD := bundle exec rake test

else ifneq ($(wildcard *.php),)
PROJECT_TYPE := php
BUILD_CMD := composer install
CLEAN_CMD := rm -rf vendor
TEST_CMD := composer test

else ifneq ($(wildcard composer.json),)
PROJECT_TYPE := php
BUILD_CMD := composer install
CLEAN_CMD := rm -rf vendor
TEST_CMD := composer test

else ifneq ($(wildcard *.pl),)
PROJECT_TYPE := perl
BUILD_CMD := perl Makefile.PL && make
CLEAN_CMD := make clean
TEST_CMD := make test

else ifneq ($(wildcard Build.PL),)
PROJECT_TYPE := perl
BUILD_CMD := perl Build.PL && ./Build
CLEAN_CMD := ./Build clean
TEST_CMD := ./Build test

else ifneq ($(wildcard Makefile.PL),)
PROJECT_TYPE := perl
BUILD_CMD := perl Makefile.PL && make
CLEAN_CMD := make clean
TEST_CMD := make test

else ifneq ($(wildcard *.sh),)
PROJECT_TYPE := shell
BUILD_CMD := @echo "No compilation needed for shell scripts"
CLEAN_CMD := find . -type f -name "*.sh" -exec chmod +x {} \;
TEST_CMD := shellcheck *.sh

else ifneq ($(wildcard *.ml),)
PROJECT_TYPE := ocaml
BUILD_CMD := ocamlbuild -use-ocamlfind main.native
CLEAN_CMD := ocamlbuild -clean
TEST_CMD := ocamlbuild -use-ocamlfind test.native && ./test.native

else ifneq ($(wildcard *.rs),)
PROJECT_TYPE := rust
BUILD_CMD := rustc *.rs
CLEAN_CMD := rm -f *.o *.so *.dll *.dylib *.exe
TEST_CMD := cargo test $(CARGO_TEST_SELECTOR)

else ifneq ($(wildcard *.swift),)
PROJECT_TYPE := swift
BUILD_CMD := swift build
CLEAN_CMD := swift package clean
TEST_CMD := swift test

else ifneq ($(wildcard Package.swift),)
PROJECT_TYPE := swift
BUILD_CMD := swift build
CLEAN_CMD := swift package clean
TEST_CMD := swift test

else ifneq ($(wildcard *.kt),)
PROJECT_TYPE := kotlin
BUILD_CMD := kotlinc *.kt -include-runtime -d $(PROJECT_NAME).jar
CLEAN_CMD := rm -f *.jar *.class
TEST_CMD := kotlinc -script test.kts

else ifneq ($(wildcard *.kts),)
PROJECT_TYPE := kotlin
BUILD_CMD := kotlinc *.kt -include-runtime -d $(PROJECT_NAME).jar
CLEAN_CMD := rm -f *.jar *.class
TEST_CMD := kotlinc -script test.kts

else ifneq ($(wildcard build.sbt),)
PROJECT_TYPE := sbt
BUILD_CMD := sbt compile
CLEAN_CMD := sbt clean
TEST_CMD := sbt test

else ifneq ($(wildcard *.scala),)
PROJECT_TYPE := scala
BUILD_CMD := scalac *.scala
CLEAN_CMD := rm -f *.class
TEST_CMD := scala Test

else ifneq ($(wildcard *.dart),)
PROJECT_TYPE := dart
BUILD_CMD := dart compile exe bin/$(PROJECT_NAME).dart
CLEAN_CMD := rm -f *.dart.js && rm -rf .dart_tool
TEST_CMD := dart test

else ifneq ($(wildcard pubspec.yaml),)
PROJECT_TYPE := dart
BUILD_CMD := dart compile exe bin/$(PROJECT_NAME).dart
CLEAN_CMD := rm -f *.dart.js && rm -rf .dart_tool
TEST_CMD := dart test

else ifneq ($(wildcard *.lua),)
PROJECT_TYPE := lua
BUILD_CMD := luac -o $(PROJECT_NAME).out *.lua
CLEAN_CMD := rm -f *.out
TEST_CMD := lua test.lua

else ifneq ($(wildcard *.zig),)
PROJECT_TYPE := zig
BUILD_CMD := zig build
CLEAN_CMD := zig clean
TEST_CMD := zig build test

else ifneq ($(wildcard build.zig),)
PROJECT_TYPE := zig
BUILD_CMD := zig build
CLEAN_CMD := zig clean
TEST_CMD := zig build test

else ifneq ($(wildcard *.c),)
PROJECT_TYPE := c
BUILD_CMD := gcc -o $(PROJECT_NAME) *.c
CLEAN_CMD := rm -f $(PROJECT_NAME) *.o
TEST_CMD := ./$(PROJECT_NAME) --test

else ifneq ($(wildcard *.cpp),)
PROJECT_TYPE := cpp
BUILD_CMD := g++ -o $(PROJECT_NAME) *.cpp
CLEAN_CMD := rm -f $(PROJECT_NAME) *.o
TEST_CMD := ./$(PROJECT_NAME) --test

else ifneq ($(wildcard *.h),)
PROJECT_TYPE := c
BUILD_CMD := gcc -o $(PROJECT_NAME) *.c
CLEAN_CMD := rm -f $(PROJECT_NAME) *.o
TEST_CMD := ./$(PROJECT_NAME) --test

else ifneq ($(wildcard *.hpp),)
PROJECT_TYPE := cpp
BUILD_CMD := g++ -o $(PROJECT_NAME) *.cpp
CLEAN_CMD := rm -f $(PROJECT_NAME) *.o
TEST_CMD := ./$(PROJECT_NAME) --test

else ifneq ($(wildcard *.java),)
PROJECT_TYPE := java
BUILD_CMD := javac *.java
CLEAN_CMD := rm -f *.class
TEST_CMD := java Test

else ifneq ($(wildcard *.ts),)
PROJECT_TYPE := typescript
BUILD_CMD := tsc
CLEAN_CMD := rm -rf dist
TEST_CMD := npm test $(NPM_TEST_SELECTOR)

else ifneq ($(wildcard *.js),)
PROJECT_TYPE := javascript
BUILD_CMD := @echo "No compilation needed for JavaScript"
CLEAN_CMD := rm -rf node_modules
TEST_CMD := npm test $(NPM_TEST_SELECTOR)

else
PROJECT_TYPE := unknown
BUILD_CMD := @echo "[ERROR] Uknown project type - no compile performed"
CLEAN_CMD := @echo "[ERROR] Uknown project type - no clean performed"
TEST_CMD := @echo "[ERROR] Uknown project type - no tests performed"
endif

LOG_INFO = @echo "[INFO]"
LOG_SUCCESS = @echo "[SUCCESS]"
LOG_ERROR = @echo "[ERROR]" >&2

# -----------------------------
# Makefile targets selection knobs
# -----------------------------
.PHONY: build
build:
	$(LOG_INFO) "Building $(PROJECT_TYPE) project"
	$(BUILD_CMD)
	$(LOG_SUCCESS) "Building complete"

.PHONY: clean
clean:
	$(LOG_INFO) "Cleaning $(PROJECT_TYPE) project"
	$(CLEAN_CMD)
	$(LOG_SUCCESS) "Project cleaned"

.PHONY: test
test:
	$(LOG_INFO) "Testing $(PROJECT_TYPE) project"
	$(TEST_CMD)
	$(LOG_SUCCESS) "Testing complete"

.PHONY: help
help:
	@echo "Available make targets: build | clean | test | help"
	@echo ""
	@echo "Test selectors (optional): TEST=...  SUITE=...  METHOD=...  REGEX=...  PKG=..."
	@echo ""
	@echo "Examples:"
	@echo "  # Run everything"
	@echo "  make test"
	@echo ""
	@echo "  # Maven / Gradle (JUnit/TestNG)"
	@echo "  make test TEST=MyTest"
	@echo "  make test TEST=MyTest METHOD=testFoo"
	@echo "  make test SUITE=AllTestsSuite"
	@echo ""
	@echo "  # Python (pytest)"
	@echo "  make test TEST=tests/test_api.py"
	@echo "  make test TEST=tests/test_api.py::test_health"
	@echo "  make test REGEX=health"
	@echo ""
	@echo "  # Go"
	@echo "  make test"
	@echo "  make test PKG=./... REGEX=TestHealth"
	@echo "  make test PKG=./mypkg TEST=TestHealth"
	@echo ""
	@echo "  # CMake/CTest"
	@echo "  make test"
	@echo "  make test REGEX='integration|smoke'"
	@echo ""
	@echo "  # .NET"
	@echo "  make test"
	@echo "  make test TEST=MyNamespace.MyTests"
	@echo ""
	@echo "  # Cargo"
	@echo "  make test"
	@echo "  make test TEST=my_test_name"
	@echo ""
	@echo "  # NPM (depends on runner)"
	@echo "  make test"
	@echo "  make test TEST=watch"
	@echo ""
	@echo "Detected project type: $(PROJECT_TYPE)"
