package demo.multitenant.app.multischema.config;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(MultiTenantConnectionProviderImpl.class);

    private final DataSource dataSource;

    @Value("${application.default-schema}")
    private String defaultSchema;

    public MultiTenantConnectionProviderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.info("GET ANY CONNECTION: {} - {}",connection.getCatalog(), connection.getSchema());
        connection.setCatalog(defaultSchema);
        connection.setSchema(defaultSchema);
        return connection;
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.info("GET TENANT CONNECTION: {} - {}",connection.getCatalog(), connection.getSchema());
        connection.setCatalog(tenantIdentifier);
        connection.setSchema(tenantIdentifier);
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

//    private boolean createDB(String db, Connection con) {
//        try {
//            PreparedStatement stmt = con.prepareStatement("CREATE SCHEMA IF NOT EXISTS " + db);
//            stmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
}
