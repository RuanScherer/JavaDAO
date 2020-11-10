package application;

import model.entities.Department;

public class Application {

    public static void main(String[] args) {
        Department department = new Department(1, "Books");
        System.out.println(department);
    }
}
