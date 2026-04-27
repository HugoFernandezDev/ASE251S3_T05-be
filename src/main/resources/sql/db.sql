CREATE DATABASE agro_pacayales;
GO

USE agro_pacayales;
GO

-- Tabla de productos agrícolas (US4: Inventario de abonos y químicos)
CREATE TABLE producto (
    id_producto INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(MAX),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    unidad_medida VARCHAR(20),
    fecha_registro DATE,
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
    area FLOAT,
    tipo_cultivo VARCHAR(80),
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
        restored_at DATETIME2
    );
END
GO

-- Tabla de usuarios (US2: Registro de trabajadores)
CREATE TABLE usuarios (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(50),
    password VARCHAR(255),
    rol VARCHAR(20) DEFAULT 'OPERADOR',
    fecha_registro DATETIME DEFAULT GETDATE(),
    estado BIT DEFAULT 1
);
GO

-- Insertar usuario administrador por defecto
INSERT INTO usuarios (nombre_completo, correo, username, password, rol)
VALUES ('Administrador General', 'admin@agropacayales.com', 'admin', 'SqlPassword2026!', 'ADMIN');
GO