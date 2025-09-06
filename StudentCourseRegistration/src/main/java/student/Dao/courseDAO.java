package student.Dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import student.entity.Course_Details;

public interface courseDAO 
{
	Course_Details findByCourseId(Long courseId);
	List<Course_Details> findAll();
	Course_Details save(Course_Details course_Details);
	void deleteByCourseId(Long courseId);
	boolean existsByTitle(String title);
	boolean existsByTitleAndCourseIdNot(String title, Long courseId);
	Course_Details updateCourseDetails(Course_Details course_Details);
}


