package com.michaelcgood.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.michaelcgood.dao.StudentRepository;
import com.michaelcgood.model.Student;

@Controller
public class StudentController {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	public String finalString = null;
	@Autowired
	private StudentRepository studentRepository;
	@PostMapping(value="/")
	public String addAStudent(@ModelAttribute @Valid Student newStudent, BindingResult bindingResult, Model model){
		if (bindingResult.hasErrors()) {
			System.out.println("BINDING RESuLT ERROR");
			return "index";
		} else {
			model.addAttribute("student", newStudent);

			if (newStudent.getName() != null) {
				try {
					// check for comments and if not present set to 'none'
					String comments = checkNullString(newStudent.getComments());
					if (comments != "None") {
						System.out.println("nothing changes");
					} else {
						newStudent.setComments(comments);
					}
				} catch (Exception e) {

					System.out.println(e);

				}
				studentRepository.save(newStudent);
				System.out.println("new student added: " + newStudent);
			}

			return "thanks";
		}
	}
	
	@GetMapping(value="thanks")
	public String thankYou(@ModelAttribute Student newStudent, Model model){
		model.addAttribute("student",newStudent);
		
		return "thanks";
	}
	
	@GetMapping(value="/")
	public String viewTheForm(Model model){
		Student newStudent = new Student();
		model.addAttribute("student",newStudent);
		return "index";
	}
	
	public String checkNullString(String str){
		String endString = null;
		if(str == null || str.isEmpty()){
			System.out.println("yes it is empty");
			str = null;
			Optional<String> opt = Optional.ofNullable(str);
			endString = opt.orElse("None");
			System.out.println("endString : " + endString);
		}
		else{
			; //do nothing
		}
		
		
		return endString;
		
	}

}
