/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhaonan.forum.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zhaonan.forum.util.DatabaseUtil;

/**
 *
 * @author jerry
 */
public class JdbcTemplate
{

    public Object doTask(JdbcTask task)
    {
        try
        {
            Object result;
            Connection conn = DatabaseUtil.getInstance().getConnection();
            result = task.executeTask(conn);
            conn.close();
            return result;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(JdbcTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
