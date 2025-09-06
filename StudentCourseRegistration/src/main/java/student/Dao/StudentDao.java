package student.Dao;

import java.util.List;

import student.entity.StudentEntity;

public interface StudentDao 
{
	List<StudentEntity> findAll();
	StudentEntity findByStudentId(Long student_id);
	void deleteByStudentId(Long id);
	StudentEntity save(StudentEntity studentEntity);
	boolean existsByEmail(String emailid);
	boolean existsByStudentemailidAndStudentidNot(String email, Long studentid);
	StudentEntity updateByStudent(StudentEntity studentEntity);
}

