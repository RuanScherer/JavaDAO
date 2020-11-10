package application;

import exceptions.DatabaseException;
import model.dao.DAOFactory;
import model.dao.department.DepartmentDAO;
import model.entities.Department;

import java.util.Date;
import java.util.List;

public class Application2 {

    public static void main(String[] args) {
        DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();

        System.out.println("===== TEST 1: department findById =====");
        Department department = departmentDAO.findById(2);
        System.out.println(department);

        System.out.println("===== TEST 2: department findAll =====");
        List<Department> departmentList = departmentDAO.findAll();
        for (Department element : departmentList) {
            System.out.println(department);
        }

        System.out.println("===== TEST 3: department insert =====");
        department = new Department(null, "TI");
        departmentDAO.insert(department);
        System.out.println(department);

        System.out.println("===== TEST 4: department update =====");
        department = departmentDAO.findById(5);
        department.setName("Design");
        departmentDAO.update(department);
        System.out.println(department);
    }
}
