package gestion.services;


import org.fluttercode.datafactory.impl.DataFactory;

import gestion.entities.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class DataService {
    private List<Employee> employeeList = new ArrayList<>();
    List<String> departments = Arrays.asList("HR", "Admin", "IT", "Sales");

    DataService() {
        DataFactory dataFactory = new DataFactory();
        
        for (int i = 1; i < 50; i++) {
            Employee employee = new Employee();
            employee.setId(i);
            employee.setName(dataFactory.getName());
            employee.setPhoneNumber(String.format("%s-%s-%s", dataFactory.getNumberText(3),
                    dataFactory.getNumberText(3),
                    dataFactory.getNumberText(4)));
            employee.setAddress(dataFactory.getAddress() + "," + dataFactory.getCity());
            employee.setDepartment(dataFactory.getItem(departments));
            employeeList.add(employee);
            System.out.println(employee);
        }
    }

    public List<String> getDepartments() {
        return departments;
    }

    public List<Employee> getEmployeeList() {
    	System.out.println(employeeList.size());
        return employeeList;
    }
}