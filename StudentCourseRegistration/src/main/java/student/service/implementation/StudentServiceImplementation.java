package student.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import student.Dao.StudentDao;
import student.Dao.courseDAO;
import student.controller.CourseController;
import student.entity.Course_Details;
import student.entity.StudentEntity;
import student.service.AdminService;
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
		StudentEntity studentEntity=studentDao.findByStudentId(studentId);
		if (studentEntity!=null)
		{
			Hibernate.initialize(studentEntity.getCourses());
		}
		return studentEntity;
	}

	@Override
	public StudentEntity saveStudent(StudentEntity studentEntity)
	{
		if (studentDao.existsByEmail(studentEntity.getStudentemailid()))
		{
			throw new IllegalArgumentException("Emailid already exists : "+studentEntity.getStudentemailid());
		}
		if (studentEntity.getPassword()==null || studentEntity.getPassword().isEmpty()) 
		{
			studentEntity.setPassword(studentEntity.passwordGeneratior());
			
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
		StudentEntity existingStudent=studentDao.findByStudentId(studentEntity.getStudentid());
		if (existingStudent ==null)
		{
			throw new IllegalArgumentException("Student not found with id: " + studentEntity.getPassword());
//			throw new ResourceNotFoundException("Student not found with id: " + studentid);
//			throw new IllegalArgumentException("Email already exists: " + studentEntity.getStudentemailid());
		}
		if (studentDao.existsByStudentemailidAndStudentidNot(studentEntity.getStudentemailid(), studentEntity.getStudentid()))
		{
			throw new IllegalArgumentException("Email already exists: " + studentEntity.getStudentemailid());
		}
		if (studentEntity.getPassword()==null || studentEntity.getPassword().isEmpty())
		{
			studentEntity.setPassword(existingStudent.getPassword());
		}
		else
		{	
			System.err.println("No password");
		}
		return studentDao.updateByStudent(studentEntity);
	}
	
	@Override
	public StudentEntity getByStudentEmailidAndStudentPassword(String emailid, String password)
	{
		StudentEntity student=studentDao.getByStudentemailidAndPassword(emailid, password);
		if (student !=null)
		{
			Hibernate.initialize(student.getCourses());
			
		}
		return student;
	}

	@Override
	public void updateStudentPassword(Long studentId, String newPassword)
	{
		StudentEntity studentEntity=studentDao.findByStudentId(studentId);
		System.err.println("Student : "+studentEntity);
		System.err.println("Student : "+studentEntity.toString());
		if (studentEntity == null) 
		{
			 throw new IllegalArgumentException("Student not found with ID: " + studentId);
		}
		studentEntity.setPassword(newPassword);
		studentDao.save(studentEntity);
		
	}

//	@Override
//	public void unenrollStudentFromCourse(Long studentid, Long courseId)
//	{
//		StudentEntity studentEntity = studentDao.findByStudentId(studentid);
//		System.err.println("student : "+studentEntity);
//		System.err.println("student : "+studentEntity.toString());
//		
//		if (studentEntity!=null) 
//		{
//			// 1. Ensure courses collection is initialized
//			Hibernate.initialize(studentEntity.getCourses());
//			
//			// 2. Use an Iterator to safely remove an element while iterating
//			Iterator<Course_Details>iterator=studentEntity.getCourses().iterator();
//			
//			while (iterator.hasNext())
//			{
//				Course_Details course=iterator.next();
//				System.err.println("course.getCourseid() : "+course.getCourseid());
//				System.err.println("Course Id : "+courseId);
//				
//				if (course.getCourseid().equals(courseId)) 
//				{
//					// CRITICAL FIX: Use iterator.remove() for safe modification
//					iterator.remove();
//					// 3. Save the updated student entity (transaction is likely still open)
//					studentDao.save(studentEntity);
//					return; 
//				}
//				
//			}
//			
//		}
//		else
//		{
//			     throw new IllegalArgumentException("Student not found with ID: " + studentid);
//		 }
//		
//	}
	
//	@Override
//	public void unenrollStudentFromCourse(Long studentId,Long courseId)
//	{
//		StudentEntity studentEntity=studentDao.findByStudentId(studentId);
//		System.err.println("student : "+studentEntity);
//		if (studentEntity==null) 
//		{
//			throw new IllegalArgumentException("Student not found with ID: " + studentId);
//		}
//		
//		Course_Details courseToRemove=new Course_Details();
//		
//		courseToRemove.setCourseid(courseId);
//		
//		if (studentEntity.getCourses().remove(courseToRemove)) 
//		{
//			studentDao.save(studentEntity);
//		}
//	}
	
	
	
	@Override
	public void unenrollStudentFromCourse(Long studentId,Long courseId)
	{
		StudentEntity studentEntity=studentDao.findByStudentId(studentId);
		System.err.println("student : "+studentEntity);
		if (studentEntity==null) 
		{
			throw new IllegalArgumentException("Student not found with ID: " + studentId);
		}
		
		Course_Details courseToRemove=courseDAO.findByCourseId(courseId);
		
//		courseToRemove.setCourseid(courseId);
		
		System.err.println("courseToRemove : "+courseToRemove);
		if (courseToRemove==null) 
		{
			throw new IllegalArgumentException("course not found with ID: " + courseToRemove);
		}
		
		boolean removed=studentEntity.getCourses().remove(courseToRemove);
		
		if (removed) 
		{
			studentDao.save(studentEntity);
		}
		else
		{
			System.err.println("Student " + studentId + " was not enrolled in course " + courseId);
		}
	}

	@Override
	public List<StudentEntity> getLockedStudents() 
	{
		return new ArrayList<>();
	}

	@Override
	public void unlockStudent(Long studentid)
	{
		
		
	}

	@Override
	public StudentEntity getByStudentEmailid(String studentemailid)
	{
		return studentDao.getByStudentemailid(studentemailid);
	}

	@Override
	public void modifyStudentPassword(String studentEmailid, String newPassword)
	{
		StudentEntity studentEntity=studentDao.findByStudentEmailid(studentEmailid);
		System.err.println("Student : "+studentEntity);
		System.err.println("Student : "+studentEntity.toString());
		if (studentEntity == null) 
		{
			 throw new IllegalArgumentException("Student not found with Emailid: " + studentEmailid);
		}
		studentEntity.setPassword(newPassword);
		studentDao.save(studentEntity);
		
	}


}

