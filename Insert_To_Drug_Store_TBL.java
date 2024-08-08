package oracledb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert_To_Drug_Store_TBL {

    public static void main(String[] args) {
        Connection connect = null;
        final String connectionURL = "jdbc:oracle:thin:@192.168.154.1:1521:xe";

        try (InputStream inputStream = new FileInputStream("D:/Code/drug_stores.csv");
             Reader reader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            Class.forName("oracle.jdbc.OracleDriver");
            connect = DriverManager.getConnection(connectionURL, "final", "final");

            final String insert_sql = """
                    INSERT INTO DRUGSTORES(DUTYADDR, DUTYNAME, DUTYTEL1, DUTYTIME1C, DUTYTIME1S, DUTYTIME2C, 
                    DUTYTIME2S, DUTYTIME3C, DUTYTIME3S, DUTYTIME4C, DUTYTIME4S, DUTYTIME5C, DUTYTIME5S, DUTYTIME6C,
                    DUTYTIME6S, DUTYTIME7C, DUTYTIME7S, DUTYTIME8C, DUTYTIME8S, HPID, WGS84LAT, WGS84LON)
                    VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;

            PreparedStatement preparedStatement = null;
            String iphone_str;
            while ((iphone_str = bufferedReader.readLine()) != null) {
                String[] iphone_arr = iphone_str.split(",");

                if (iphone_arr.length < 22) {
                    System.out.println("ERROR: Insufficient data in line: " + iphone_str);
                    continue;
                }

                try {
                    float floatvalue21 = Float.parseFloat(iphone_arr[20]);
                    float floatvalue22 = Float.parseFloat(iphone_arr[21]);

                    preparedStatement = connect.prepareStatement(insert_sql);
                    preparedStatement.setString(1, iphone_arr[0]);
                    preparedStatement.setString(2, iphone_arr[1]);
                    preparedStatement.setString(3, iphone_arr[2]);
                    preparedStatement.setString(4, iphone_arr[3]);
                    preparedStatement.setString(5, iphone_arr[4]);
                    preparedStatement.setString(6, iphone_arr[5]);
                    preparedStatement.setString(7, iphone_arr[6]);
                    preparedStatement.setString(8, iphone_arr[7]);
                    preparedStatement.setString(9, iphone_arr[8]);
                    preparedStatement.setString(10, iphone_arr[9]);
                    preparedStatement.setString(11, iphone_arr[10]);
                    preparedStatement.setString(12, iphone_arr[11]);
                    preparedStatement.setString(13, iphone_arr[12]);
                    preparedStatement.setString(14, iphone_arr[13]);
                    preparedStatement.setString(15, iphone_arr[14]);
                    preparedStatement.setString(16, iphone_arr[15]);
                    preparedStatement.setString(17, iphone_arr[16]);
                    preparedStatement.setString(18, iphone_arr[17]);
                    preparedStatement.setString(19, iphone_arr[18]);
                    preparedStatement.setString(20, iphone_arr[19]);
                    preparedStatement.setFloat(21, floatvalue21);
                    preparedStatement.setFloat(22, floatvalue22);

                    final int row = preparedStatement.executeUpdate();
                    System.out.println("Saved index: " + row);

                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Invalid number format in line: " + iphone_str);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (preparedStatement != null) {
                        try {
                            preparedStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: Oracle JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("ERROR: SQL exception occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ERROR: IO exception occurred.");
            e.printStackTrace();
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    System.out.println("ERROR: Failed to close the connection.");
                    e.printStackTrace();
                }
            }
        }
    }
}
