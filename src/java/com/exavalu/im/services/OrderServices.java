/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exavalu.im.services;

import com.exavalu.im.core.ConnectionManager;
import com.exavalu.im.pojos.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BeingJay
 */
public class OrderServices {

    public static ArrayList getAllOrders() {
        ArrayList orders = new ArrayList();
        Connection con = null;
        try {

            con = ConnectionManager.getConnection();
            String sql = "select * from orders o,customer c "
                    + "where o.customerId=c.customerId ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();

                order.setCustomerId(rs.getInt("customerId"));
                order.setCustomerName(rs.getString("customerName"));
                order.setDestinationCity(rs.getString("destinationCity"));
                order.setDestinationCountry(rs.getString("destinationCountry"));
                order.setOrderDate(rs.getString("orderDate"));
                order.setOrderId(rs.getInt("orderId"));
                order.setOrderValue(rs.getDouble("orderValue"));
                order.setTaxAmount(rs.getDouble("taxAmount"));

                orders.add(order);

            }
            System.out.println("Total number of orders = " + orders.size());

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return orders;
    }

    public static Order getOrder(String strOrderId) {
        Order order = new Order();
        Connection con = null;
        try {

            con = ConnectionManager.getConnection();
            String sql = "select * from orders o,customer c "
                    + "where o.customerId=c.customerId and orderId=? ";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, strOrderId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                order.setCustomerId(rs.getInt("customerId"));
                order.setCustomerName(rs.getString("customerName"));
                order.setDestinationCity(rs.getString("destinationCity"));
                order.setDestinationCountry(rs.getString("destinationCountry"));
                order.setOrderDate(rs.getString("orderDate"));
                order.setOrderId(rs.getInt("orderId"));
                order.setOrderValue(rs.getDouble("orderValue"));
                order.setTaxAmount(rs.getDouble("orderValue"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return order;
    }

    public static boolean saveOrder(Order order) {
        boolean success = false;

        Connection con = null;
        try {

            con = ConnectionManager.getConnection();
            String sql = "UPDATE orders SET orderDate = ?,orderValue = ?,"
                    + "taxAmount =?,destinationCity =?,destinationCountry = ? "
                    + "WHERE orderId =?  ";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,order.getOrderDate());
            ps.setDouble(2,order.getOrderValue());
            ps.setDouble(3,order.getTaxAmount());
            ps.setString(4,order.getDestinationCity());
            ps.setString(5,order.getDestinationCountry());
            ps.setInt(6,order.getOrderId());

            int row = ps.executeUpdate();

            if (row>0) {
                 success=true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return success;
    }

}
