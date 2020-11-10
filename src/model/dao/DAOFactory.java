package model.dao;

import model.dao.seller.SellerDAO;
import model.dao.seller.SellerJDBC;

public class DAOFactory {

    public static SellerDAO createSellerDAO() {
        return new SellerJDBC();
    }
}
