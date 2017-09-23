package com.michaelcgood.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michaelcgood.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

}
