CREATE TABLE IF NOT EXISTS dean
(
    dean_id INT NOT NULL CONSTRAINT dean_pkey PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    NAME VARCHAR(100)  NOT NULL
);

CREATE TABLE IF NOT EXISTS school
(
    school_id INT NOT NULL CONSTRAINT school_pkey PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    NAME TEXT NOT NULL UNIQUE,
    dean_id INT  NOT NULL,
    CONSTRAINT fk_dean FOREIGN KEY(dean_id) REFERENCES dean(dean_id)

);

CREATE TABLE IF NOT EXISTS program
(
    program_ID INT NOT NULL CONSTRAINT program_pkey PRIMARY KEY,
    NAME TEXT NOT NULL,
    credit_hour_fees INT NOT NULL,
    head_dep  VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS student
(
    student_id INT NOT NULL CONSTRAINT student_pkey PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    NAME TEXT NOT NULL,
    school_id INT NOT NULL,
    CONSTRAINT fk_school FOREIGN KEY(school_id) REFERENCES school(school_id),
    program_id INT NOT NULL,
    CONSTRAINT fk_program FOREIGN KEY(program_id) REFERENCES program(program_ID)
);

CREATE TABLE IF NOT EXISTS instructor
(
    instructor_id INT NOT NULL CONSTRAINT instructor_pkey PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    NAME TEXT NOT NULL,
    salary INT NOT NULL,
    school_id INT NOT NULL,
    CONSTRAINT fk_school FOREIGN KEY(school_id) REFERENCES school(school_id)
);

CREATE TABLE IF NOT EXISTS course
(
    course_id INT NOT NULL CONSTRAINT course_pkey PRIMARY KEY,
    NAME TEXT NOT NULL,
    credit_hour INT NOT NULL,
    student_id INT NOT NULL,
    CONSTRAINT fk_student FOREIGN KEY(student_id) REFERENCES student(student_id),
    program_id INT NOT NULL,
    CONSTRAINT fk_program FOREIGN KEY(program_id) REFERENCES program(program_ID)
);

