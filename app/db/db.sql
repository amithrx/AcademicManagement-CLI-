-- Assumptions
-- In one semester, two instructor will not teach the same course
-- All PC course for student in that semester get enrolled automatically
-- Session will start from the first half i.e first half will contain odd semester and second half of year will contain even semester
-- As student request for enrolling in a course, it get also stored in course_record table of that particular course_code & instructor_id
-- As the session changes, admin will enroll all the courses of student who got 'F' in the previous semester if that course being floated this semester 
-- For checking whether elligible to graduate or not, just count total credits completed and whether completed BTP or not
-- cp301,cp302,cp303 must for graduation
-- minm credit is 145

CREATE TABLE current_sessions(
    academic_year INTEGER NOT NULL,
    semester INTEGER NOT NULL,
    PRIMARY KEY(academic_year,semester)
);

CREATE OR REPLACE FUNCTION deleate_offerings()
  RETURNS TRIGGER 
  LANGUAGE PLPGSQL
  AS
$$
declare 

BEGIN
  EXECUTE 'DELETE FROM course_offerings';
	RETURN OLD;
END;
$$;

CREATE TRIGGER update_sessions_deleate_offerings
AFTER UPDATE ON currernt_sessions
FOR EACH row
  EXECUTE PROCEDURE deleate_offerings();
-- As the academic_year or semester changes, all entries of course_offering should be deleated using the above triggers,
-- then all the course_record table would be deleated using triggers
INSERT INTO current_sessions (academic_year,semester)
VALUES (2023,1);
-- UPDATE currernt_sessions SET semester=2; (run update command from java)

CREATE OR REPLACE FUNCTION deleate_report()
  RETURNS TRIGGER 
  LANGUAGE PLPGSQL
  AS
$$
declare 

BEGIN
  EXECUTE 'DELETE FROM report_validator';
	RETURN OLD;
END;
$$;

CREATE TRIGGER update_sessions_deleate_report
AFTER UPDATE ON currernt_sessions
FOR EACH row
  EXECUTE PROCEDURE deleate_report();

  
CREATE TABLE login_logout(
  email_id VARCHAR(255) NOT NULL,
  status VARCHAR(255) NOT NULL,
  created_at TIMESTAMP default CURRENT_TIMESTAMP
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
      academic_year INTEGER NOT NULL,
      semester INTEGER NOT NULL,
      name VARCHAR(255),
      course_code VARCHAR(255),
      instructor_id VARCHAR(255),
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
-- INSERT INTO users (email_id, name, password, role)
-- VALUES ('abc@iitrpr.ac.in', 'abc', 'abc', 's');
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
    academic_year INTEGER NOT NULL,
    semester INTEGER NOT NULL,
    prerequisites TEXT[] default '{}',
    branch_elligible TEXT[] default '{}',
    minm_semester_elligible INTEGER default 0, 
    -- used for checking for PE but for PC, all student having semester=minm_semester_elligible will get enrolled
    core_elective TEXT[] default '{}',
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

-- INSERT INTO course_catalog(course_code,L,T,P,academic_year,semester,prerequisites)
-- VALUES ('cs305',4,2,3,2023,1,ARRAY ['cs302','cs304','ge109']);
INSERT INTO course_catalog(course_code,L,T,P,academic_year,semester,prerequisites)
VALUES ('cs302',4,2,3,2023,1,ARRAY ['cs302','cs304','ge109']);
INSERT INTO course_catalog(course_code,L,T,P,academic_year,semester,prerequisites)
VALUES ('cs304',4,2,3,2023,1,ARRAY ['cs302','cs304','ge109']);
INSERT INTO course_catalog(course_code,L,T,P,academic_year,semester,prerequisites)
VALUES ('ge109',4,2,3,2023,1,ARRAY ['cs302','cs304','ge109']);
INSERT INTO course_catalog(course_code,L,T,P,academic_year,semester,branch_elligible,minm_semester_elligible,core_elective)
VALUES ('cs305',4,2,3,2023,1,ARRAY ['csb','mcb','chb'],5,ARRAY['PE','PE','PE']);


CREATE TABLE course_offerings(
    instructor_id VARCHAR(255) NOT NULL,
    course_code VARCHAR(255) NOT NULL,
    cgpa_constraints FLOAT NOT NULL,
    PRIMARY KEY(instructor_id,course_code)
);

CREATE OR REPLACE FUNCTION make_course_records()
  RETURNS TRIGGER 
  LANGUAGE PLPGSQL
  AS
$$
declare 
  course_code VARCHAR(255);
  table_name VARCHAR(255);
  faculty_name VARCHAR(255);

BEGIN
  course_code := new.course_code;
  faculty_name := new.instructor_id;
  table_name := CONCAT(course_code,'_',substring(faculty_name,1,POSITION('@' IN faculty_name)-1));
  EXECUTE 'create table if not exists '
    || quote_ident(table_name)
    || ' (
      email_id varchar(255) NOT NULL,
      name VARCHAR(255) NOT NULL,
      grade VARCHAR(255)
    )';

	RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION remove_course_records()
  RETURNS TRIGGER 
  LANGUAGE PLPGSQL
  AS
