package com.aakash.employee.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aakash.employee.entity.Employee;
import com.aakash.employee.exception.DataNotFoundException;
import com.aakash.employee.services.EmployeeService;
import com.aakash.employee.util.GeneratePdfReport;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	@GetMapping("/id/{id}")
	public Employee getEmpById(@PathVariable Integer id) {
		Employee employee = service.getEmpById(id);
		if(employee !=null) {
			return employee;
		}else {
			throw new DataNotFoundException("Employee with ID: "+id+" Not Found");
		}
	}
	
	@GetMapping("/getAll")
	public List<Employee> getAllEmp(){
		return service.getAllEmp();
	}
	
	
	@PostMapping("/save")
	public String saveEmp(@RequestBody Employee emp) {
		if(emp!=null) {
			service.saveEmp(emp);
		}
		else {
			return "Please fill all field";
		}
		return "Record Saved";
	}
	
	@GetMapping("/byname")
	public List<Employee> getByName(@RequestParam String empName){
		return service.getByName(empName);
	}
	
	@GetMapping("/bycity")
	public List<Employee> getByCity(@RequestParam(name = "c") String city){
		return service.getByCity(city);
	}
	
	@GetMapping ("/salary/{empsalary}")
	public List<Employee> getBySalary(@PathVariable(name = "empsalary") Integer salary){
		return service.getBySalary(salary);
	}
	
	@DeleteMapping("/{id}")
	public String deleteEmpByID(@PathVariable Integer id) {
		service.deleteEmpById(id);
		return "Deleted Successfully";
	}
	
	@RequestMapping(value = "/pdfreport", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> empReport(){
		List<Employee> empList = service.getAllEmp();
		
		ByteArrayInputStream stream = GeneratePdfReport.empReport(empList);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=employee_report.pdf");
		
		return ResponseEntity
		        .ok()
		        .headers(headers)
		        .contentType(MediaType.APPLICATION_PDF)
		        .body(new InputStreamResource(stream));
	}
}
