package com.example.chatapplication.repo;

import com.example.chatapplication.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {

    List<Subject> getSubjectByIdIsIn(List<Long> ids);
}
