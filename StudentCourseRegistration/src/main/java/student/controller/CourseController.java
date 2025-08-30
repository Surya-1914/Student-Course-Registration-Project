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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import student.entity.Course_Details;
import student.entity.StudentEntity;
import student.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController
{
	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public String listOfCourses(Model model)
	{
		model.addAttribute("courses", courseService.getAllCourses());
		return "ListOfCourses";
	}
	
	@GetMapping("/add")
	public String addCourseForm(Model model) 
	{
		model.addAttribute("course", new Course_Details());
		return "AddCourseForm";
	}
	
	@PostMapping("/save")
	public String saveCourse(@Valid @ModelAttribute("course") Course_Details course_Details,BindingResult result,Model model,RedirectAttributes redirectAttributes) 
	{
		if (result.hasErrors())
		{
			model.addAttribute("course", course_Details);
			return "AddCourseForm";
		}
		try 
		{
			courseService.saveCourse(course_Details);
		}
		catch (IllegalArgumentException e) 
		{
			model.addAttribute("duplicateError", e.getMessage());
			return "AddCourseForm";
		}
		
		redirectAttributes.addFlashAttribute("sucessMessage", "course added Sucessfully! ");
		return "redirect:/courses";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteByCourseId(@PathVariable("id")Long courseId)
	{
		courseService.deleteCourseId(courseId);
		return "redirect:/courses";
	}
	
	
	@GetMapping("update/{id}")
	public String getCourseUpdateForm(@PathVariable("id") Long id,Model model) 
	{
		model.addAttribute("course", courseService.getCourseId(id));
		return "CourseDetailsUpdateForm";
	}
	
	@PostMapping("update/{id}")
	public String UpdatingCourseDetails(@PathVariable("id") Long id,@ModelAttribute("course") Course_Details course_Details) 
	{
		course_Details.setCourseid(id);
		courseService.updateCourseDetails(course_Details);
		return "redirect:/courses";
	}
}


