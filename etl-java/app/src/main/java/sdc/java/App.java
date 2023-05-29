/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package sdc.java;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.time.Instant;
import java.sql.Timestamp;
import java.sql.*;

public class App {
    public static void main(String[] args) {
        String[] filePaths = {
                "/Users/xintongmi/code/hack-reactor/RFP2303/sdc-moda-products/seeds/product.csv",
                "/Users/xintongmi/code/hack-reactor/RFP2303/sdc-moda-products/seeds/features.csv",
                "/Users/xintongmi/code/hack-reactor/RFP2303/sdc-moda-products/seeds/related.csv",
                "/Users/xintongmi/code/hack-reactor/RFP2303/sdc-moda-products/seeds/styles.csv",
                "/Users/xintongmi/code/hack-reactor/RFP2303/sdc-moda-products/seeds/skus.csv",
                "/Users/xintongmi/code/hack-reactor/RFP2303/sdc-moda-products/seeds/photos.csv",};
        String[] queryStrs = {
                "INSERT INTO product (id, name, slogan, description, category, default_price, created_at, updated_at) VALUES (?,?,?,?,?,?,?,?,)",
                "INSERT INTO feature (id, product_id, feature, value) VALUES (?,?,?,?)",
                "INSERT INTO related (id, current_product_id, related_product_id) VALUES (?,?,?)",
                "INSERT INTO style (id, product_id, name, sale_price, original_price, default_style, created_at, updated_at) VALUES (?,?,?,?,?,?,?,?)",
                "INSERT INTO sku (id, style_id, size, quantity) VALUES (?,?,?,?)",
                "INSERT INTO photo (id, style_id, url, thumbnail_url) VALUES (?,?,?,?)"};
        for (int i = 0; i < 6; i++) {
            App.readCsv(i, filePaths[i], queryStrs[i]);
        }
    }

    private static void readCsv(int index, String filePath, String queryStr) {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/sdcdb",
                    prop.getProperty("db.user"), prop.getProperty("db.password"));

            // System.out.println(prop.getProperty("db.user"));
            FileInputStream stream = new FileInputStream(filePath);
            Scanner sc = new Scanner(stream);
            Boolean firstline = true;
            while (sc.hasNextLine()) {
                String row = sc.nextLine();
                if (firstline) {
                    firstline = false;
                    continue;
                }
                String[] cols = row.split(",");
                PreparedStatement state = conn.prepareStatement(queryStr);
                if (index == 0) {
                    state.setInt(1, Integer.valueOf(cols[0]));
                    state.setString(2, cols[1]);
                    state.setString(3, cols[2]);
                    state.setString(4, cols[3]);
                    state.setString(5, cols[4]);
                    state.setFloat(6, Float.parseFloat(cols[5]));
                    state.setTimestamp(7, Timestamp.from(Instant.now()));
                    state.setTimestamp(8, Timestamp.from(Instant.now()));
                } else if (index == 2) {
                    state.setInt(1, Integer.valueOf(cols[0]));
                    state.setInt(2, Integer.valueOf(cols[1]));
                    state.setString(3, cols[2]);
                    state.setString(4, cols[3]);
                } else if (index == 3) {
                    state.setInt(1, Integer.valueOf(cols[0]));
                    state.setInt(2, Integer.valueOf(cols[1]));
                    state.setString(3, cols[2]);
                    if (cols[3].equals("null")) {
                        state.setNull(4, Types.FLOAT);
                    } else {
                        state.setFloat(4, Float.parseFloat(cols[3]));
                    }
                    state.setFloat(5, Float.parseFloat(cols[4]));
                    state.setBoolean(6, cols[5].equals("null"));
                } else if (index == 4) {
                    state.setInt(1, Integer.valueOf(cols[0].replaceAll("\\s", "")));
                    state.setInt(2, Integer.valueOf(cols[1].replaceAll("\\s", "")));
                    state.setString(3, cols[2]);
                    state.setInt(4, Integer.valueOf(cols[3].replaceAll("\\s", "")));
                } else if (index == 5) {
                    state.setInt(1, Integer.valueOf(cols[0].replaceAll("\\s", "")));
                    state.setInt(2, Integer.valueOf(cols[1].replaceAll("\\s", "")));
                    state.setString(3, cols[2]);
                    state.setString(4, cols[3]);
                }
                state.executeUpdate();
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}