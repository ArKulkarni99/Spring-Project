package com.aakash.employee.datain;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.aakash.employee.entity.Employee;
import com.aakash.employee.repo.EmployeeRepository;
import com.aakash.employee.services.EmployeeServiceImp;

@Component
public class DataInIt implements ApplicationRunner{
	
	private EmployeeRepository repository;
	
	@Autowired
	public DataInIt(EmployeeRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		List<Employee> list = Arrays.asList(
				new Employee("Aakash", "Sangli", 60000),
				new Employee("Sachin", "pune", 50000),
				new Employee("Vishal", "Pune", 70000), 
				new Employee("Tejaswini", "Hydarabad", 80000),
				new Employee("Avdhut", "Miraj", 20000),
				new Employee ("Reavti", "Pune", 30000),
				new Employee ("Rajesh", "Sangli", 20000),
				new Employee("Gourav", "Sangli", 25000));

		repository.saveAll(list);
		
	}

}
