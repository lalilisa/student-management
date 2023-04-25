package com.example.chatapplication.repo;

import com.example.chatapplication.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Student findStudentByAccountId(Long accountId);

    Student findStudentByCode(String code);

    Student findStudentByEmail(String email);
}
