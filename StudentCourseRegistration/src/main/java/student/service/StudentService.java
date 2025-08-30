package student.service;

import java.util.List;

import student.entity.StudentEntity;

public interface StudentService
{
	List<StudentEntity> getAllStudents();
    StudentEntity getStudentId(Long studentId);
    StudentEntity saveStudent(StudentEntity studentEntity);
    void deleteStudentId(Long studentId);
    void enrollStudent(Long studentId, Long courseId);
    StudentEntity updateByStudent(StudentEntity studentEntity);
}

