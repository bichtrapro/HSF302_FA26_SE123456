package com.hsf302.chapter6.controller;

import com.hsf302.chapter6.entity.Student;
import com.hsf302.chapter6.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/students")
public class StudentController {
    private static final String FORM_VIEW = "students/form";
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ModelAttribute("majors")
    public List<String> majors() {
        return studentService.getMajors();
    }

    //Read all
    /*
    @GetMapping
    public String listStudent(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students/list";
    }
    */
    //Get and search
    @GetMapping
    public String listStudent(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Student> students;
        if (keyword != null && !keyword.isEmpty()) {
            students = studentService.searchStudents(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            students = studentService.findAll();
        }
        model.addAttribute("students", students);
        return "students/list";
    }

    //Read one
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        // Implementation for displaying student details
        return studentService.findById(id).map(student -> {
            model.addAttribute("student", student);
            return "students/detail";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", "Student not found" + id);
            return "redirect:/students";
        });
    }
    //Create
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return formView(model, false);
    }

    @PostMapping("/create")
    public String createStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
    //1. Kiem tra email trùng
        if (!bindingResult.hasFieldErrors("email") && studentService.isEmailTaken(student.getEmail(), null)) {
            bindingResult.rejectValue("email", "duplicate", "Email đã tồn tại");
        }
        //2. Nếu có lỗi validate → trả về form (khong Redirect de giữ dữ liêu va loi)
        if (bindingResult.hasErrors()) {
            return formView(model, false);
        }
        // 3. Lưu DB — vẫn bắt lỗi UNIQUE phòng trường hợp 2 người submit cùng lúc
        try {
            Student saved = studentService.create(student);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm mới thành công");
            return "redirect:/students/" + saved.getId();
        } catch (DataIntegrityViolationException e) {
            bindingResult.rejectValue("email", "duplicate", "Email đã tồn tại (bị lỗi UNIQUE)");
            return formView(model, false);
        }
    }
    //Update
    @GetMapping("{id}/edit")
    public  String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        return studentService.findById(id).map(student -> {
            model.addAttribute("student", student);
            return formView(model, true);
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", "Student not found: " + id);
            return "redirect:/students";
        });
    }
    @PostMapping("{id}/edit")
    public String updateStudent(@PathVariable("id") Long id, @Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        student.setId(id); //form không gởi id
        if (!bindingResult.hasFieldErrors("email") && studentService.isEmailTaken(student.getEmail(), id)) {
            bindingResult.rejectValue("email", "duplicate", "Email đã tồn tại");
        }
        if (bindingResult.hasErrors()) {
            return formView(model, true);
        }
        try {
            if(studentService.update(id, student)) {
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công");

            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Student not found: " + id);
               // return "redirect:/students";
            }
        } catch (DataIntegrityViolationException e) {
            bindingResult.rejectValue("email", "duplicate", "Email đã tồn tại (bị lỗi UNIQUE)");
            return formView(model, true);
        }
        return "redirect:/students/" + id;
    }

    //Delete
    @PostMapping("{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (studentService.delete(id)) {
            redirectAttributes.addFlashAttribute("successMessage", "Xóa sinh viên thành công");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Student not found: " + id);
        }
        return "redirect:/students";
    }
    // ==================== Helper ====================

    private String formView(Model model, boolean isEdit) {
        model.addAttribute("isEdit", isEdit);
        model.addAttribute("pageTitle", isEdit ? "Cập nhật sinh viên" : "Thêm sinh viên mới");
        return FORM_VIEW;
    }
}
