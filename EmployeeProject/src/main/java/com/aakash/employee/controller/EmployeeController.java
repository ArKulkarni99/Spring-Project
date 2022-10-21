package com.aakash.employee.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aakash.employee.entity.Employee;
import com.aakash.employee.exception.DataNotFoundException;
import com.aakash.employee.repo.EmployeeRepository;
import com.aakash.employee.services.EmployeeService;
import com.aakash.employee.util.GeneratePdfReport;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Employee> getEmpById(@PathVariable Integer id) {
		Employee employee = service.getEmpById(id);
		if(employee !=null) {
			return ResponseEntity.ok(employee);
		}else {
			throw new DataNotFoundException("Employee with ID: "+id+" Not Found");
		}
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Employee>> getAllEmp(){
		 List<Employee> emp = service.getAllEmp();
		 return ResponseEntity.ok().body(emp);
	}
	
	
	@PostMapping("/save")
	public ResponseEntity<Employee> saveEmp(@RequestBody Employee emp) {
		URI location = null;
		Employee employee = null;
		if(emp!=null) {
			employee = service.saveEmp(emp);
			location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/id/{id}")
					.buildAndExpand(employee.getId())
					.toUri();
		}
		else {
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.created(location).body(employee);
	}
	
	@GetMapping("/byname")
	public ResponseEntity<List<Employee>> getByName(@RequestParam String empName){
		return new ResponseEntity<>(service.getByName(empName), HttpStatus.OK);
	}
	
	@GetMapping("/bycity")
	public ResponseEntity<List<Employee>> getByCity(@RequestParam(name = "c") String city){
		return new ResponseEntity<>(service.getByCity(city), HttpStatus.OK);
	}
	
	@GetMapping ("/salary/{empsalary}")
	public ResponseEntity<List<Employee>> getBySalary(@PathVariable(name = "empsalary") Integer salary){
		return new ResponseEntity<>(service.getBySalary(salary),HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateEmp(@PathVariable Integer id, @RequestBody Employee emp) {
		try {
			service.updateEmpById(id, emp);
			return ResponseEntity.ok("Record updated");
		} catch (DataNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> deleteEmpByID(@PathVariable Integer id) {
		service.deleteEmpById(id);
		return ResponseEntity.noContent().build();
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
