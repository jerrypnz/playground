/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zhaonan.forum.service.impl;

import java.sql.Connection;

/**
 *
 * @author jerry
 */
public interface JdbcTask
{
    Object executeTask(Connection conn);
}
