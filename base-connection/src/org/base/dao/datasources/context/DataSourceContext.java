/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.base.dao.datasources.context;

import java.sql.Connection;
import java.sql.Statement;
import org.base.dao.datasources.connections.AbstractConnectionPool;
import org.base.dao.datasources.variations.IDataSourceVariation;

/**
 *
 * @author mproenza
 */
public class DataSourceContext {
    private AbstractConnectionPool connectionPool;
    private IDataSourceVariation dataSourceVariation;
    
    public DataSourceContext(AbstractConnectionPool connectionPool, IDataSourceVariation dataSourceVariation) {
        this.connectionPool = connectionPool;
        this.dataSourceVariation = dataSourceVariation;
    }
    
    /*
     * Data source variations
     */
    public String getDBObjectExpression(String objectName) {
        return dataSourceVariation.getDBObjectExpression(objectName);
    }
    
    /*
     * Data source connections and transaccions
     */
    
    public Connection getConnection() {
        return connectionPool.getConnection();
    }
    
    public void close(Connection conn) {
        connectionPool.close(conn);
    }

    public void close(Statement stm) {
        connectionPool.close(stm);
    }
    
    public void startTransaction() {
        connectionPool.startTransaction();
    }

    public void commitTransaction() {
        connectionPool.commitTransaction();
    }

    public void rollbackTransaction() {
        connectionPool.rollbackTransaction();
    } 
}
