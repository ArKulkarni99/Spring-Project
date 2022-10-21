package com.aakash.employee.services;

import java.util.List;

import com.aakash.employee.entity.Employee;

public interface EmployeeService {
	public Employee getEmpById(Integer id);
	public List<Employee> getAllEmp();
	public List<Employee> getByName(String empName);
	public List<Employee> getByCity(String city);
	public List<Employee> getBySalary(Integer salary);
	
	public Employee saveEmp(Employee emp);
	public void saveAllEmp(List<Employee> emp);
	
	public void updateEmpById(Integer empId,Employee emp);
	
	public void deleteEmpById(Integer empId);
	public void deleteAllEmp();
}
