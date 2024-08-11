package com.vserviceu.employee.service;

import com.vserviceu.employee.customException.EmailAlreadyExistedException;
import com.vserviceu.employee.customException.EmployeeNotFoundException;
import com.vserviceu.employee.model.Employee;
import com.vserviceu.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }


    // add new employee
    public Employee addEmployee(Employee newEmployee, MultipartFile imageFile) throws IOException {
        if (employeeRepository.existsByEmail(newEmployee.getEmail()))
            throw new EmailAlreadyExistedException("Email already exists.");
        else if (imageFile != null) {
            newEmployee.setImageName(imageFile.getName());
            newEmployee.setImageType(imageFile.getContentType());
            newEmployee.setImage(imageFile.getBytes());
        }
        return employeeRepository.save(newEmployee);
    }


    // retrieve existing employee with specified id
    public Employee getEmployeeById(Long empId){
        return employeeRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }


    // remove existing employee with specified id
    public void removeEmpById(Long empId){
        Employee existingEmployee = employeeRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        employeeRepository.deleteById(empId);
    }


    // update existing employee with specified id
    public Employee updateEmpById(Employee employee, Long empId) {
        Employee existingEmployee = employeeRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        // update the existing employee entity with new information from the request body
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setDob(employee.getDob());
        existingEmployee.setGender(employee.getGender());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.getAddress().setCity(employee.getAddress().getCity());
        existingEmployee.getAddress().setState(employee.getAddress().getState());
        existingEmployee.getAddress().setZip(employee.getAddress().getZip());
        existingEmployee.setPhoneNo(employee.getPhoneNo());

        return employeeRepository.save(existingEmployee);
    }

    public Employee updateImage(MultipartFile imageFile, Long empId) throws IOException {
        Employee existingEmployee = employeeRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        if (!imageFile.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("File type not supported");
        }

        existingEmployee.setImage(imageFile.getBytes());
        existingEmployee.setImageType(imageFile.getContentType());
        existingEmployee.setImageName(imageFile.getName());

        return employeeRepository.save(existingEmployee);
    }


    // retrieve the list of all existing employee
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }
}
