package student.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import student.Dao.StudentDao;
import student.Dao.courseDAO;
import student.entity.Course_Details;
import student.entity.StudentEntity;
import student.service.StudentService;

@Service
public class StudentServiceImplementation implements StudentService
{
	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private courseDAO courseDAO;

	@Override
	public List<StudentEntity> getAllStudents() 
	{
		return studentDao.findAll();
	}

	@Override
	public StudentEntity getStudentId(Long studentId)
	{
		return studentDao.findByStudentId(studentId);
	}

	@Override
	public StudentEntity saveStudent(StudentEntity studentEntity)
	{
		if (studentDao.existsByEmail(studentEntity.getStudentemailid()))
		{
			throw new IllegalArgumentException("Emailid already exists : "+studentEntity.getStudentemailid());
		}
		return studentDao.save(studentEntity);
		
	}

	@Override
	public void deleteStudentId(Long studentId)
	{
		studentDao.deleteByStudentId(studentId);
		
	}

	@Override
	public void enrollStudent(Long studentId, Long courseId)
	{
		StudentEntity studentEntity=studentDao.findByStudentId(studentId);
		Course_Details course_Details=courseDAO.findByCourseId(courseId);
		if (studentEntity !=null && course_Details !=null)
		{
			studentEntity.getCourses().add(course_Details);
			studentDao.save(studentEntity);
			
		}
		
	}

	@Override
	public StudentEntity updateByStudent(StudentEntity studentEntity)
	{
		return studentDao.updateByStudent(studentEntity);
	}

}

