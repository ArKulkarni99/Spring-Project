package com.aakash.employee.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService service;
	
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Employee> getEmpById(@PathVariable Integer id) {
		LOG.info("getEmpById Called");
		Employee employee = service.getEmpById(id);
		if(employee !=null) {
			LOG.info("Got Employee By Id :"+id);
			return ResponseEntity.ok(employee);
		}else {
			LOG.error("No Data Found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Employee>> getAllEmp(){
		 LOG.info("getAllEmp Called");
		 List<Employee> emp = service.getAllEmp();
		 return ResponseEntity.ok().body(emp);
	}
	
	
	@PostMapping("/save")
	public ResponseEntity<Employee> saveEmp(@RequestBody Employee emp) {
		LOG.info("saveEmp Called");
		URI location = null;
		Employee employee = null;
		if(emp!=null) {
			employee = service.saveEmp(emp);
			location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/id/{id}")
					.buildAndExpand(employee.getId())
					.toUri();
			LOG.info("Emp is not null");
		}
		else {
			LOG.error("Emp is null");
			return ResponseEntity.badRequest().body(null);
		}
		LOG.info("Emp is saved");
		return ResponseEntity.created(location).body(employee);
	}
	
	@GetMapping("/byname")
	public ResponseEntity<List<Employee>> getByName(@RequestParam String empName){
		LOG.info("getByName called");
		return new ResponseEntity<>(service.getByName(empName), HttpStatus.OK);
	}
	
	@GetMapping("/bycity")
	public ResponseEntity<List<Employee>> getByCity(@RequestParam(name = "c") String city){
		LOG.info("getByCity called");
		return new ResponseEntity<>(service.getByCity(city), HttpStatus.OK);
	}
	
	@GetMapping ("/salary/{empsalary}")
	public ResponseEntity<List<Employee>> getBySalary(@PathVariable(name = "empsalary") Integer salary){
		LOG.info("getBySalary called");
		return new ResponseEntity<>(service.getBySalary(salary),HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateEmp(@PathVariable Integer id, @RequestBody Employee emp) {
		LOG.info("updateEmp called");
		try {
			service.updateEmpById(id, emp);
			LOG.info("Employee Updated");
			return ResponseEntity.ok("Record updated");
		} catch (DataNotFoundException e) {
			LOG.error("Data Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> deleteEmpByID(@PathVariable Integer id) {
		LOG.info("deleteEmpByID called");
		service.deleteEmpById(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/pdfreport", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> empReport(){
		LOG.info("empReport called");
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
