<<<<<<< HEAD
 # 📚 DOCUMENTACIÓN DEL BACKEND - AGROPACAYALES

**Versión:** 1.0
**Fecha:** Mayo 2026
**Estado:** Desarrollo/Testing

---

## 1️⃣ ENDPOINTS Y RUTAS

### Base URL
```
http://localhost:5001/api
```

### Documentación Swagger (OpenAPI)
```
http://localhost:5001/swagger-ui.html
```

### Endpoints Principales

| Recurso | GET | POST | PUT | PATCH |
|---------|-----|------|-----|-------|
| `/usuarios` | Listar | Crear | Editar | Eliminar/Restaurar |
| `/parcelas` | Listar | Crear | Editar | Eliminar/Restaurar |
| `/insumos` | Listar | Crear | Editar | Eliminar/Restaurar |
| `/cultivos` | Listar | Crear | Editar | Eliminar/Restaurar |

### URLs Detalladas

#### USUARIOS
```
GET    /api/usuarios                    # Listar todos
GET    /api/usuarios/{id}              # Obtener por ID
GET    /api/usuarios/estado/{estado}   # Filtrar por estado (true/false)
POST   /api/usuarios                    # Crear nuevo
PUT    /api/usuarios/{id}              # Editar
PATCH  /api/usuarios/{id}/eliminar     # Eliminar (soft delete)
PATCH  /api/usuarios/{id}/restaurar    # Restaurar
```

#### PARCELAS
```
GET    /api/parcelas                    # Listar todos
GET    /api/parcelas/{id}              # Obtener por ID
GET    /api/parcelas/estado/{estado}   # Filtrar por estado
POST   /api/parcelas                    # Crear nuevo
PUT    /api/parcelas/{id}              # Editar
PATCH  /api/parcelas/{id}/eliminar     # Eliminar
PATCH  /api/parcelas/{id}/restaurar    # Restaurar
```

#### INSUMOS
```
GET    /api/insumos                   # Listar todos
GET    /api/insumos/{id}             # Obtener por ID
GET    /api/insumos/estado/{estado}  # Filtrar por estado
POST   /api/insumos                   # Crear nuevo
PUT    /api/insumos/{id}             # Editar
PATCH  /api/insumos/{id}/eliminar    # Eliminar
PATCH  /api/insumos/{id}/restaurar   # Restaurar
```

#### CULTIVOS
```
GET    /api/cultivos                    # Listar todos
GET    /api/cultivos/{id}              # Obtener por ID
GET    /api/cultivos/estado/{estado}   # Filtrar por estado
POST   /api/cultivos                    # Crear nuevo
PUT    /api/cultivos/{id}              # Editar
PATCH  /api/cultivos/{id}/eliminar     # Eliminar
PATCH  /api/cultivos/{id}/restaurar    # Restaurar
```

---

## 2️⃣ PATRÓN DE RESPUESTA

### Respuesta Exitosa (HTTP 200, 201)
```json
{
  "idParcela": 1,
  "nombre": "Lote A - Valle Norte",
  "ubicacion": "Sector Norte, Km 5",
  "areaHectareas": 2.5,
  "tipoSuelo": "Franco",
  "responsable": "Juan Pérez",
  "estadoRiego": "Goteo",
  "fechaUltimaSiembra": "2026-03-10",
  "produccionEstimada": "500 kg",
  "cultivoActual": "Maíz",
  "observaciones": "Parcela en buen estado, riego automatizado",
  "enUso": false,
  "estado": true,
  "createdAt": "15/05/2026 13:45:30",
  "updatedAt": null,
  "deletedAt": null,
  "restoredAt": null
}
```

### Respuesta de Listado (HTTP 200)
```json
[
  {
    "idParcela": 1,
    "nombre": "Lote A",
    ...
  },
  {
    "idParcela": 2,
    "nombre": "Lote B",
    ...
  }
]
```

