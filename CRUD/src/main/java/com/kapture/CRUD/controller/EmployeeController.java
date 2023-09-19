package com.kapture.CRUD.controller;


import com.kapture.CRUD.model.Employee;
import com.kapture.CRUD.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping("/employees")
    public String createNewEmployee(@RequestBody Employee employee){
        employeeRepository.save(employee);
        return "Employee Created in Database";

    }

    @GetMapping("/get-employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> empList = new ArrayList<>();
        employeeRepository.findAll().forEach(empList::add);
        return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);
    }

    @GetMapping("/get-employees/{emp_id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long emp_id){
        Optional<Employee> emp = employeeRepository.findById(emp_id);
        if(emp.isPresent()){
            return new ResponseEntity<Employee>(emp.get(), HttpStatus.FOUND);
        }
        else{
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/employees/{emp_id}")
    public String updateEmployeeById(@PathVariable long emp_id, @RequestBody Employee employee){
        Optional<Employee> emp = employeeRepository.findById(emp_id);
        if(emp.isPresent()){
            Employee existEmp = emp.get();
            existEmp.setEmp_age((employee.getEmp_age()));
            existEmp.setEmp_city(employee.getEmp_city());
            existEmp.setEmp_name(employee.getEmp_name());
            existEmp.setEmp_salary(employee.getEmp_salary());
            employeeRepository.save(existEmp);
            return "Employee details against ID" +emp_id + "updated";
        }else {
            return "Employee details does not exist for Employee id" + emp_id;
        }

    }

    @DeleteMapping("/employees/{emp_id}")
    public String deleteEmployeeByEmpId(@PathVariable Long emp_id){
        employeeRepository.deleteById(emp_id);
        return "Employee deleted Successfully";
    }

    @DeleteMapping("/employees")
    public String deleteAllEmployee(){
        employeeRepository.deleteAll();
        return "Employee deleted Successfully";
    }


}
