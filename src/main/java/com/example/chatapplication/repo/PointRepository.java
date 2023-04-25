package com.example.chatapplication.repo;

import com.example.chatapplication.domain.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface PointRepository extends JpaRepository<Points,Long> {

    Points findByClassIdAndStudentId(Long classId,Long studentId);
}
