ALTER TABLE Student
ADD CONSTRAINT chk_student_age CHECK (age >= 16);
ALTER TABLE Student
ALTER COLUMN name SET NOT NULL;
ALTER TABLE Student
ADD CONSTRAINT unique_student_name unique (name);
ALTER TABLE Faculty
ADD CONSTRAINT unique_faculty_name_color UNIQUE (name, color);
ALTER TABLE Student
alter COLUMN age SET DEFAULT 20;