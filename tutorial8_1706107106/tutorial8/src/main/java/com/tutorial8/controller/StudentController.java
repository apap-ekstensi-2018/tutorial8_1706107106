package com.tutorial8.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.tutorial8.service.StudentService;
import com.tutorial8.model.StudentModel;

@Controller
public class StudentController {
	@Autowired
    StudentService studentDAO;

    @RequestMapping("/student/add")
    public String add (Model model)
    {
    		model.addAttribute("title","Add Student");
        return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (Model model,
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
        StudentModel student = new StudentModel (npm, name, gpa);
        studentDAO.addStudent (student);
        model.addAttribute("title","Data Berhasil Ditambahkan");
        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
    	System.out.println("masuk"+npm);
        StudentModel student = studentDAO.selectStudent (npm);        
        
        if (student != null) {
        		model.addAttribute("title","View Students");
            model.addAttribute ("student", student);
            return "view";
        } else {
        		model.addAttribute("title","404 Not Found");
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


//    @RequestMapping("/student/view/{npm}")
//    public String viewPath (Model model,
//            @PathVariable(value = "npm") String npm)
//    {
//        StudentModel student = studentDAO.selectStudent (npm);
//
//        if (student != null) {
//        		model.addAttribute("title","View Students");
//            model.addAttribute ("student", student);
//            return "view";
//        } else {
//        		model.addAttribute("title","404 Not Found");
//            model.addAttribute ("npm", npm);
//            return "not-found";
//        }
//    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);
        model.addAttribute("title","View All Students");

        return "viewall";
    }


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
    		if (studentDAO.deleteStudent(npm)) {
    			model.addAttribute("title","Data berhasil dihapus");
    			return "delete";
    		} else {
    			model.addAttribute("title","404 Not Found");
    			model.addAttribute("npm",npm);
    			return "not-found";
    		}
    }
    
    @RequestMapping("/student/update/{npm}")
    public String update (Model model, @PathVariable(value = "npm") String npm) {
    		StudentModel student = studentDAO.selectStudent(npm);
    		if (student != null) {
    			model.addAttribute("title","Update Student");
    			model.addAttribute("student",student);
    			return "form-update";
    		} else {
    			model.addAttribute("title","404 Not Found");
    			model.addAttribute("npm",npm);
    			return "not-found";
    		}
    }
    
    @RequestMapping(value = "/student/update/submit", method = RequestMethod.POST)
    public String updateSubmit (Model model, @ModelAttribute StudentModel student) {
        studentDAO.updateStudent(student);
        model.addAttribute("title","Data berhasil diupdate");
        return "success-update";
    }
}
