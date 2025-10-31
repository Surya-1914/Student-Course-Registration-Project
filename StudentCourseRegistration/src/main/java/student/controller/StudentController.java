package student.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import student.entity.StudentEntity;
import student.service.AdminService;
import student.service.CourseService;
import student.service.StudentService;
import student.service.implementation.AdminService_Implementation;

@Controller
@RequestMapping("/students")
public class StudentController 
{

    private final AdminService adminService;
	
	private final StudentService studentService;
	
	private final CourseService courseService;

	
	
	public StudentController(StudentService studentService, CourseService courseService, AdminService adminService)
	{
		super();
		this.studentService = studentService;
		this.courseService = courseService;
		this.adminService = adminService;
	}

	@GetMapping
	private String welcome() 
	{
		return "Welcome";

	}

	@GetMapping("/login")
	private String studentLoginForm(Model model) 
	{
		model.addAttribute("student", new StudentEntity());
		return "StudentLoginForm";
	}
    
    @PostMapping("/login")
    private String verifyLoginDetails(Model model,@ModelAttribute("student")StudentEntity studentEntity,HttpSession session)
    {
    	final String ATTEMPTS_KEY = "LOGIN_ATTEMPTS_COUNT";
        final int MAX_ATTEMPTS = 3;
        
        Integer attempts = (Integer) session.getAttribute(ATTEMPTS_KEY);
        if (attempts == null) 
        {
            attempts = 0;
        }
        
    	if (attempts>=MAX_ATTEMPTS)
    	{
    		System.err.println("Session for user " + studentEntity.getStudentemailid() + " is temporarily locked.");
            model.addAttribute("loginError", "Account temporarily locked after " + MAX_ATTEMPTS + " failed attempts.");
            return "StudentLoginForm";
		}
    	
    	StudentEntity foundStudent = studentService.getByStudentEmailidAndStudentPassword(
    	        studentEntity.getStudentemailid(), studentEntity.getPassword()
    	    );

    	if (foundStudent != null) 
    	{
    	   session.setAttribute("LOGGED_IN_STUDENT", foundStudent);
    	   session.removeAttribute(ATTEMPTS_KEY);
    	   model.addAttribute("courses", courseService.getAllCourses());
    	   return "redirect:/students/operations";
    	} 
    	else
    	{
    	   attempts++;
    	   session.setAttribute(ATTEMPTS_KEY, attempts);

    	   String errorMessage = "Invalid credentials. You have " + (MAX_ATTEMPTS - attempts) + " attempts remaining.";
    	        
    	   if (attempts >= MAX_ATTEMPTS)
    	   {
	    	   errorMessage = "Invalid credentials. Account is now locked.";
	    	   System.err.println("User " + studentEntity.getStudentemailid() + " failed their final attempt.");
    	   }
    	        
	    	 model.addAttribute("loginError", errorMessage);
	    	 return "StudentLoginForm";
    	}
    }
    
