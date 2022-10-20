package com.aakash.employee.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aakash.employee.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	public List<Employee> findByName(String empName);
	
	public List<Employee> findByCity(String city);
	
	public List<Employee> findBySalary(Integer salary);
}
