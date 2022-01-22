/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exavalu.im.services;

import com.exavalu.im.core.ConnectionManager;
import com.exavalu.im.pojos.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author BeingJay
 */
public class ProductServices {

    public static ArrayList getAllProducts() {
        
        ArrayList products = new ArrayList();
        Connection con = null;
        try {

            con = ConnectionManager.getConnection();
            String sql = "SELECT * FROM product";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();

                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setProductMake(rs.getString("productMake"));
                product.setAvailability(rs.getInt("availability"));
                
               DecimalFormat df = new DecimalFormat("0.00");
               double a=rs.getDouble("price");
               product.setPrice(Double.parseDouble(df.format(a)));
                products.add(product);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return products;
    }

    public static Product getProduct(String strProductId) {
        Product product = new Product();
        Connection con = null;
        try {

            con = ConnectionManager.getConnection();
            String sql = "SELECT * FROM product where productId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, strProductId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                
                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setProductMake(rs.getString("productMake"));
                product.setAvailability(rs.getInt("availability"));
                product.setPrice(rs.getDouble("price"));
                

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return product;
    }

    public static boolean saveProduct(Product product) {
        boolean success = false;
        Connection con = null;
        try {

            con = ConnectionManager.getConnection();
            String sql = "UPDATE product SET productName = ?,"
                    + "productMake = ?,price = ?,availability = ? WHERE productId = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, product.getProductName());
            ps.setString(2, product.getProductMake());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getAvailability());
            ps.setInt(5, product.getProductId());

            int row = ps.executeUpdate();

            if (row > 0) {

                success = true;

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return success;

    }
    
}
