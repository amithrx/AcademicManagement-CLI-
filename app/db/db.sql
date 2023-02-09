CREATE TABLE currernt_sessions(
    academic_year VARCHAR(255) NOT NULL,
    semester INTEGER NOT NULL,
    PRIMARY KEY(academic_year,semester)
);


CREATE TABLE users(
  email_id VARCHAR(255),
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(255) NOT NULL,
  PRIMARY KEY(email_id)
);

CREATE OR REPLACE FUNCTION make_student_records()
  RETURNS TRIGGER 
  LANGUAGE PLPGSQL
  AS
$$
declare 
  table_name VARCHAR(255);
  email_id VARCHAR(255);

BEGIN
  IF new.role = 's' THEN
  email_id := new.email_id;
  table_name := CONCAT('s_',substring(email_id,1,11)); 
  
  EXECUTE 'create table if not exists '
    || quote_ident(table_name)
    || ' (
      academid_year varchar(255),
      semester INTEGER,
      course_code VARCHAR(255),
      grade VARCHAR(255)
    )';
  END IF;

	RETURN NEW;
END;
$$;

CREATE TRIGGER create_student_records
  AFTER insert
  ON users
  FOR EACH row
  EXECUTE PROCEDURE make_student_records();
-- s: student, i: instructor, a: admin
INSERT INTO users (email_id, name, password, role)
VALUES ('2020csb1070@iitrpr.ac.in', 'Amit Kumar', '2020csb1070', 's');
INSERT INTO users (email_id, name, password, role)
VALUES ('2020csb1072@iitrpr.ac.in', 'Ankit Sharma', '2020csb1072', 's');
INSERT INTO users (email_id, name, password, role)
VALUES ('2020csb1098@iitrpr.ac.in', 'Mohit Kumar', '2020csb1098', 's');
INSERT INTO users (email_id, name, password, role)
VALUES ('2020csb1068@iitrpr.ac.in', 'Akshat Toolaj Sinha', '2020csb1068', 's');
INSERT INTO users (email_id, name, password, role)
VALUES ('mukesh@iitrpr.ac.in', 'Mukesh Saini', 'mukesh', 'i');
INSERT INTO users (email_id, name, password, role)
VALUES ('gunturi@iitrpr.ac.in', 'Vishwanath Gunturi', 'gunturi', 'i');
INSERT INTO users (email_id, name, password, role)
VALUES ('sodhi@iitrpr.ac.in', 'Balwinder Sodhi', 'sodhi', 'i');
INSERT INTO users (email_id, name, password, role)
VALUES ('admin@iitrpr.ac.in', 'Admin', 'admin', 'a');


CREATE TABLE course_catalog(
    course_code VARCHAR(255) NOT NULL,
    L INTEGER NOT NULL,
    T INTEGER NOT NULL,
    P NUMERIC(3,2) NOT NULL,
    C NUMERIC(3,2),
    academic_year VARCHAR(255) NOT NULL,
    semester INTEGER NOT NULL,
    prerequisites TEXT[],
    PRIMARY KEY(course_code,academic_year,semester)
);

CREATE OR REPLACE FUNCTION make_ltpc()
  RETURNS TRIGGER 
  LANGUAGE PLPGSQL
  AS
$$
declare 
  C NUMERIC(3,2);

BEGIN
  C := (new.L)+(new.P)/2;
  new.C := C;
	RETURN NEW;
END;
$$;

CREATE TRIGGER create_ltpc
  BEFORE insert
  ON course_catalog
  FOR EACH row
  EXECUTE PROCEDURE make_ltpc();

INSERT INTO course_catalog(course_code,L,T,P,academic_year,semester,prerequisites)
VALUES ('cs305',4,2,3,'2023',1,ARRAY ['cs302','cs304','ge109']);


CREATE TABLE course_offerings(
    instructor_id VARCHAR(255) NOT NULL,
    course_code VARCHAR(255) NOT NULL,
    cgpa_constraints FLOAT NOT NULL,
    PRIMARY KEY(instructor_id,course_code)
);


CREATE TABLE report_validator(
  course_code VARCHAR(255) NOT NULL,
  student_id VARCHAR(255) NOT NULL,
  PRIMARY KEY(course_code,student_id)
);


CREATE TABLE config(
  grade_start_date boolean default false,
  grade_end_date boolean default false,
  validation_end boolean default false
);














