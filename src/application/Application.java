package application;

import model.dao.DAOFactory;
import model.dao.seller.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Application {

    public static void main(String[] args) {
        SellerDAO sellerDAO = DAOFactory.createSellerDAO();

        System.out.println("===== TEST 1: seller findById =====");
        Seller seller = sellerDAO.findById(2);
        System.out.println(seller);
    }
}
