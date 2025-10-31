package student.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import student.entity.StudentEntity;

public interface StudentService
{
	List<StudentEntity> getAllStudents();
	@Transactional
    StudentEntity getStudentId(Long studentId);
    StudentEntity saveStudent(StudentEntity studentEntity);
    void deleteStudentId(Long studentId);
    void enrollStudent(Long studentId, Long courseId);
    StudentEntity updateByStudent(StudentEntity studentEntity);
    
    @Transactional
    StudentEntity getByStudentEmailidAndStudentPassword(String emailid,String password);
	void updateStudentPassword(Long studentId, String newPassword);
	void modifyStudentPassword(String studentEmailid, String newPassword);
	@Transactional
	void unenrollStudentFromCourse(Long studentid, Long courseId);
	List<StudentEntity> getLockedStudents();
	void unlockStudent(Long studentid);
	StudentEntity getByStudentEmailid(String studentemailid);
}

