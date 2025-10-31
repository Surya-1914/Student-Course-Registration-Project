package student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import student.entity.StudentEntity;
import java.util.List;



public interface StudentRepository extends JpaRepository<StudentEntity, Long>
{
	StudentEntity findByStudentid(Long studentid);
	boolean existsByStudentemailid(String studentemailid);
	boolean existsByStudentemailidAndStudentidNot(String email, Long studentid);
	void deleteByStudentid(Long studentid);
	
	StudentEntity findByStudentemailidAndPassword(String studentemailid, String password);
	StudentEntity findByStudentemailid(String studentemailid);
}

