package model.dao.department;

import database.Database;
import exceptions.DatabaseException;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentJDBC implements DepartmentDAO {

    private final Connection connection;

    public DepartmentJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {

    }

    @Override
    public void update(Department department) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.prepareStatement("SELECT * FROM department  WHERE Id = ?");
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return instantiateDepartment(resultSet);
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
    public List<Department> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.prepareStatement("SELECT * FROM department");
            resultSet = statement.executeQuery();

            List<Department> departmentList = new ArrayList<>();
            while (resultSet.next()) {
                departmentList.add(instantiateDepartment(resultSet));
            }
            return departmentList;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    public Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));
        return department;
    }
}
