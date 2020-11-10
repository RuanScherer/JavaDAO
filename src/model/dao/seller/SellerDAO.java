package model.dao.seller;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDAO {

    void insert(Seller seller);

    void update(Seller seller);

    void deleteById(Integer id);

    Seller findById(Integer id);

    List<Seller> findAll();
}
