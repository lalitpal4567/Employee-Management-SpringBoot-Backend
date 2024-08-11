package com.vserviceu.employee.controller;

import com.vserviceu.employee.customException.EmailAlreadyExistedException;
import com.vserviceu.employee.model.Employee;
import com.vserviceu.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    private EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    // add new employee
    @PostMapping("/add-emp")
    public ResponseEntity<Map<String, Object>> addEmployee(@RequestPart("newEmployee") Employee newEmployee, @RequestPart(value = "imageFile", required = false) MultipartFile imageFile){
        Map<String, Object> response = new LinkedHashMap<>();
        try{
            Employee savedEmployee = employeeService.addEmployee(newEmployee, imageFile);
            response.put("message", "Employee added successfully");
            response.put("employee", savedEmployee);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(EmailAlreadyExistedException e){
            response.put("error", "Email already exists.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        catch (Exception e){
            response.put("error",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    // retrieve existing employee with specified id
    @GetMapping("/fetch-emp/{id}")
    public ResponseEntity<Map<String, Object>> getEmployeeById(@PathVariable(name = "id") Long empId){
        Employee existingEmployee = employeeService.getEmployeeById(empId);
        Map<String, Object> response = new LinkedHashMap<>();

       try{
           response.put("message", "Employee added successfully");
           response.put("Employee", existingEmployee);
           return ResponseEntity.status(HttpStatus.OK).body(response);
       }catch (Exception e){
           response.put("error", e.getMessage());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
       }
    }


    // remove existing employee with specified id
    @DeleteMapping("/remove-emp/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmpById(@PathVariable(name = "id") Long empId){
        Map<String, Object> response = new LinkedHashMap<>();

        try{
            employeeService.removeEmpById(empId);
            response.put("message", "Employee removed successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    // update existing employee with specified id
    @PutMapping("/update-emp/{id}")
    public ResponseEntity<Map<String, Object>> updateEmpById(@RequestBody Employee employee, @PathVariable(name = "id") Long empId) throws IOException {
        Map<String, Object> response = new LinkedHashMap<>();

        try{
            Employee updatedEmployee = employeeService.updateEmpById(employee, empId);
            response.put("message", "Employee updated successfully");
            response.put("Employee", updatedEmployee);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("emp/profile-picture/{id}")
    public ResponseEntity<Map<String, Object>> updateImage(@RequestPart("imageFile") MultipartFile imageFile, @PathVariable(name = "id") Long empId){
        Map<String, Object> response = new LinkedHashMap<>();

        try{
            Employee updatedEmployee = employeeService.updateImage(imageFile, empId);
            response.put("message", "Profile Picture updated successfully");
            response.put("Employee", updatedEmployee);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // retrieve the list of all existing employee
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmp(){
        List<Employee> empList = employeeService.getAllEmployee();
        return ResponseEntity.status(HttpStatus.OK).body(empList);
    }
}
