package student.entity;

import java.util.Set;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Table(name="course_details")
public class Course_Details 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private Long courseid;
	
	@NotBlank
	@Size(min=3,max=50, message = "title should be between 3 to 50 characters")
	@Column(name="title", unique = true)
	private String title;
	
	@Size(max=500, message = "Description should not be greater than 500 characters")
	@Column(name="description")
	private String description;
	
	@ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
	private Set<StudentEntity> studentDetails;

	public Course_Details(String title, String description, Set<StudentEntity> studentDetails) 
	{
		super();
		this.title = title;
		this.description = description;
		this.studentDetails = studentDetails;
	}
}

