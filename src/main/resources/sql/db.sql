CREATE DATABASE agro_pacayales;
GO

USE agro_pacayales;
GO

CREATE TABLE producto (
    id_producto INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(MAX),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    unidad_medida VARCHAR(20),
    fecha_registro DATE,
    estado BIT DEFAULT 1
);

select * from producto;