# Script de Migración - Separar Skills del Módulo Catalog
# Ejecutar desde la raíz del proyecto

Write-Host "=== Iniciando migración de Skills ===" -ForegroundColor Green

$baseDir = "src/main/java/com/portfolio/michael/modules"
$catalogDir = "$baseDir/catalog"
$skillDir = "$baseDir/skill"

# 1. Copiar archivos de dominio
Write-Host "`n1. Copiando archivos de dominio..." -ForegroundColor Yellow
Copy-Item "$catalogDir/domain/Skill.java" "$skillDir/domain/"
Copy-Item "$catalogDir/domain/SkillRepository.java" "$skillDir/domain/"
Copy-Item "$catalogDir/domain/UserSkill.java" "$skillDir/domain/"
Copy-Item "$catalogDir/domain/UserSkillRepository.java" "$skillDir/domain/"

# 2. Copiar archivos de infraestructura
Write-Host "2. Copiando archivos de infraestructura..." -ForegroundColor Yellow
Copy-Item "$catalogDir/infrastructure/persistence/SkillJpaEntity.java" "$skillDir/infrastructure/persistence/"
Copy-Item "$catalogDir/infrastructure/persistence/UserSkillJpaEntity.java" "$skillDir/infrastructure/persistence/"
Copy-Item "$catalogDir/infrastructure/persistence/JpaSkillRepository.java" "$skillDir/infrastructure/persistence/"
Copy-Item "$catalogDir/infrastructure/persistence/JpaUserSkillRepository.java" "$skillDir/infrastructure/persistence/"
Copy-Item "$catalogDir/infrastructure/persistence/SpringDataSkillRepository.java" "$skillDir/infrastructure/persistence/"
Copy-Item "$catalogDir/infrastructure/persistence/SpringDataUserSkillRepository.java" "$skillDir/infrastructure/persistence/"

# 3. Copiar archivos de aplicación
Write-Host "3. Copiando archivos de aplicación..." -ForegroundColor Yellow
Copy-Item "$catalogDir/application/usecase/CreateSkillUseCase.java" "$skillDir/application/usecase/"
Copy-Item "$catalogDir/application/usecase/UpdateSkillUseCase.java" "$skillDir/application/usecase/"
Copy-Item "$catalogDir/application/usecase/DeleteSkillUseCase.java" "$skillDir/application/usecase/"
Copy-Item "$catalogDir/application/usecase/GetSkillsUseCase.java" "$skillDir/application/usecase/"

Write-Host "`n=== Archivos copiados exitosamente ===" -ForegroundColor Green
Write-Host "`nPasos siguientes:" -ForegroundColor Cyan
Write-Host "1. Actualizar imports en los archivos copiados (catalog -> skill)"
Write-Host "2. Crear SkillController extrayendo endpoints de CatalogController"
Write-Host "3. Crear DTOs: SkillRequest.java y SkillResponse.java"
Write-Host "4. Actualizar imports en módulos: project, dashboard"
Write-Host "5. Eliminar archivos originales del módulo catalog"
Write-Host "6. Compilar y verificar"
