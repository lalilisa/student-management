package com.example.chatapplication.repo;

import com.example.chatapplication.domain.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Classes,Long> {

    @Query(value = "select c.subjectId from Classes c where  c.id in :list group by c.subjectId")
    List<Long> getAllIdSubject(List<Long> list);


}
