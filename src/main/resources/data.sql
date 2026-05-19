INSERT INTO autores (nombre)
SELECT 'Gabriel Garcia Marquez'
WHERE NOT EXISTS (
    SELECT 1 FROM autores WHERE nombre = 'Gabriel Garcia Marquez'
);

INSERT INTO autores (nombre)
SELECT 'Isabel Allende'
WHERE NOT EXISTS (
    SELECT 1 FROM autores WHERE nombre = 'Isabel Allende'
);

INSERT INTO editoriales (nombre)
SELECT 'Planeta'
WHERE NOT EXISTS (
    SELECT 1 FROM editoriales WHERE nombre = 'Planeta'
);

INSERT INTO editoriales (nombre)
SELECT 'Alfaguara'
WHERE NOT EXISTS (
    SELECT 1 FROM editoriales WHERE nombre = 'Alfaguara'
);

INSERT INTO usuarios (nombre, email, password, fecha_registro)
SELECT 'Admin', 'admin@biblioteca.com', 'admin123', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE email = 'admin@biblioteca.com'
);
