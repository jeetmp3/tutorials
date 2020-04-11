//package demo.multitenant.app.multipledb.config;
//
//import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
//import org.springframework.stereotype.Component;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@Component
//public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {
//
//    @Override
//    public Connection getAnyConnection() throws SQLException {
//        return null;
//    }
//
//    @Override
//    public void releaseAnyConnection(Connection connection) throws SQLException {
//
//    }
//
//    @Override
//    public Connection getConnection(String tenantIdentifier) throws SQLException {
//        return null;
//    }
//
//    @Override
//    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
//
//    }
//
//    @Override
//    public boolean supportsAggressiveRelease() {
//        return false;
//    }
//
//    @Override
//    public boolean isUnwrappableAs(Class unwrapType) {
//        return false;
//    }
//
//    @Override
//    public <T> T unwrap(Class<T> unwrapType) {
//        return null;
//    }
//}
