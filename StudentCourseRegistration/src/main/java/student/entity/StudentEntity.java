package student.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name="student_details")
public class StudentEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="student_id")
	private Long studentid;
	
	@NotBlank
	@Size(min = 3,max = 50,message = "Student name must be between 3 to 50 characters")
	@Column(name="student_name", nullable = false)
	private String studentname;
	
	@NotBlank
	@Email(message = "invalid Message format")
	@Column(name = "student_emailid", nullable = false, unique = true)
	private String studentemailid;
	
	public StudentEntity(String studentname, String studentemailid) 
	{
		super();
		this.studentname = studentname;
		this.studentemailid = studentemailid;
		
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="student_course",joinColumns = @JoinColumn(name="student_id"), inverseJoinColumns = @JoinColumn(name="course_id"))
	private Set<Course_Details> courses;

}


