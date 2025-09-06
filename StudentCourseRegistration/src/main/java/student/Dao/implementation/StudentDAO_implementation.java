package student.Dao.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import student.Dao.StudentDao;
import student.entity.StudentEntity;
import student.repository.StudentRepository;

@Repository
public class StudentDAO_implementation implements StudentDao 
{
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public List<StudentEntity> findAll() 
	{
		return studentRepository.findAll();
	}

	@Override
	public StudentEntity findByStudentId(Long student_id)
	{
		return studentRepository.findByStudentid(student_id);
	}

	@Override
	public void deleteByStudentId(Long id)
	{
		studentRepository.deleteById(id);

	}

	@Override
	public StudentEntity save(StudentEntity studentEntity)
	{
		return studentRepository.save(studentEntity);
	}

	@Override
	public boolean existsByEmail(String emailid) 
	{
		return studentRepository.existsByStudentemailid(emailid);
	}

	@Override
	public StudentEntity updateByStudent(StudentEntity studentEntity) 
	{
		return studentRepository.save(studentEntity);
	}

	@Override
	public boolean existsByStudentemailidAndStudentidNot(String email, Long studentid)
	{
		return studentRepository.existsByStudentemailidAndStudentidNot(email, studentid);
	}

}
