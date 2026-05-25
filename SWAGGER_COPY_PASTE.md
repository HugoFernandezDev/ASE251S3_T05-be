# SWAGGER - COPIAR Y PEGAR DIRECTAMENTE

## USUARIOS - POST /api/usuarios

### Usuario 1
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan.perez@agropacayales.com",
  "password": "SecurePass123!",
  "rol": "OPERADOR",
  "fechaContratacion": "2026-01-15"
}
```

### Usuario 2
```json
{
  "nombre": "María",
  "apellido": "García",
  "correo": "maria.garcia@agropacayales.com",
  "password": "SecurePass456!",
  "rol": "SUPERVISOR",
  "fechaContratacion": "2025-06-20"
}
```

### Usuario 3
```json
{
  "nombre": "Carlos",
  "apellido": "López",
  "correo": "carlos.lopez@agropacayales.com",
  "password": "SecurePass789!",
  "rol": "OPERADOR",
  "fechaContratacion": null
}
```

---

## PARCELAS - POST /api/parcelas

### Parcela 1
```json
{
  "nombre": "Lote A - Valle Norte",
  "ubicacion": "Sector Norte, Km 5",
  "areaHectareas": 2.5,
  "tipoSuelo": "Franco",
  "responsable": "Juan Pérez",
  "estadoRiego": "Goteo",
  "fechaUltimaSiembra": "2026-03-10",
  "produccionEstimada": "500 kg",
  "cultivoActual": "Maíz",
  "observaciones": "Parcela en buen estado, riego automatizado"
}
```

### Parcela 2
```json
{
  "nombre": "Lote B - Valle Sur",
  "ubicacion": "Sector Sur, Km 12",
  "areaHectareas": 3.0,
  "tipoSuelo": "Arcilloso",
  "responsable": "María García",
  "estadoRiego": "Aspersión",
  "fechaUltimaSiembra": "2026-02-05",
  "produccionEstimada": "750 kg",
  "cultivoActual": "Trigo",
  "observaciones": "Requiere drenaje adicional en época lluviosa"
}
```

### Parcela 3
```json
{
  "nombre": "Lote C - Terraza Alta",
  "ubicacion": "Zona de Terraza, Km 8",
  "areaHectareas": 1.8,
  "tipoSuelo": "Arenoso",
  "responsable": "Carlos López",
  "estadoRiego": "Manual",
  "fechaUltimaSiembra": null,
  "produccionEstimada": "300 kg",
  "cultivoActual": null,
  "observaciones": "Parcela en preparación para próxima siembra"
}
```

---

## INSUMOS - POST /api/insumos

### Insumo 1 - Fertilizante
```json
{
  "nombre": "Fertilizante NPK 20-10-10",
  "descripcion": "Fertilizante balanceado para cultivos",
  "precio": 45.50,
  "stock": 100,
  "unidadMedida": "kg",
  "tipoInsumo": "FERTILIZANTE",
  "proveedor": "Agroquímicos del Valle",
  "presentacion": "Bolsa 50kg"
}
```

### Insumo 2 - Pesticida
```json
{
  "nombre": "Pesticida Insecticida Organico",
  "descripcion": "Control de plagas sin químicos sintéticos",
  "precio": 65.00,
  "stock": 50,
  "unidadMedida": "L",
  "tipoInsumo": "PESTICIDA",
  "proveedor": "BioAgro Solutions",
  "presentacion": "Bidón 20L"
}
```

### Insumo 3 - Herbicida
```json
{
  "nombre": "Herbicida Selectivo",
  "descripcion": "Para control de malezas en maíz",
  "precio": 85.75,
  "stock": 30,
  "unidadMedida": "L",
  "tipoInsumo": "HERBICIDA",
  "proveedor": "ChemiCrop",
  "presentacion": "Frasco 500ml"
}
```

### Insumo 4 - Fungicida
```json
{
  "nombre": "Fungicida Preventivo",
  "descripcion": "Prevención de enfermedades fúngicas",
  "precio": 120.00,
  "stock": 25,
  "unidadMedida": "kg",
  "tipoInsumo": "FUNGICIDA",
  "proveedor": "FitoProtección",
  "presentacion": "Bolsa 25kg"
}
```

### Insumo 5 - Semilla
```json
{
  "nombre": "Semilla Maíz Híbrido",
  "descripcion": "Semilla de maíz de alto rendimiento",
  "precio": 250.00,
  "stock": 10,
  "unidadMedida": "bolsa",
  "tipoInsumo": "SEMILLA",
  "proveedor": "SemillasElite",
  "presentacion": "Bolsa 25kg"
}
```

---

## CULTIVOS - POST /api/cultivos

**IMPORTANTE:** Primero crea las parcelas y obtén sus IDs. Luego reemplaza el `idParcela` en estos ejemplos.

### Cultivo 1 - Maíz (Para Parcela ID 1)
```json
{
  "parcela": {
    "idParcela": 1
  },
  "nombre": "Maíz Híbrido 2026",
  "tipoCultivo": "Maíz",
  "frecuenciaRiegoDias": 7,
  "temperaturaIdeal": 25.5,
  "fechaSiembra": "2026-03-10",
  "requiereSombra": false,
  "observaciones": "Variedad de alto rendimiento, resistente a plagas"
}
```

### Cultivo 2 - Trigo (Para Parcela ID 2)
```json
{
  "parcela": {
    "idParcela": 2
  },
  "nombre": "Trigo Primavera 2026",
  "tipoCultivo": "Trigo",
  "frecuenciaRiegoDias": 10,
  "temperaturaIdeal": 18.0,
  "fechaSiembra": "2026-02-05",
  "requiereSombra": false,
  "observaciones": "Cultivo de ciclo corto, excelente para rotación"
}
```

### Cultivo 3 - Frijol (Para Parcela ID 3)
```json
{
  "parcela": {
    "idParcela": 3
  },
  "nombre": "Frijol Voluble",
  "tipoCultivo": "Frijol",
  "frecuenciaRiegoDias": 5,
  "temperaturaIdeal": 22.0,
  "fechaSiembra": "2026-04-01",
  "requiereSombra": true,
  "observaciones": "Requiere soporte, ideal para terrazas"
}
```

---

## ACTIVIDADES-CULTIVOS - POST /api/actividades-cultivos

**IMPORTANTE:** Primero crea los cultivos e insumos y obtén sus IDs. Luego reemplaza `idCultivo` e `idInsumo`.

### Actividad 1 - Fertilización con dos insumos
```json
{
  "idCultivo": 1,
  "tipoActividad": "Fertilización",
  "descripcion": "Aplicación de NPK y fungicida preventivo",
  "detalles": [
    {
      "idInsumo": 1,
      "cantidad": 2
    },
    {
      "idInsumo": 4,
      "cantidad": 1
    }
  ]
}
```

### Actividad 2 - Control de plagas con un insumo
```json
{
  "idCultivo": 2,
  "tipoActividad": "Control de plagas",
  "descripcion": "Aplicación de insecticida orgánico",
  "detalles": [
    {
      "idInsumo": 2,
      "cantidad": 3
    }
  ]
}
```

### Actividad 3 - Inspección sin insumos
```json
{
  "idCultivo": 3,
  "tipoActividad": "Inspección",
  "descripcion": "Revisión visual de estado del cultivo"
}
```

---

## EDITAR - PUT /api/{recurso}/{id}

### Editar Usuario
```json
{
  "nombre": "Juan",
  "apellido": "Pérez García",
  "correo": "juan.perez@agropacayales.com",
  "password": "NewPassword789!",
  "rol": "SUPERVISOR",
  "fechaContratacion": "2026-01-15"
}
```

### Editar Parcela
```json
{
  "nombre": "Lote A - Valle Norte Modificado",
  "ubicacion": "Sector Norte, Km 5",
  "areaHectareas": 2.8,
  "tipoSuelo": "Franco",
  "responsable": "Juan Pérez",
  "estadoRiego": "Goteo",
  "fechaUltimaSiembra": "2026-03-10",
  "produccionEstimada": "600 kg",
  "cultivoActual": "Maíz",
  "observaciones": "Parcela mejorada"
}
```

### Editar Insumo
```json
{
  "nombre": "Fertilizante NPK 20-10-10",
  "descripcion": "Fertilizante balanceado - Premium",
  "precio": 49.99,
  "stock": 150,
  "unidadMedida": "kg",
  "tipoInsumo": "FERTILIZANTE",
  "proveedor": "Agroquímicos del Valle",
  "presentacion": "Bolsa 50kg"
}
```

### Editar Cultivo
```json
{
  "parcela": {
    "idParcela": 1
  },
  "nombre": "Maíz Híbrido 2026 - Actualizado",
  "tipoCultivo": "Maíz",
  "frecuenciaRiegoDias": 5,
  "temperaturaIdeal": 26.0,
  "fechaSiembra": "2026-03-10",
  "requiereSombra": false,
  "observaciones": "Ajustes realizados en riego"
}
```

---

## ENDPOINTS ÚTILES

| Operación | Endpoint |
|-----------|----------|
| Listar todos | GET /api/{recurso} |
| Obtener por ID | GET /api/{recurso}/{id} |
| Crear | POST /api/{recurso} |
| Editar | PUT /api/{recurso}/{id} |
| Eliminar (soft delete) | PATCH /api/{recurso}/{id}/eliminar |
| Restaurar | PATCH /api/{recurso}/{id}/restaurar |

**Donde {recurso} = usuarios, parcelas, insumos, cultivos**
