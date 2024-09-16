CREATE TABLE IF NOT EXISTS capacidad (
    id SERIAL PRIMARY KEY,               -- Clave primaria autogenerada
    nombre VARCHAR(50) NOT NULL UNIQUE,  -- El nombre no se puede repetir y es obligatorio
    descripcion VARCHAR(90) NOT NULL     -- La descripción es obligatoria
);

CREATE TABLE IF NOT EXISTS capacidad_tecnologia (
    id SERIAL PRIMARY KEY,              -- Clave primaria simple
    capacidad_id INTEGER NOT NULL,
    tecnologia_id INTEGER NOT NULL,
    UNIQUE (capacidad_id, tecnologia_id), -- Índice único para garantizar la unicidad
    FOREIGN KEY (capacidad_id) REFERENCES capacidad(id) ON DELETE CASCADE,
    FOREIGN KEY (tecnologia_id) REFERENCES tecnologia(id) ON DELETE CASCADE
);
