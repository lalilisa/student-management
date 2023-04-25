package com.example.chatapplication.repo;

import com.example.chatapplication.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Teacher findTeacherByAccountId(Long accountId);

}
