-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- Insertar 3 pacientes
insert into pacientes (id, nombre, edad, cedula) values (1, 'Juan Perez', '30', '0102030405');
insert into pacientes (id, nombre, edad, cedula) values (2, 'Maria Lopez', '25', '0203040506');
insert into pacientes (id, nombre, edad, cedula) values (3, 'Carlos Ruiz', '40', '0304050607');

-- Insertar 3 doctores
insert into doctores (id, nombre, especialidad) values (1, 'Dr. Gomez', 'Cardiología');
insert into doctores (id, nombre, especialidad) values (2, 'Dra. Torres', 'Pediatría');
insert into doctores (id, nombre, especialidad) values (3, 'Dr. Salazar', 'Dermatología');

-- Insertar 1 cita (doctor_id=1, paciente_id=1)
insert into citas (id, fecha, hora, doctor_id, paciente_id) values (1, '2026-02-12', '09:00', 1, 1);

-- Más citas
insert into citas (id, fecha, hora, doctor_id, paciente_id) values (2, '2026-02-13', '10:30', 2, 2);
insert into citas (id, fecha, hora, doctor_id, paciente_id) values (3, '2026-02-14', '11:15', 3, 3);
insert into citas (id, fecha, hora, doctor_id, paciente_id) values (4, '2026-02-15', '08:45', 1, 2);
insert into citas (id, fecha, hora, doctor_id, paciente_id) values (5, '2026-02-16', '12:00', 2, 1);