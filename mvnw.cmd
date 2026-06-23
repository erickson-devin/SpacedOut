@echo off
setlocal

:: Set project base to the directory containing this script
set "MAVEN_PROJECTBASEDIR=%~dp0"
:: Remove trailing backslash
if "%MAVEN_PROJECTBASEDIR:~-1%"=="\" set "MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%"

set "WRAPPER_DIR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper"
set "WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar"
set "WRAPPER_PROPS=%WRAPPER_DIR%\maven-wrapper.properties"

:: Download wrapper jar if missing
if not exist "%WRAPPER_JAR%" (
    echo Downloading Maven Wrapper...
    if not exist "%WRAPPER_DIR%" mkdir "%WRAPPER_DIR%"
    powershell -NoProfile -ExecutionPolicy Bypass -Command ^
        "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar' -OutFile '%WRAPPER_JAR%'"
    if errorlevel 1 (
        echo ERROR: Could not download maven-wrapper.jar
        echo Please check your internet connection and try again.
        exit /b 1
    )
)

:: Verify java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo.
    echo ERROR: 'java' not found on PATH.
    echo.
    echo Please install JDK 21 from https://adoptium.net/
    echo After installing, CLOSE this terminal and open a NEW one, then try again.
    echo.
    exit /b 1
)

:: Launch Maven via the wrapper jar
java -cp "%WRAPPER_JAR%" "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" org.apache.maven.wrapper.MavenWrapperMain %*
endlocal
