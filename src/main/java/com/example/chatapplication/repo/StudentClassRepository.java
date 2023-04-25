package com.example.chatapplication.repo;

import com.example.chatapplication.domain.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass,Long> {

    List<StudentClass> findAllByStudentId(Long studentId);

    @Query(value = "select ss.classId from StudentClass ss " +
            "inner join Classes c on ss.classId=c.id " +
            "where ss.studentId=:studentId and c.term =:term and  c.season=:season")
    List<Long>  getListIdClass(Long studentId,Integer term,String season);


    @Query(value = "select  ss.studentId from StudentClass ss where ss.classId = :classId group by ss.studentId")
    List<Long> getListStudentIdByClassId(Long classId);


    @Query(value = "select  ss.classId from StudentClass ss where ss.classId = :studentId group by ss.classId")
    List<Long> getListClassIdByStudentId(Long studentId);

    StudentClass getStudentClassByClassIdAndStudentId(Long classId,Long studentId);
}
