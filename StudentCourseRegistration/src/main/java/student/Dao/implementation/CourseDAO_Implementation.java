package student.Dao.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import student.Dao.courseDAO;
import student.entity.Course_Details;
import student.repository.CourseRepository;

@Repository
public class CourseDAO_Implementation implements courseDAO
{

	@Autowired
	private CourseRepository courseRepository;
	
	@Override
	public Course_Details findByCourseId(Long courseId)
	{
		return courseRepository.findByCourseid(courseId);
	}

	@Override
	public List<Course_Details> findAll()
	{
		return courseRepository.findAll();
	}

	@Override
	public Course_Details save(Course_Details course_Details) 
	{
		return courseRepository.save(course_Details);
	}

	@Override
	public void deleteByCourseId(Long courseId)
	{
		courseRepository.deleteById(courseId);
	}

	@Override
	public boolean existsByTitle(String title) 
	{
		return courseRepository.existsByTitle(title);
	}

}

