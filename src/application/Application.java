package application;

import exceptions.DatabaseException;
import model.dao.DAOFactory;
import model.dao.seller.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        SellerDAO sellerDAO = DAOFactory.createSellerDAO();

        System.out.println("===== TEST 1: seller findById =====");
        Seller seller = sellerDAO.findById(2);
        System.out.println(seller);

        System.out.println("\n===== TEST 2: seller findByDepartment =====");
        Department department = new Department(2, null);
        List<Seller> sellerList = sellerDAO.findByDepartment(department);
        for (Seller element : sellerList) {
            System.out.println(element);
        }

        System.out.println("\n===== TEST 3: seller findAll =====");
        sellerList = sellerDAO.findAll();
        for (Seller element : sellerList) {
            System.out.println(element);
        }

        System.out.println("\n===== TEST 4: seller insert =====");
        seller = new Seller(
                null,
                "Ruan",
                "ruan.vscherer@gmail.com",
                new Date(),
                2800.00,
                department
        );
        try {
            sellerDAO.insert(seller);
            System.out.println("Seller inserted");
        } catch (DatabaseException exception) {
            System.out.println("Fail on insert.");
        }
    }
}
