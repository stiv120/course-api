@echo off
REM Script para iniciar la aplicación Course API con Docker en Windows

echo ==========================================
echo Course API - Docker Setup
echo ==========================================
echo.

REM Verificar si Docker está corriendo
docker info >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Docker no esta corriendo. Por favor inicia Docker Desktop.
    pause
    exit /b 1
)

echo [OK] Docker esta corriendo
echo.

REM Construir y levantar los servicios
echo [INFO] Construyendo y levantando servicios...
docker-compose up --build -d

if errorlevel 1 (
    echo [ERROR] Error al levantar los servicios
    pause
    exit /b 1
)

echo.
echo [INFO] Esperando a que los servicios esten listos...
timeout /t 10 /nobreak >nul

REM Verificar estado de los servicios
echo.
echo [INFO] Estado de los servicios:
docker-compose ps

echo.
echo ==========================================
echo [OK] Servicios iniciados!
echo ==========================================
echo.
echo [INFO] Aplicacion: http://localhost:8080
echo [INFO] API Students: http://localhost:8080/api/v1/students
echo [INFO] MySQL: localhost:3306
echo.
echo [INFO] Para ver logs: docker-compose logs -f
echo [INFO] Para detener: docker-compose down
echo.
pause
