CREATE DATABASE agro_pacayales;
GO

USE agro_pacayales;
GO

-- Tabla de insumos agrícolas (US4: Inventario de abonos y químicos)
CREATE TABLE insumos (
    id_insumo INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(MAX),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    unidad_medida VARCHAR(20),
    tipo_insumo VARCHAR(50),
    proveedor VARCHAR(100),
    presentacion VARCHAR(100),
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2
);
GO

-- Tabla de parcelas / terrenos de cultivo (US1: Registro de terrenos)
CREATE TABLE parcelas (
    id_parcela INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    ubicacion VARCHAR(200),
    area_hectareas DECIMAL(10,2),
    tipo_suelo VARCHAR(80),
    responsable VARCHAR(100),
    estado_riego VARCHAR(50),
    fecha_ultima_siembra DATE,
    produccion_estimada VARCHAR(100),
    cultivo_actual VARCHAR(100),
    observaciones VARCHAR(MAX),
    en_uso BIT DEFAULT 0,
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2
);
GO

-- Tabla de cultivos (US5: Registro de siembra)
IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'cultivos')
BEGIN
    CREATE TABLE cultivos (
        id_cultivo INT IDENTITY(1,1) PRIMARY KEY,
        id_parcela INT NOT NULL,
        nombre VARCHAR(100) NOT NULL,
        tipo_cultivo VARCHAR(80) NOT NULL,
        frecuencia_riego_dias INT NOT NULL,
        temperatura_ideal DECIMAL(5,2) NOT NULL,
        fecha_siembra DATE,
        requiere_sombra BIT DEFAULT 0,
        observaciones VARCHAR(MAX),
        estado BIT DEFAULT 1,
        created_at DATETIME2,
        updated_at DATETIME2,
        deleted_at DATETIME2,
        restored_at DATETIME2,
        CONSTRAINT FK_cultivos_parcelas FOREIGN KEY (id_parcela) 
            REFERENCES parcelas(id_parcela) 
            ON DELETE NO ACTION -- Cambio aquí de RESTRICT a NO ACTION
    );
END
GO

-- Tabla de usuarios (US2: Registro de trabajadores)
CREATE TABLE usuarios (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255),
    rol VARCHAR(20) DEFAULT 'OPERADOR',
    fecha_contratacion DATE,
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2
);
GO

-- Insertar usuario administrador por defecto
INSERT INTO usuarios (nombre, apellido, correo, password, rol, estado)
VALUES ('Administrador', 'General', 'admin@agropacayales.com', 'SqlPassword2026!', 'ADMIN', 1);
GO

-- Tabla de actividades de cultivo (US6: Actividades y control de cultivos)
IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'actividad_cultivo')
BEGIN
    CREATE TABLE actividad_cultivo (
        id_actividad INT IDENTITY(1,1) PRIMARY KEY,
        id_cultivo INT NOT NULL,
        tipo_actividad VARCHAR(50) NOT NULL, -- E.g. SIEMBRA, RIEGO, COSECHA, FUMIGACION, ABONADO
        descripcion VARCHAR(MAX),
        fecha_actividad DATETIME2 NOT NULL,
        costo_total DECIMAL(10,2) NOT NULL,
        estado BIT DEFAULT 1,
        created_at DATETIME2,
        updated_at DATETIME2,
        deleted_at DATETIME2,
        restored_at DATETIME2,
        CONSTRAINT FK_actividad_cultivos FOREIGN KEY (id_cultivo) 
            REFERENCES cultivos(id_cultivo) 
            ON DELETE NO ACTION
    );
END
GO

-- Tabla de detalle de actividades (Insumos consumidos)
IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'detalle_actividad')
BEGIN
    CREATE TABLE detalle_actividad (
        id_detalle INT IDENTITY(1,1) PRIMARY KEY,
        id_actividad INT NOT NULL,
        id_insumo INT NOT NULL,
        cantidad INT NOT NULL,
        precio_unitario DECIMAL(10,2) NOT NULL,
        subtotal DECIMAL(10,2) NOT NULL,
        CONSTRAINT FK_detalle_actividad_cabecera FOREIGN KEY (id_actividad) 
            REFERENCES actividad_cultivo(id_actividad) 
            ON DELETE CASCADE,
        CONSTRAINT FK_detalle_actividad_insumo FOREIGN KEY (id_insumo) 
            REFERENCES insumos(id_insumo) 
            ON DELETE NO ACTION
    );
END
GO