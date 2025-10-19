CREATE TABLE IF NOT EXISTS students (
    student_id VARCHAR(20) PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_student_name ON students(student_name);
CREATE INDEX IF NOT EXISTS idx_username ON students(username);
CREATE INDEX IF NOT EXISTS idx_email ON students(email);

CREATE TABLE IF NOT EXISTS teachers (
    teacher_id VARCHAR(20) PRIMARY KEY,
    teacher_name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    department VARCHAR(100),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_teacher_name ON teachers(teacher_name);
CREATE INDEX IF NOT EXISTS idx_t_username ON teachers(username);

CREATE TABLE IF NOT EXISTS subject (
    subject_id VARCHAR(20) PRIMARY KEY,
    subject_name VARCHAR(200) NOT NULL,
    credit INT NOT NULL CHECK (credit BETWEEN 1 AND 10),
    teacher_name VARCHAR(100),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_subject_name ON subject(subject_name);
CREATE INDEX IF NOT EXISTS idx_sub_teacher ON subject(teacher_name);

CREATE TABLE IF NOT EXISTS "group" (
    group_id VARCHAR(20) PRIMARY KEY,
    group_name VARCHAR(100) NOT NULL,
    subject_name VARCHAR(200) NOT NULL,
    teacher_name VARCHAR(100) NOT NULL,
    max_students INT NOT NULL DEFAULT 30 CHECK (max_students BETWEEN 10 AND 100),
    number_of_student INT NOT NULL DEFAULT 0 CHECK (number_of_student >= 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_student_count CHECK (number_of_student <= max_students)
);

CREATE INDEX IF NOT EXISTS idx_group_name ON "group"(group_name);
CREATE INDEX IF NOT EXISTS idx_group_subj ON "group"(subject_name);
CREATE INDEX IF NOT EXISTS idx_group_tchr ON "group"(teacher_name);

CREATE TABLE IF NOT EXISTS student_group (
    id SERIAL PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL,
    group_id VARCHAR(20) NOT NULL,
    enrolled_at TIMESTAMPTZ DEFAULT NOW(),
    CONSTRAINT unique_student_group UNIQUE (student_id, group_id),
    CONSTRAINT fk_sg_student FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    CONSTRAINT fk_sg_group FOREIGN KEY (group_id) REFERENCES "group"(group_id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_student_id ON student_group(student_id);
CREATE INDEX IF NOT EXISTS idx_group_id ON student_group(group_id);

CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
BEGIN
  NEW.updated_at := NOW();
  RETURN NEW;
END $$;

DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'trg_students_updated_at') THEN
    CREATE TRIGGER trg_students_updated_at BEFORE UPDATE ON students
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'trg_teachers_updated_at') THEN
    CREATE TRIGGER trg_teachers_updated_at BEFORE UPDATE ON teachers
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'trg_subject_updated_at') THEN
    CREATE TRIGGER trg_subject_updated_at BEFORE UPDATE ON subject
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'trg_group_updated_at') THEN
    CREATE TRIGGER trg_group_updated_at BEFORE UPDATE ON "group"
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();
  END IF;
END $$;

INSERT INTO teachers (teacher_id, teacher_name, username, password, email, phone, department)
VALUES
('TC000001','Nguyễn Văn An','TC000001','Teacher123','nva@ptit.edu.vn','0912345678','Khoa Công nghệ Thông tin'),
('TC000002','Trần Thị Bình','TC000002','Teacher456','ttb@ptit.edu.vn','0923456789','Khoa Công nghệ Thông tin'),
('TC000003','Lê Văn Cường','TC000003','Teacher789','lvc@ptit.edu.vn','0934567890','Khoa Điện tử Viễn thông'),
('TC000004','Phạm Thị Dung','TC000004','Teacher321','ptd@ptit.edu.vn','0945678901','Khoa Toán Tin'),
('TC000005','Hoàng Văn Em','TC000005','Teacher654','hve@ptit.edu.vn','0956789012','Khoa Công nghệ Thông tin')
ON CONFLICT DO NOTHING;

INSERT INTO students (student_id, student_name, username, password, email, phone) VALUES
('B24DCCN470','Nguyễn Minh Hải','STU000001','Student123','hainm@ptit.edu.vn','0987654321'),
('B24DCCN471','Trần Thanh Hương','STU000002','Student456','huongtt@ptit.edu.vn','0987654322'),
('B24DCCN472','Lê Quang Khải','STU000003','Student789','khailq@ptit.edu.vn','0987654323'),
('B24DCCN473','Phạm Thị Lan','STU000004','Student321','lanpt@ptit.edu.vn','0987654324'),
('B24DCCN474','Hoàng Văn Minh','STU000005','Student654','minhhv@ptit.edu.vn','0987654325'),
('B24DCCN475','Vũ Thị Nga','STU000006','Student987','ngavt@ptit.edu.vn','0987654326'),
('B24DCCN476','Đặng Văn Phong','STU000007','Student147','phongdv@ptit.edu.vn','0987654327'),
('B24DCCN477','Bùi Thị Quỳnh','STU000008','Student258','quynhbt@ptit.edu.vn','0987654328'),
('B24DCCN478','Dương Văn Sơn','STU000009','Student369','sondv@ptit.edu.vn','0987654329'),
('B24DCCN479','Ngô Thị Tâm','STU000010','Student753','tamnt@ptit.edu.vn','0987654330')
ON CONFLICT DO NOTHING;

INSERT INTO subject (subject_id, subject_name, credit, teacher_name) VALUES
('IT3100','Lập trình Hướng đối tượng',3,'Nguyễn Văn An'),
('IT3120','Cơ sở dữ liệu',3,'Trần Thị Bình'),
('IT3150','Cấu trúc dữ liệu và giải thuật',4,'Nguyễn Văn An'),
('IT4010','Mạng máy tính',3,'Lê Văn Cường'),
('IT4020','Hệ điều hành',3,'Phạm Thị Dung'),
('IT4050','Công nghệ Web',3,'Hoàng Văn Em'),
('IT4060','Trí tuệ nhân tạo',3,'Nguyễn Văn An'),
('IT4080','An toàn và bảo mật thông tin',3,'Trần Thị Bình'),
('MAT1101','Toán cao cấp 1',4,'Phạm Thị Dung'),
('MAT1102','Toán cao cấp 2',3,'Phạm Thị Dung')
ON CONFLICT DO NOTHING;

INSERT INTO "group" (group_id, group_name, subject_name, teacher_name, max_students, number_of_student) VALUES
('INT1319','OOP-01','Lập trình Hướng đối tượng','Nguyễn Văn An',40,0),
('INT1320','OOP-02','Lập trình Hướng đối tượng','Nguyễn Văn An',40,0),
('INT1321','DB-01','Cơ sở dữ liệu','Trần Thị Bình',35,0),
('INT1322','DSA-01','Cấu trúc dữ liệu và giải thuật','Nguyễn Văn An',45,0),
('INT1323','NET-01','Mạng máy tính','Lê Văn Cường',35,0),
('INT1324','OS-01','Hệ điều hành','Phạm Thị Dung',40,0),
('INT1325','WEB-01','Công nghệ Web','Hoàng Văn Em',35,0),
('INT1326','AI-01','Trí tuệ nhân tạo','Nguyễn Văn An',30,0),
('INT1327','SEC-01','An toàn và bảo mật thông tin','Trần Thị Bình',30,0),
('INT1328','MATH-01','Toán cao cấp 1','Phạm Thị Dung',50,0)
ON CONFLICT DO NOTHING;

INSERT INTO student_group (student_id, group_id) VALUES
('B24DCCN470','INT1319'),
('B24DCCN471','INT1319'),
('B24DCCN472','INT1319'),
('B24DCCN473','INT1320'),
('B24DCCN474','INT1320'),
('B24DCCN475','INT1321'),
('B24DCCN476','INT1321'),
('B24DCCN477','INT1322'),
('B24DCCN478','INT1323'),
('B24DCCN479','INT1324')
ON CONFLICT DO NOTHING;

UPDATE "group" g
SET number_of_student = sub.cnt
FROM (
    SELECT sg.group_id, COUNT(*)::INT AS cnt
    FROM student_group sg
    GROUP BY sg.group_id
) sub
WHERE g.group_id = sub.group_id;

CREATE OR REPLACE VIEW vw_student_enrollments AS
SELECT s.student_id, s.student_name, s.email, s.phone,
       g.group_id, g.group_name, g.subject_name, g.teacher_name, sg.enrolled_at
FROM students s
JOIN student_group sg ON s.student_id = sg.student_id
JOIN "group" g ON sg.group_id = g.group_id
ORDER BY s.student_id, g.group_id;

CREATE OR REPLACE VIEW vw_group_capacity AS
SELECT g.group_id, g.group_name, g.subject_name, g.teacher_name,
       g.number_of_student, g.max_students,
       (g.max_students - g.number_of_student) AS available_slots,
       ROUND((g.number_of_student::numeric / NULLIF(g.max_students,0)) * 100, 2) AS fill_percentage
FROM "group" g
ORDER BY fill_percentage DESC NULLS LAST;

CREATE OR REPLACE VIEW vw_teacher_workload AS
SELECT t.teacher_id, t.teacher_name, t.department,
       COUNT(DISTINCT g.group_id) AS total_groups,
       COALESCE(SUM(g.number_of_student),0) AS total_students,
       COUNT(DISTINCT sub.subject_id) AS total_subjects
FROM teachers t
LEFT JOIN "group" g ON t.teacher_name = g.teacher_name
LEFT JOIN subject sub ON t.teacher_name = sub.teacher_name
GROUP BY t.teacher_id, t.teacher_name, t.department
ORDER BY total_groups DESC;

CREATE OR REPLACE PROCEDURE sp_enroll_student(
    IN p_student_id VARCHAR(20),
    IN p_group_id VARCHAR(20),
    INOUT p_result TEXT DEFAULT NULL
)
LANGUAGE plpgsql AS $$
DECLARE v_current_count INT; v_max_students INT; v_already INT;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM students WHERE student_id = p_student_id) THEN
        p_result := 'ERROR: Student not found'; RAISE EXCEPTION '%', p_result;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM "group" WHERE group_id = p_group_id) THEN
        p_result := 'ERROR: Group not found'; RAISE EXCEPTION '%', p_result;
    END IF;
    SELECT COUNT(*) INTO v_already FROM student_group WHERE student_id = p_student_id AND group_id = p_group_id;
    IF v_already > 0 THEN
        p_result := 'ERROR: Student already enrolled'; RAISE EXCEPTION '%', p_result;
    END IF;
    SELECT number_of_student, max_students INTO v_current_count, v_max_students FROM "group" WHERE group_id = p_group_id;
    IF v_current_count >= v_max_students THEN
        p_result := 'ERROR: Group is full'; RAISE EXCEPTION '%', p_result;
    END IF;
    INSERT INTO student_group (student_id, group_id) VALUES (p_student_id, p_group_id);
    UPDATE "group" SET number_of_student = number_of_student + 1 WHERE group_id = p_group_id;
    p_result := 'SUCCESS: Student enrolled successfully';
