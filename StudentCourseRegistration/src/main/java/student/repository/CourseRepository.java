package student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import student.entity.Course_Details;


public interface CourseRepository extends JpaRepository<Course_Details, Long>
{
	Course_Details findByCourseid(Long courseid);
	boolean existsByTitle(String title);
	@Transactional
	void deleteByCourseid(Long courseid);
}

