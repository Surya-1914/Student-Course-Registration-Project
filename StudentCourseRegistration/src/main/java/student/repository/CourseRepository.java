package student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import student.entity.Course_Details;
import java.util.List;


public interface CourseRepository extends JpaRepository<Course_Details, Long>
{
	Course_Details findByCourseid(Long courseid);
	boolean existsByTitle(String title);
}