$$
declare 
  course_code VARCHAR(255);
  table_name VARCHAR(255);
  faculty_name VARCHAR(255);

BEGIN
  course_code := old.course_code;
  faculty_name := old.instructor_id;
  table_name := CONCAT(course_code,'_',substring(faculty_name,1,POSITION('@' IN faculty_name)-1));
  EXECUTE 'DROP TABLE '
    || quote_ident(table_name);

	RETURN OLD;
END;
$$;

CREATE TRIGGER deleate_course_records
  AFTER DELETE
  ON course_offerings
  FOR EACH row
  EXECUTE PROCEDURE remove_course_records();

CREATE TRIGGER create_course_records
  AFTER insert
  ON course_offerings
  FOR EACH row
  EXECUTE PROCEDURE make_course_records();

INSERT INTO course_offerings (instructor_id,course_code,cgpa_constraints)
VALUES ('gunturi@iitrpr.ac.in','cs305',7.5);
INSERT INTO course_offerings (instructor_id,course_code,cgpa_constraints)
VALUES ('sodhi@iitrpr.ac.in','cs301',7.2);
INSERT INTO course_offerings (instructor_id,course_code,cgpa_constraints)
VALUES ('rano@iitrpr.ac.in','cs304',7.2);
-- DELETE FROM course_offerings WHERE course_offerings.instructor_id = 'gunturi@iitrpr.ac.in' 
-- AND course_offerings.course_code = 'cs301';


CREATE TABLE report_validator(
  course_code VARCHAR(255) NOT NULL,
  student_id VARCHAR(255) NOT NULL,
  instructor_id VARCHAR(255) NOT NULL,
  PRIMARY KEY(course_code,instructor_id)
);

INSERT INTO report_validator (course_code,student_id,instructor_id)
VALUES ('cs301','2020csb1109@iitrpr.ac.in','mukesh@iitrpr.ac.in');

-- 100000000,010000000,001000000...
CREATE TABLE config(
  course_catalog_start BOOLEAN default false,
  course_catalog_end BOOLEAN default false,
  course_float_start BOOLEAN default false,
  course_float_end BOOLEAN default false,
  course_register_start BOOLEAN default false,
  course_register_end BOOLEAN default false,
  grade_start BOOLEAN default false,
  grade_end BOOLEAN default false,
  validation_check_end BOOLEAN default false
);

INSERT INTO config (course_catalog_start,course_catalog_end,course_float_start,course_float_end,
course_register_start,course_register_end,grade_start,grade_end,validation_check_end) 
VALUES (false,false,false,false,false,false,false,false,true);

-- run update command from java
-- INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade)
-- VALUES(2023,1,'Amit Kumar','cs305','guturi@iitrpr.ac.in','B');
-- INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade)
-- VALUES(2023,1,'Amit Kumar','cs302','abc@iitrpr.ac.in','A-');
INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade)
VALUES(2023,1,'Amit Kumar','ge109','saini@iitrpr.ac.in','A');
-- INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade)
-- VALUES(2023,1,'Amit Kumar','cs304','rano@iitrpr.ac.in','F');
INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade)
VALUES(2022,1,'Amit Kumar','cs304','rano@iitrpr.ac.in','F');
INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade)
VALUES(2022,1,'Amit Kumar','cs304','rano@iitrpr.ac.in','F');
INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade)
VALUES(2022,1,'Amit Kumar','cs304','rano@iitrpr.ac.in','F');
INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id) 
VALUES(2023,1,'Amit Kumar','cs305','gunturi@iitrpr.ac.in');