    @GetMapping("/forgot-password")
    private String forgotPassword(Model model) 
    {
    	model.addAttribute("student", new StudentEntity());
		return "ForgotPassword";
	}
    
//    @PostMapping("/forgot-password")
//    private String forgotPassword_operation(@ModelAttribute("student")StudentEntity studentEntity,Model model,HttpSession session,@RequestParam(value="userotp",required = false)String userotp) 
//    {
//		StudentEntity foundEmailid=studentService.getByStudentEmailid(studentEntity.getStudentemailid());
//		if (foundEmailid !=null)
//		{
//			System.err.println("Emailid Found");
//			Random random=new Random();
//			int otp=random.nextInt(9000)+1000;
//			System.err.println("random otp : "+otp);
//			if (otp>=1000 && otp<=9999)
//			{
//				System.err.println("OTP : "+otp);
//				Integer storedOTP=(Integer) session.getAttribute("GENERATED_OTP");
//			}
//			
//			
//			
//			if (otp==otp)
//			{
//				System.err.println("Modify the password");
//			}
//			else
//			{
//				System.err.println("Invalid OTP");
//			}
//			return "ForgotPassword";
//		}
//		else
//		{
//			System.err.println("Emailid Not Found");
//			model.addAttribute("errorMessage", "Email not exist");
//			return "ForgotPassword";
//		}
//	}
    
    
    @PostMapping("/forgot-password")
    private String forgotPasswordOperation(
            @ModelAttribute("student") StudentEntity studentEntity,
            Model model,
            HttpSession session,
            @RequestParam(value = "userOtp", required = false) String userOtp)
    {

        // Step 1: Check if the email exists in the database
        StudentEntity foundEmail = studentService.getByStudentEmailid(studentEntity.getStudentemailid());

        if (foundEmail == null) {
            // ❌ Email not found
            model.addAttribute("errorMessage", "Email ID not found.");
            model.addAttribute("otpStage", false);
            return "ForgotPassword";
        }

        // ✅ Email found
        if (userOtp == null || userOtp.isEmpty()) {
            // Step 2: Generate OTP when user only provides email
            Random random = new Random();
            int otp = random.nextInt(9000) + 1000; // 4-digit OTP
            System.out.println("📩 Generated OTP: " + otp);

            // Save OTP in session
            session.setAttribute("GENERATED_OTP", otp);
            session.setAttribute("FORGOT_EMAIL", foundEmail.getStudentemailid());

            // (Optional) send OTP to email here

            // Send success message to page
            model.addAttribute("infoMessage", "OTP has been sent to your registered email.");
            model.addAttribute("otpStage", true); // Switch to OTP input mode
            model.addAttribute("student", studentEntity);
            return "ForgotPassword";
        }

        // Step 3: If OTP entered, verify it
        Integer storedOtp = (Integer) session.getAttribute("GENERATED_OTP");
        if (storedOtp != null && userOtp.equals(String.valueOf(storedOtp)))
        {
            System.out.println("✅ OTP verified successfully!");
            session.setAttribute("OTP_VERIFIED", true);
            model.addAttribute("email", foundEmail.getStudentemailid());
            return "ResetPassword"; // Move to reset password page
        } 
        else 
        {
            // ❌ Invalid OTP
            System.err.println("❌ Invalid OTP entered!");
            model.addAttribute("errorMessage", "Invalid OTP. Please try again.");
            model.addAttribute("otpStage", true); // Stay on OTP entry stage
            model.addAttribute("student", studentEntity);
            return "ForgotPassword";
        }
    }

    
    
    
    @GetMapping("/operations")
    private String studentOperations(Model model,HttpSession session)
    {
    	StudentEntity studentEntity=(StudentEntity) session.getAttribute("LOGGED_IN_STUDENT");
    	System.err.println("object op : "+(studentEntity != null ? studentEntity.getStudentname() : "null"));
    	
    	if (studentEntity !=null) 
    	{
    		System.err.println("enter");
    		model.addAttribute("student", studentEntity);
    		System.err.println("Model.containsAttribute('courses') : "+!model.containsAttribute("courses"));
    		if (!model.containsAttribute("courses")) 
    		{
    			model.addAttribute("courses", courseService.getAllCourses());
    		}
    		return "StudentOperation";
		}
		else
		{
			System.err.println("no");
			model.addAttribute("loginError", "Invalid credentials");
			return "StudentLoginForm";
		}
	}
    
