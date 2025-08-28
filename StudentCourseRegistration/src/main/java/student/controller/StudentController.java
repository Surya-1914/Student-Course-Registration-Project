package student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import student.entity.StudentEntity;
import student.service.CourseService;
import student.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController 
{
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public String listOfStudents(Model model)
	{
		model.addAttribute("students", studentService.getAllStudents());
		return "Students";
	}
	
	@GetMapping("/add")
	public String addStudentForm(Model model) 
	{
		model.addAttribute("student", new StudentEntity());
		return "AddStudentsForm";
	}
	
	@PostMapping("/save")
	public String saveStudent(@Valid @ModelAttribute ("student") StudentEntity studentEntity, BindingResult result,Model model)
	{
		if (result.hasErrors())
		{
			return "AddStudentsForm";
		}
		try 
		{
			studentService.saveStudent(studentEntity);
		} 
		catch (IllegalArgumentException e)
		{
			model.addAttribute("duplicateError", e.getMessage());
			return "AddStudentsForm";
		}
		
		return "redirect:/students";
	}
	
	@GetMapping("/enroll/{id}")
	public String enrollForm(@PathVariable("id") Long id,Model model)
	{
		model.addAttribute("student", studentService.getStudentId(id));
		model.addAttribute("courses", courseService.getAllCourses());
		return "EnrollStudents";
	}
	
	@PostMapping("/enroll/{id}")
	public String enrollStudent(@PathVariable("id") Long studentId,@RequestParam("courseId") Long courseId) 
	{
		studentService.enrollStudent(studentId, courseId);
		return "redirect:/students";
	}
}


