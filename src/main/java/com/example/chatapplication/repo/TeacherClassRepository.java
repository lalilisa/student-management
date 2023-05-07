package com.example.chatapplication.repo;
import com.example.chatapplication.domain.StudentClass;
import com.example.chatapplication.domain.Teacher;
import com.example.chatapplication.domain.TeacherClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherClassRepository extends JpaRepository<TeacherClass,Long> {

    List<TeacherClass> findAllByTeacherId(Long teacherId);

    @Query(value = "select ts.classId from TeacherClass  ts where ts.teacherId=:teacherId")
    List<Long> getListIdClass(Long teacherId);

    @Query(value = "select ts.classId from TeacherClass  ts inner join Classes c " +
            " on ts.classId=c.id " +
            "where ts.teacherId=:teacherId and c.term=:term and c.season =:season ")
    List<Long> getListIdClassInTermAndSeason(Long teacherId,Integer term,String season);

    TeacherClass getTeacherClassByClassIdAndTeacherId(Long classId, Long teacherId);

}