### Respuesta de Error (HTTP 400, 404, 500)
```json
{
  "timestamp": "2026-05-15T13:45:30.123Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Could not write JSON...",
  "path": "/api/parcelas"
}
```

### Códigos HTTP Esperados
- **200 OK** - Operación exitosa (GET, PUT, PATCH)
- **201 Created** - Recurso creado (POST)
- **204 No Content** - Sin contenido (algunos DELETE)
- **400 Bad Request** - Error en los datos enviados
- **404 Not Found** - Recurso no encontrado
- **500 Internal Server Error** - Error del servidor

---

## 3️⃣ AUTENTICACIÓN

### Estado Actual: SIN AUTENTICACIÓN
❌ No hay JWT
❌ No hay Bearer tokens
❌ No hay refresh tokens
✅ Endpoints públicos (sin protección)

### Para Implementar en el Futuro
Si necesitan agregar autenticación JWT:
1. Agregar dependencia `spring-boot-starter-security`
2. Crear clase `JwtTokenProvider`
3. Agregar filtro `JwtAuthenticationFilter`
4. Proteger endpoints con `@PreAuthorize`

---

## 4️⃣ ESTRUCTURA DE DATOS

### USUARIO
```json
{
  "idUsuario": 1,           // Integer, auto-increment
  "nombre": "Juan",          // String, required
  "apellido": "Pérez",       // String, required
  "correo": "user@agro.com", // String, unique, required
  "password": "hash...",     // String, required
  "rol": "OPERADOR",         // String: ADMIN, SUPERVISOR, OPERADOR
  "fechaContratacion": "2026-01-15",  // LocalDate, nullable
  "estado": true,            // Boolean, default true
  "createdAt": "15/05/2026 13:45:30",   // LocalDateTime
  "updatedAt": null,         // LocalDateTime, nullable
  "deletedAt": null,         // LocalDateTime, nullable
  "restoredAt": null         // LocalDateTime, nullable
}
```

### PARCELA
```json
{
  "idParcela": 1,                        // Long, auto-increment
  "nombre": "Lote A",                    // String, unique, required
  "ubicacion": "Sector Norte, Km 5",     // String
  "areaHectareas": 2.5,                  // BigDecimal (precision 10, scale 2)
  "tipoSuelo": "Franco",                 // String
  "responsable": "Juan Pérez",           // String
  "estadoRiego": "Goteo",                // String
  "fechaUltimaSiembra": "2026-03-10",    // LocalDate, nullable
  "produccionEstimada": "500 kg",        // String
  "cultivoActual": "Maíz",               // String
  "observaciones": "En buen estado",     // String (TEXT)
  "enUso": false,                        // Boolean, indica si tiene cultivos activos
  "estado": true,                        // Boolean, default true
  "createdAt": "15/05/2026 13:45:30",    // LocalDateTime
  "updatedAt": null,
  "deletedAt": null,
  "restoredAt": null
}
```

### INSUMO
```json
{
  "idInsumo": 1,                   // Long, auto-increment
  "nombre": "Fertilizante NPK",    // String, required
  "descripcion": "Fertilizante...", // String (TEXT)
  "precio": 45.50,                 // BigDecimal (precision 10, scale 2)
  "stock": 100,                    // Integer, required
  "unidadMedida": "kg",            // String
  "tipoInsumo": "FERTILIZANTE",    // String: FERTILIZANTE, PESTICIDA, HERBICIDA, FUNGICIDA, SEMILLA, OTRO
  "proveedor": "Agroquímicos...",  // String
  "presentacion": "Bolsa 50kg",    // String
  "estado": true,                  // Boolean, default true
  "createdAt": "15/05/2026 13:45:30",
  "updatedAt": null,
  "deletedAt": null,
  "restoredAt": null
}
```

