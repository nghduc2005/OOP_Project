    package app.dao;
    
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.util.ArrayList;
    import java.util.List;
    
    import app.model.Grade;
    
    public class GradeDao {
    
        /**
         * Tạo bảng grades nếu chưa tồn tại - MySQL Compatible
         */
        public static void createGradeTable() {
            String sql = """
                CREATE TABLE IF NOT EXISTS grades (
                    grade_id INT AUTO_INCREMENT PRIMARY KEY,
                    student_id VARCHAR(50) NOT NULL,
                    subject_id VARCHAR(50) NOT NULL,
                    class_id VARCHAR(50),
                    attendance_score DECIMAL(5,2) DEFAULT 0.00,
                    assignment_score DECIMAL(5,2) DEFAULT 0.00,
                    midterm_score DECIMAL(5,2) DEFAULT 0.00,
                    final_score DECIMAL(5,2) DEFAULT 0.00,
                    total_score DECIMAL(5,2) DEFAULT 0.00,
                    letter_grade VARCHAR(5),
                    semester VARCHAR(20),
                    academic_year VARCHAR(20),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                    UNIQUE KEY unique_grade (student_id, subject_id, class_id, semester, academic_year)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
            """;
            
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
                System.out.println("✅ Bảng grades đã được tạo hoặc đã tồn tại.");
            } catch (SQLException e) {
                System.err.println("❌ Lỗi khi tạo bảng grades: " + e.getMessage());
                e.printStackTrace();
            }
        }
    
        /**
         * Thêm điểm mới
         */
        public static boolean createGrade(Grade grade) {
            String sql = """
                INSERT INTO grades (student_id, subject_id, class_id, attendance_score,
                                   assignment_score, midterm_score, final_score, total_score,
                                   letter_grade, semester, academic_year)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
    
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, grade.getStudentId());
                pstmt.setString(2, grade.getSubjectId());
                pstmt.setString(3, grade.getClassId());
                pstmt.setDouble(4, grade.getAttendanceScore());
                pstmt.setDouble(5, grade.getAssignmentScore());
                pstmt.setDouble(6, grade.getMidtermScore());
                pstmt.setDouble(7, grade.getFinalScore());
                pstmt.setDouble(8, grade.getTotalScore());
                pstmt.setString(9, grade.getLetterGrade());
                pstmt.setString(10, grade.getSemester());
                pstmt.setString(11, grade.getAcademicYear());
    
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.err.println("Lỗi khi thêm điểm: " + e.getMessage());
                return false;
            }
        }
    
        /**
         * Cập nhật điểm
         */
        public static boolean updateGrade(Grade grade) {
            String sql = """
                UPDATE grades SET
                    attendance_score = ?,
                    assignment_score = ?,
                    midterm_score = ?,
                    final_score = ?,
                    total_score = ?,
                    letter_grade = ?,
                    updated_at = CURRENT_TIMESTAMP
                WHERE grade_id = ?
            """;
    
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDouble(1, grade.getAttendanceScore());
                pstmt.setDouble(2, grade.getAssignmentScore());
                pstmt.setDouble(3, grade.getMidtermScore());
                pstmt.setDouble(4, grade.getFinalScore());
                pstmt.setDouble(5, grade.getTotalScore());
                pstmt.setString(6, grade.getLetterGrade());
                pstmt.setString(7, grade.getGradeId());
    
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.err.println("Lỗi khi cập nhật điểm: " + e.getMessage());
                return false;
            }
        }
    
        /**
         * Xóa điểm
         */
        public static boolean deleteGrade(String gradeId) {
            String sql = "DELETE FROM grades WHERE grade_id = ?";
    
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, gradeId);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.err.println("Lỗi khi xóa điểm: " + e.getMessage());
                return false;
            }
        }
    
        /**
         * Lấy điểm theo ID
         */
        public static Grade getGradeById(String gradeId) {
            String sql = "SELECT * FROM grades WHERE grade_id = ?";
    
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, gradeId);
                ResultSet rs = pstmt.executeQuery();
    
                if (rs.next()) {
                    return extractGradeFromResultSet(rs);
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy điểm theo ID: " + e.getMessage());
            }
            return null;
        }
    
        /**
         * Lấy tất cả điểm của một sinh viên
         */
        public static List<Grade> getGradesByStudentId(String studentId) {
            String sql = "SELECT * FROM grades WHERE student_id = ? ORDER BY academic_year DESC, semester DESC";
            List<Grade> grades = new ArrayList<>();
    
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, studentId);
                ResultSet rs = pstmt.executeQuery();
    
                while (rs.next()) {
                    grades.add(extractGradeFromResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy điểm của sinh viên: " + e.getMessage());
            }
            return grades;
        }
    
        /**
         * Lấy tất cả điểm trong hệ thống
         */
        public static List<Grade> getAllGrades() {
            String sql = "SELECT * FROM grades ORDER BY academic_year DESC, semester DESC, student_id";
            List<Grade> grades = new ArrayList<>();
    
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
    
                while (rs.next()) {
                    grades.add(extractGradeFromResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy tất cả điểm: " + e.getMessage());
            }
            return grades;
        }
    
        /**
         * Lấy điểm của một lớp
         */
        public static List<Grade> getGradesByClassId(String classId) {
            String sql = "SELECT * FROM grades WHERE class_id = ? ORDER BY student_id";
            List<Grade> grades = new ArrayList<>();
    
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, classId);
                ResultSet rs = pstmt.executeQuery();
    
                while (rs.next()) {
                    grades.add(extractGradeFromResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy điểm của lớp: " + e.getMessage());
            }
            return grades;
        }
    
        /**
         * Lấy điểm theo môn học
         */
        public static List<Grade> getGradesBySubjectId(String subjectId) {
            String sql = "SELECT * FROM grades WHERE subject_id = ? ORDER BY student_id";
            List<Grade> grades = new ArrayList<>();
    
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, subjectId);
                ResultSet rs = pstmt.executeQuery();
    
                while (rs.next()) {
                    grades.add(extractGradeFromResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy điểm theo môn học: " + e.getMessage());
            }
            return grades;
        }
    
        /**
         * Lấy điểm của sinh viên theo lớp và môn học
         */
        public static Grade getGradeByStudentAndClass(String studentId, String classId, String subjectId) {
            String sql = "SELECT * FROM grades WHERE student_id = ? AND class_id = ? AND subject_id = ?";
    
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, studentId);
                pstmt.setString(2, classId);
                pstmt.setString(3, subjectId);
                ResultSet rs = pstmt.executeQuery();
    
                if (rs.next()) {
                    return extractGradeFromResultSet(rs);
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy điểm: " + e.getMessage());
            }
            return null;
        }
    
        /**
         * Tìm kiếm điểm
         */
        public static List<Grade> searchGrades(String searchTerm) {
            String sql = """
                SELECT * FROM grades
                WHERE student_id LIKE ? OR subject_id LIKE ? OR class_id LIKE ?
                ORDER BY academic_year DESC, semester DESC
            """;
            List<Grade> grades = new ArrayList<>();
    
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                String searchPattern = "%" + searchTerm + "%";
                pstmt.setString(1, searchPattern);
                pstmt.setString(2, searchPattern);
                pstmt.setString(3, searchPattern);
                ResultSet rs = pstmt.executeQuery();
    
                while (rs.next()) {
                    grades.add(extractGradeFromResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi tìm kiếm điểm: " + e.getMessage());
            }
            return grades;
        }
    
        /**
         * Extract Grade object from ResultSet
         */
        private static Grade extractGradeFromResultSet(ResultSet rs) throws SQLException {
            Grade grade = new Grade();
            grade.setGradeId(String.valueOf(rs.getInt("grade_id")));
            grade.setStudentId(rs.getString("student_id"));
            grade.setSubjectId(rs.getString("subject_id"));
            grade.setClassId(rs.getString("class_id"));
            grade.setAttendanceScore(rs.getDouble("attendance_score"));
            grade.setAssignmentScore(rs.getDouble("assignment_score"));
            grade.setMidtermScore(rs.getDouble("midterm_score"));
            grade.setFinalScore(rs.getDouble("final_score"));
            grade.setSemester(rs.getString("semester"));
            grade.setAcademicYear(rs.getString("academic_year"));
            return grade;
        }
    }
