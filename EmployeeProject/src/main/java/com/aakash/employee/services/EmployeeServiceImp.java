package com.aakash.employee.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aakash.employee.entity.Employee;
import com.aakash.employee.repo.EmployeeRepository;

@Service
public class EmployeeServiceImp implements EmployeeService{
	
	private EmployeeRepository repository;
	
	public EmployeeServiceImp(EmployeeRepository repository) {
		this.repository = repository;
	}

	@Override
	public Employee getEmpById(Integer id) {
		Employee employee = repository.findById(id).orElse(null);
		return employee;
	}

	@Override
	public List<Employee> getAllEmp() {
		Iterable<Employee> empItr = repository.findAll();
		List<Employee>emplist = new ArrayList<Employee>();
		empItr.forEach(x->emplist.add(x));
		return emplist;
	}

	@Override
	public List<Employee> getByName(String empName) {
		return repository.findByName(empName);
	}

	@Override
	public List<Employee> getByCity(String city) {
		return repository.findByCity(city);
	}

	@Override
	public void saveEmp(Employee emp) {
		repository.save(emp);		
	}

	@Override
	public void saveAllEmp(List<Employee> empList) {
		repository.saveAll(empList);
	}

	@Override
	public void updateEmpById(Integer empId, String name) {
		Employee employee = repository.findById(empId).orElse(null);
		employee.setName(name);
		repository.save(employee);	
	}

	@Override
	public void deleteEmpById(Integer empId) {
		repository.deleteById(empId);
		
	}

	@Override
	public void deleteAllEmp() {
		repository.deleteAll();
		
	}

	@Override
	public List<Employee> getBySalary(Integer salary) {
		return repository.findBySalary(salary);
	}

}