### CULTIVO
```json
{
  "idCultivo": 1,                      // Long, auto-increment
  "parcela": {                         // Objeto Parcela (relación ManyToOne)
    "idParcela": 1,
    "nombre": "Lote A"
  },
  "nombre": "Maíz Híbrido 2026",       // String, required
  "tipoCultivo": "Maíz",               // String, required
  "frecuenciaRiegoDias": 7,            // Integer, required
  "temperaturaIdeal": 25.5,            // Double, required
  "fechaSiembra": "2026-03-10",        // LocalDate
  "requiereSombra": false,             // Boolean
  "observaciones": "Alto rendimiento", // String (TEXT)
  "estado": true,                      // Boolean, default true
  "createdAt": "15/05/2026 13:45:30",
  "updatedAt": null,
  "deletedAt": null,
  "restoredAt": null
}
```

### Tipos de Datos
- **String** - Texto
- **Integer** - Números enteros
- **Long** - Números enteros grandes
- **BigDecimal** - Números decimales con precisión
- **Double** - Números decimales
- **Boolean** - true/false
- **LocalDate** - Fecha (yyyy-MM-dd)
- **LocalDateTime** - Fecha y hora (yyyy-MM-dd HH:mm:ss)

### IDs
- **Tipo:** Numéricos (Integer para Usuario, Long para otros)
- **Generación:** Auto-increment (IDENTITY)
- **Único:** Sí, garantizado por BD

### Relaciones Entre Entidades
```
Parcela ← Cultivo (1 a Many)
  └─ Un cultivo DEBE estar asociado a UNA parcela
  └─ Una parcela PUEDE tener MÚLTIPLES cultivos

Usuario ← Parcela (sin relación BD)
  └─ Se relacionan por el campo "responsable" (String)

Insumo (sin relaciones)
  └─ Entidad independiente
```

---

## 5️⃣ MANEJO DE ERRORES

### Formato de Error
```json
{
  "timestamp": "2026-05-15T13:45:30.123Z",
  "status": 404,
  "error": "Not Found",
  "message": "No message available",
  "path": "/api/parcelas/999"
}
```

### Errores Comunes

#### 400 Bad Request
**Causa:** Datos inválidos o campos faltantes
```json
{
  "timestamp": "2026-05-15T13:45:30.123Z",
  "status": 400,
  "error": "Bad Request",
  "message": "JSON parse error...",
  "path": "/api/parcelas"
}
```

**Soluciones:**
- Verificar JSON válido
- Incluir campos requeridos
- Tipos de datos correctos

#### 404 Not Found
**Causa:** Recurso no existe
```json
{
  "status": 404,
  "message": "No resource found"
}
```

**Soluciones:**
- Verificar ID correcto
- Asegurar que el recurso existe
- Revisar que no fue eliminado

#### 409 Conflict
**Causa:** Violación de unicidad
```json
{
  "status": 409,
  "message": "Duplicate entry"
}
```

**Casos:**
- Nombre de parcela duplicado
- Correo de usuario duplicado

#### 500 Internal Server Error
**Causa:** Error del servidor
**Soluciones:**
- Revisar logs del backend
- Verificar conexión BD
- Contactar al equipo backend

---

## 6️⃣ OTRAS CONSIDERACIONES

### Paginación
❌ **NO implementada actualmente**
- Los listados devuelven TODOS los registros
- TODO: Implementar en futuro con `@PageableDefault`

### CORS (Cross-Origin Resource Sharing)
✅ **Configurado en:** `/src/main/java/Agropacayales/valleGrande/config/CorsConfig.java`

**Orígenes permitidos:**
```
http://localhost:4200  (Angular)
http://localhost:4201  (Angular alternativo)
```

**Métodos permitidos:**
```
GET, POST, PUT, PATCH, DELETE, OPTIONS
```

**Headers permitidos:**
```
* (todos)
```

**Credenciales:** Permitidas (allowCredentials: true)

**Max Age:** 3600 segundos (1 hora)

### Ambiente Actual

