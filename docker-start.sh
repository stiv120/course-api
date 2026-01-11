#!/bin/bash

# Script para iniciar la aplicación Course API con Docker

echo "=========================================="
echo "Course API - Docker Setup"
echo "=========================================="
echo ""

# Verificar si Docker está corriendo
if ! docker info > /dev/null 2>&1; then
    echo "❌ Error: Docker no está corriendo. Por favor inicia Docker Desktop."
    exit 1
fi

echo "✅ Docker está corriendo"
echo ""

# Construir y levantar los servicios
echo "🔨 Construyendo y levantando servicios..."
docker-compose up --build -d

echo ""
echo "⏳ Esperando a que los servicios estén listos..."
sleep 10

# Verificar estado de los servicios
echo ""
echo "📊 Estado de los servicios:"
docker-compose ps

echo ""
echo "=========================================="
echo "✅ Servicios iniciados!"
echo "=========================================="
echo ""
echo "📍 Aplicación: http://localhost:8080"
echo "📍 API Students: http://localhost:8080/api/v1/students"
echo "📍 MySQL: localhost:3306"
echo ""
echo "📝 Para ver logs: docker-compose logs -f"
echo "🛑 Para detener: docker-compose down"
echo ""
