package student.entity;

import java.security.SecureRandom;
import java.util.Random;
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
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor@Setter@Getter
@Table(name="student_details")
@ToString(exclude = "courses")
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
	@Pattern(
		    regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
		    message = "Email must be a valid format like user@example.com"
		)
	@Column(name = "student_emailid", nullable = false, unique = true)
	private String studentemailid;
	
	@Size(min=6, message = "length should not be less than 6")
	@Column(name="password",nullable = false)
	private String password;
	
	@Transient
	private final String characters="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	//private final int stringLength=6;
	
	@Transient
	private final SecureRandom random=new SecureRandom();
	public String passwordGeneratior() 
	{
		StringBuilder stringBuilder=new StringBuilder();
		for (int i = 0; i < 6; i++) 
		{
			int index=random.nextInt(characters.length());
			stringBuilder.append(characters.charAt(index));
		}
		return stringBuilder.toString();
	}

	
	



	public StudentEntity(
			@NotBlank @Size(min = 3, max = 50, message = "Student name must be between 3 to 50 characters") String studentname,
			@NotBlank @Email(message = "invalid Message format") @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email must be a valid format like user@example.com") String studentemailid,
			 Set<Course_Details> courses) {
		super();
		this.studentname = studentname;
		this.studentemailid = studentemailid;
		this.password = passwordGeneratior();
		this.courses = courses;
	}
	







	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="student_course",joinColumns = @JoinColumn(name="student_id"), inverseJoinColumns = @JoinColumn(name="course_id"))
	private Set<Course_Details> courses;


}


