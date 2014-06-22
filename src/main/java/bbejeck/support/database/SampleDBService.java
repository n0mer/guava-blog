package bbejeck.support.database;

import bbejeck.support.BaseSample;
import bbejeck.support.model.Person;
import com.google.common.util.concurrent.ListenableFuture;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: bbejeck
 * Date: 11/20/11
 * Time: 10:51 PM
 */

public class SampleDBService extends BaseSample {

    private JdbcConnectionPool connectionPool;
    private static final String query = "Select first_name,last_name,address,email from person where id in(";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String ADDRESS = "address";
    private static final String EMAIL = "email";

    public SampleDBService(JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Map<String, String>> getPersonDataById(List<String> rawIds) {
        try {
            Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = executeQuery(statement, query + buildInList(rawIds) + ')');
            List<Map<String, String>> results = extractResults(resultSet);
            close(resultSet, statement, connection);

            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void close(ResultSet resultSet, Statement statement, Connection connection) throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }

    @SuppressWarnings("UnusedDeclaration")
    public ListenableFuture<List<Map<String, String>>> getPersonDataByIdAsync(final List<String> ids) {
        return executorService.submit(() -> getPersonDataById(ids));
    }

    @SuppressWarnings("UnusedDeclaration")
    public ListenableFuture<List<Person>> getPersonsByIdAsync(final List<String> ids) {
        return executorService.submit(() -> getPersonsById(ids));
    }

    public List<Person> getPersonsById(List<String> ids) {
        List<Map<String, String>> personData = getPersonDataById(ids);
        List<Person> people = new ArrayList<>();
        for (Map<String, String> data : personData) {
            people.add(new Person(data));
        }
        return people;
    }

    private List<Map<String, String>> extractResults(ResultSet resultSet) {
        List<Map<String, String>> allResults = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Map<String, String> rowResults = new HashMap<>();
                rowResults.put(FIRST_NAME, resultSet.getString(FIRST_NAME));
                rowResults.put(LAST_NAME, resultSet.getString(LAST_NAME));
                rowResults.put(ADDRESS, resultSet.getString(ADDRESS));
                rowResults.put(EMAIL, resultSet.getString(EMAIL));
                allResults.add(rowResults);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allResults;
    }

    private ResultSet executeQuery(Statement statement, String query) throws SQLException {
        return statement.executeQuery(query);
    }

    private String buildInList(List<String> rawIds) {
        return String.join(",", rawIds);
    }

}
