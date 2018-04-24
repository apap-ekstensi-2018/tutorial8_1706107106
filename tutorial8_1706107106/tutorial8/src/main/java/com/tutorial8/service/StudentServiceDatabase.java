package com.tutorial8.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tutorial8.model.StudentModel;
import com.tutorial8.dao.StudentMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceDatabase implements StudentService {
	@Autowired
	private StudentMapper studentMapper;
	
	@Override
	public StudentModel selectStudent(String npm) {
		log.info ("select student with npm {}", npm);
        return studentMapper.selectStudent (npm);
	}

	@Override
	public List<StudentModel> selectAllStudents() {
		log.info ("select all students");
        return studentMapper.selectAllStudents ();
	}

	@Override
	public void addStudent(StudentModel student) {
		studentMapper.addStudent (student);
	}

	@Override
	public boolean deleteStudent(String npm) {
		StudentModel student = selectStudent(npm);
		if(student != null) {
			log.info ("student {} deleted", npm);
    		studentMapper.deleteStudent(npm);
    		return true;
		} else {
			return false;
		}
	}

	@Override
	public void updateStudent(StudentModel student) {
		log.info("updating student with {}, {}", student.getName(), student.getGpa());
		studentMapper.updateStudent(student);
	}

}
