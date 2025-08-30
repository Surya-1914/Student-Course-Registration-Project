package student.service;

import java.util.List;

import student.entity.Course_Details;

public interface CourseService
{
	List<Course_Details> getAllCourses();
    Course_Details getCourseId(Long course_id);
    Course_Details saveCourse(Course_Details courseDetails);
    void deleteCourseId(Long course_id);
    Course_Details updateCourseDetails(Course_Details course_Details);

}

