package com.tutorial8.service;

import java.util.List;
import com.tutorial8.model.StudentModel;

public interface StudentService {
	StudentModel selectStudent(String npm);
	List<StudentModel> selectAllStudents();
	void addStudent(StudentModel student);
	boolean deleteStudent(String npm);
	void updateStudent(StudentModel student);
}