END;
$$;

CREATE OR REPLACE PROCEDURE sp_remove_student_from_group(
    IN p_student_id VARCHAR(20),
    IN p_group_id VARCHAR(20),
    INOUT p_result TEXT DEFAULT NULL
)
LANGUAGE plpgsql AS $$
DECLARE v_exists INT;
BEGIN
    SELECT COUNT(*) INTO v_exists FROM student_group WHERE student_id = p_student_id AND group_id = p_group_id;
    IF v_exists = 0 THEN
        p_result := 'ERROR: Enrollment not found'; RAISE EXCEPTION '%', p_result;
    END IF;
    DELETE FROM student_group WHERE student_id = p_student_id AND group_id = p_group_id;
    UPDATE "group" SET number_of_student = GREATEST(0, number_of_student - 1) WHERE group_id = p_group_id;
    p_result := 'SUCCESS: Student removed from group';
END;
$$;

CREATE OR REPLACE FUNCTION trg_check_group_capacity_before_insert_fn()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
DECLARE v_current INT; v_max INT;
BEGIN
    SELECT number_of_student, max_students INTO v_current, v_max FROM "group" WHERE group_id = NEW.group_id;
    IF v_current >= v_max THEN RAISE EXCEPTION 'Cannot enroll: Group is at maximum capacity'; END IF;
    RETURN NEW;
END $$;

DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'trg_check_group_capacity_before_insert') THEN
    CREATE TRIGGER trg_check_group_capacity_before_insert
    BEFORE INSERT ON student_group
    FOR EACH ROW EXECUTE FUNCTION trg_check_group_capacity_before_insert_fn();
  END IF;
END $$;

SELECT 'Database created successfully!' AS status, COUNT(*) AS total FROM students
UNION ALL SELECT 'Total teachers:', COUNT(*) FROM teachers
UNION ALL SELECT 'Total subjects:', COUNT(*) FROM subject
UNION ALL SELECT 'Total groups:', COUNT(*) FROM "group"
UNION ALL SELECT 'Total enrollments:', COUNT(*) FROM student_group;
