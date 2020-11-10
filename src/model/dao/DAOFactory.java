package model.dao;

import database.Database;
import model.dao.department.DepartmentDAO;
import model.dao.department.DepartmentJDBC;
import model.dao.seller.SellerDAO;
import model.dao.seller.SellerJDBC;

public class DAOFactory {

    public static SellerDAO createSellerDAO() {
        return new SellerJDBC(Database.getConnection());
    }

    public static DepartmentDAO createDepartmentDAO() {
        return new DepartmentJDBC(Database.getConnection());
    }
}
