package student.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import student.Dao.courseDAO;
import student.entity.Course_Details;
import student.service.CourseService;

@Service
public class CourseServiceImplementaion implements CourseService
{

	@Autowired
	private courseDAO courseDAO;
	
	@Override
	public List<Course_Details> getAllCourses()
	{
		return courseDAO.findAll();
	}

	@Override
	public Course_Details getCourseId(Long course_id)
	{
		return courseDAO.findByCourseId(course_id);
	}

	@Override
	public Course_Details saveCourse(Course_Details courseDetails)
	{
		if (courseDAO.existsByTitle(courseDetails.getTitle()))
		{
			throw new IllegalArgumentException("Course already exists : "+courseDetails.getTitle());
		}
		return courseDAO.save(courseDetails);
	}

	@Override
	public void deleteCourseId(Long course_id) 
	{
		courseDAO.deleteByCourseId(course_id);
	}

	@Override
	public Course_Details updateCourseDetails(Course_Details course_Details) 
	{
		if (courseDAO.existsByTitleAndCourseIdNot(course_Details.getTitle(), course_Details.getCourseid()))
		{
			throw new IllegalArgumentException("Course already exists: "+course_Details.getTitle());	
		}
		return courseDAO.updateCourseDetails(course_Details);
	}

}