    @PostMapping("/updatePassword/{id}")
    private String updatePassword(@PathVariable("id") Long studentId,@RequestParam("password") String newPassword, Model model, HttpSession session)
    {
		StudentEntity currentStudent= (StudentEntity) session.getAttribute("LOGGED_IN_STUDENT");
		
		System.err.println("Current Student : "+currentStudent);
		System.err.println("Current Student : "+currentStudent.toString());
		System.err.println("currentStudent.getStudentid() : "+currentStudent.getStudentid());
		System.err.println("studentId : "+studentId);
		if (currentStudent == null || !currentStudent.getStudentid().equals(studentId))
		{
			model.addAttribute("loginError", "Invalid Credentials");
	        return "StudentLoginForm";
		}
		
		try
		{
			studentService.updateStudentPassword(studentId,newPassword);
			StudentEntity updatedStudent = studentService.getStudentId(studentId);
			session.setAttribute("LOGGED_IN_STUDENT", updatedStudent);
			model.addAttribute("student", updatedStudent);
			model.addAttribute("passwordSuccess", "✅ Password updated successfully!");
			
		} 
		catch (Exception e) 
		{
			model.addAttribute("passwordError", "❌ Error updating password: " + e.getMessage());
	       
	        model.addAttribute("student", currentStudent); 
		}

		model.addAttribute("courses", courseService.getAllCourses());
		return "StudentOperation";
	}
    
    
    //for forgot password
    @PostMapping("/modifyPassword/{email}")
    private String modifyPassword(@PathVariable("email") String studentEmailid,@RequestParam("password") String newPassword, Model model, HttpSession session)
    {
    	Boolean verified = (Boolean) session.getAttribute("OTP_VERIFIED");
        String forgotEmail = (String) session.getAttribute("FORGOT_EMAIL");
        
		if (verified == null || !verified || forgotEmail == null || !forgotEmail.equals(studentEmailid))
		{
			model.addAttribute("loginError", "Password reset process expired or invalid.");
	        return "StudentLoginForm";
		}
		
		try
		{
			studentService.modifyStudentPassword(studentEmailid, newPassword);
			session.removeAttribute("GENERATED_OTP");
			session.removeAttribute("FORGOT_EMAIL");
			session.removeAttribute("OTP_VERIFIED");
			model.addAttribute("passwordSuccess", "✅ Password reset successfully! Please log in with your new password.");
			
		} 
		catch (Exception e) 
		{
			System.err.println("Error during password modification: " + e.getMessage());
			model.addAttribute("passwordError", "❌ Error resetting password. Please try again.");
	        StudentEntity studentForModel=studentService.getByStudentEmailid(studentEmailid);
	        model.addAttribute("student", studentForModel); 
	        return "ResetPassword";
		}

		model.addAttribute("courses", courseService.getAllCourses());
		//return "StudentLoginForm";
		return "redirect:/students/login";
	}
    
    
    
    
    
    
    @GetMapping("/start")
    private String startCourse() 
    {
		return "StartCourse";

	}
//    private String logout(HttpSession session, SessionStatus status) 
//    {
//		session.removeAttribute("LOGGED_IN_STUDENT");
//		session.invalidate();
//		return "redirect:/students/login";
//
//	}
    
    
    
	@PostMapping("/enrollStudent/{id}")
	public String enrollStudentOperations(@PathVariable("id") Long studentId,@RequestParam("courseid") Long courseId) 
	{
		studentService.enrollStudent(studentId, courseId);
		return "redirect:/students/operations";
		//		return "redirect:/students/operations";
	}
	
	
	// At the time of Student want to unenroll
	@PostMapping("/unenroll")
	private String unenrollCourse(@RequestParam("courseid") Long courseId, HttpSession session)
	{
		StudentEntity studentEntity=(StudentEntity) session.getAttribute("LOGGED_IN_STUDENT");
		System.err.println("Student : "+studentEntity );
		
		if (studentEntity !=null)
		{
			System.err.println("Student: " + studentEntity.getStudentname());
			studentService.unenrollStudentFromCourse(studentEntity.getStudentid(),courseId);
			
			StudentEntity refreshedStudent=studentService.getStudentId(studentEntity.getStudentid());
			session.setAttribute("LOGGED_IN_STUDENT", refreshedStudent);
			
		}
		return "redirect:/students/operations";
	}
	
}


