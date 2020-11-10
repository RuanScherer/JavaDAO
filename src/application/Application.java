package application;

import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Application {

    public static void main(String[] args) {
        Department department = new Department(1, "Books");
        System.out.println(department);

        Seller seller = new Seller(
                1,
                "Ruan",
                "ruan.vscherer@gmail.com",
                new Date(),
                2800.00, department
        );
        System.out.println(seller);
    }
}
