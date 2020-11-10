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
    }
}
