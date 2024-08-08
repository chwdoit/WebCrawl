package oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementUtil {
    private final String insertSQL = "INSERT INTO GMARKET(상품이름, 상품가격, 상품구매수, 상품리뷰수) VALUES(?, ?, ?, ?)";

    public void insertGmarketRecord(Connection connection, Gmarket gmarket) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, gmarket.productName());
            preparedStatement.setInt(2, gmarket.productPrice());
            preparedStatement.setInt(3, gmarket.productbuycount());
            preparedStatement.setInt(4, gmarket.productreviewcount());
            int row = preparedStatement.executeUpdate();
            System.out.println("Inserted row: " + row);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1) { // ORA-00001: unique constraint violated
                System.out.println("Duplicate entry found for: " + gmarket.productName());
            } else {
                System.out.println("SQL error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
