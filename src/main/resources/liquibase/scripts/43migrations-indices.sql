-- liquibase formatted sql

-- changeset s.borisov:1
CREATE INDEX idx_student_name ON student (name);
-- changeset s.borisov:2
CREATE INDEX idx_faculty_name_color ON faculty (name, color);