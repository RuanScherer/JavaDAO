package model.dao.seller;

import database.Database;
import exceptions.DatabaseException;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerJDBC implements SellerDAO {

    private final Connection connection;

    public SellerJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                "INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, seller.getName());
            statement.setString(2, seller.getEmail());
            statement.setDate(3, new Date(seller.getBirthDate().getTime()));
            statement.setDouble(4, seller.getBaseSalary());
            statement.setInt(5, seller.getDepartment().getId());

            this.commonOperation(statement, seller);
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                    "UPDATE seller "
                            + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                            + "WHERE Id = ? ",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, seller.getName());
            statement.setString(2, seller.getEmail());
            statement.setDate(3, new Date(seller.getBirthDate().getTime()));
            statement.setDouble(4, seller.getBaseSalary());
            statement.setInt(5, seller.getDepartment().getId());
            statement.setInt(6, seller.getId());

            this.commonOperation(statement, seller);
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.prepareStatement(
            "SELECT seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?"
            );
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Department department = this.instantiateDepartment(resultSet);
                return instantiateSeller(resultSet, department);
            }
            return null;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name"
            );

            return commonFind(statement);
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.prepareStatement(
            "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? "
                    + "ORDER BY Name"
            );
            statement.setInt(1, department.getId());

            return commonFind(statement);
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    public void commonOperation(PreparedStatement statement, Seller seller) throws SQLException {
        int affectedRows = statement.executeUpdate();

        if (affectedRows > 0) {
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                seller.setId(id);
            }
            Database.closeResultSet(resultSet);
        } else {
            throw new DatabaseException("Unexpected error. No rows affected.");
        }
    }

    public List<Seller> commonFind(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();

        List<Seller> sellerList = new ArrayList<>();
        Map<Integer, Department> departmentMap = new HashMap<>();
        while (resultSet.next()) {
            Department dep = departmentMap.get(resultSet.getInt("DepartmentId"));
            if (dep == null) {
                dep = instantiateDepartment(resultSet);
                departmentMap.put(resultSet.getInt("DepartmentId"), dep);
            }
            sellerList.add(instantiateSeller(resultSet, dep));
        }

        Database.closeResultSet(resultSet);
        return sellerList;
    }

    public Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    public Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setDepartment(department);
        return seller;
    }
}