#### Desarrollo Local
```
URL Base: http://localhost:5001
BD: SQL Server local (localhost:1433)
Estado: Corriendo
Swagger: http://localhost:5001/swagger-ui.html
```

#### Docker
```
URL Base: http://localhost:5001 (mismo)
BD: Contenedor sql-server
Red: my-network (compartida)
```

---

## 7️⃣ SOFT DELETE (Eliminación Lógica)

### Concepto
No se eliminan registros de la BD, solo se marcan como inactivos.

### Cómo Funciona
```
PATCH /api/parcelas/1/eliminar
└─ Establece estado = false
└─ Registra deleted_at = fecha actual
└─ Registro sigue en BD pero "invisible"
```

### Restaurar
```
PATCH /api/parcelas/1/restaurar
└─ Establece estado = true
└─ Registra restored_at = fecha actual
└─ Registro vuelve a estar "visible"
```

### Auditoría Automática
```json
{
  "createdAt": "15/05/2026 10:30:00",    // Cuando se crea
  "updatedAt": "15/05/2026 14:20:00",    // Última edición
  "deletedAt": "15/05/2026 15:45:00",    // Cuando se elimina
  "restoredAt": "15/05/2026 16:10:00"    // Cuando se restaura
}
```

---

## 8️⃣ PARA EL EQUIPO DE FRONTEND

### Instalación de Dependencias
```bash
npm install axios  # o fetch (nativo)
npm install cors   # si usan Express
```

### Configurar BaseURL
```javascript
// services/api.js
const BASE_URL = 'http://localhost:5001/api';

export const api = {
  usuarios: {
    listar: () => fetch(`${BASE_URL}/usuarios`).then(r => r.json()),
    obtener: (id) => fetch(`${BASE_URL}/usuarios/${id}`).then(r => r.json()),
    crear: (datos) => fetch(`${BASE_URL}/usuarios`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(datos)
    }).then(r => r.json())
  }
  // ... más
};
```

### Ejemplo de Llamada
```javascript
// Crear parcela
const nuevaParcela = {
  nombre: "Lote A",
  ubicacion: "Sector Norte",
  areaHectareas: 2.5,
  tipoSuelo: "Franco",
  responsable: "Juan",
  estadoRiego: "Goteo",
  fechaUltimaSiembra: "2026-03-10",
  produccionEstimada: "500 kg",
  cultivoActual: "Maíz",
  observaciones: "Parcela en buen estado"
};

fetch('http://localhost:5001/api/parcelas', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(nuevaParcela)
})
.then(response => response.json())
.then(data => console.log('Parcela creada:', data))
.catch(error => console.error('Error:', error));
```

### Testing en Swagger
1. Ir a http://localhost:5001/swagger-ui.html
2. Expandir endpoint deseado
3. Click "Try it out"
4. Llenar datos / pegar JSON
5. Click "Execute"

---

## 9️⃣ PRÓXIMOS PASOS RECOMENDADOS

### Backend
- [ ] Implementar autenticación JWT
- [ ] Agregar paginación
- [ ] Crear DTOs para validación
- [ ] Logging centralizado
- [ ] Tests unitarios

### Frontend
- [ ] Conectar con endpoints
- [ ] Crear servicios de HTTP
- [ ] Implementar formularios
- [ ] Gestión de estados (Redux/Context)
- [ ] Validación de datos

---

## 🆘 CONTACTO Y SOPORTE

**Documentación Swagger:** http://localhost:5001/swagger-ui.html
**BD:** SQL Server (agro_pacayales)
**Java Version:** 25
**Spring Boot:** 3.5.14
**Maven:** 3.9

**Para problemas:**
1. Revisar logs de Docker: `docker logs springboot-sqlserver`
2. Verificar BD: `docker logs sql-server`
3. Revisar Swagger documentation
4. Contactar al equipo backend
=======
# ASE251S3_T05-be 
---
>>>>>>> f0e2af4 (initial commit)
