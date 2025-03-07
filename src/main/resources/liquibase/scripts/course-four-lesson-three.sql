-- liquibase formatted sql

-- changeset dolgorykij:1
CREATE INDEX student_name_index ON student(name);

--changeset dolgorykij:2
CREATE INDEX faculty_search_index ON faculty (name,color);