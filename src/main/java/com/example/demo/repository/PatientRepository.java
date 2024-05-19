package com.example.demo.repository;

import java.util.List;

import com.example.demo.dto.PatientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	@Query("select p from Patient p where p.name like %:name%")
	List<PatientDto> findByName(String name);
	
	@Query("select p from Patient p where p.age = :age")
    List<Patient> findByAge(@Param("age") int age);

    @Query("SELECT p FROM Patient p WHERE LOWER(p.gender) = LOWER(:gender)")
    List<Patient> findByGender(@Param("gender") String gender);

    @Query("select p from Patient p join p.diseases d where lower(d.diseaseName) = lower(:diseaseName)")
List<Patient> findByDisease(@Param("diseaseName") String diseaseName);


}