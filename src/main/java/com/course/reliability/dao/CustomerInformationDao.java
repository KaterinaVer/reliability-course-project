package com.course.reliability.dao;

import com.course.reliability.model.CustomerInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Repository
public class CustomerInformationDao {

    private static final String ID = "id";
    private static final String AGE = "age";
    private static final String SALARY = "salary";
    private static final String REAL_ESTATE = "realEstate";
    private static final String DEBT = "debt";
    private static final String ELEC = "elec";
    private static final String GAS = "gas";
    private static final String RELIABILITY = "reliability";

    @Value("${findAll}")
    private String findAllSql;
    @Value("${getCustomerInformationById}")
    private String getCustomerInformationByIdSql;
    @Value("${addCustomerInformation}")
    private String addCustomerInformationSql;
    @Value("${updateCustomerReliability}")
    private String updateCustomerReliabilitySql;
    @Value("${updateCustomerInformation}")
    private String updateCustomerInformationSql;
    @Value("${deleteCustomerInformation}")
    private String deleteCustomerInformationSql;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CustomerInformationDao(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<CustomerInformation> customerInformationRowMapper =(resultSet, i)->new CustomerInformation(
            resultSet.getInt("id"),
            resultSet.getInt("age"),
            resultSet.getDouble("salary"),
            resultSet.getInt("real_estate"),
            resultSet.getDouble("debt"),
            resultSet.getDouble("elec"),
            resultSet.getDouble("gas"),
            resultSet.getDouble("reliability")
    );

    public List<CustomerInformation> findAll() throws DataAccessException{
        return namedParameterJdbcTemplate.query(findAllSql, customerInformationRowMapper);

    }

    public CustomerInformation getCustomerInformationById(final Integer id) throws DataAccessException {
        return namedParameterJdbcTemplate
                .queryForObject(getCustomerInformationByIdSql, new MapSqlParameterSource(ID, id), customerInformationRowMapper);
    }

    public Integer insertCustomerInformation(final CustomerInformation customerInformation) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue(AGE, customerInformation.getAge());
        namedParameters.addValue(SALARY, customerInformation.getSalary());
        namedParameters.addValue(REAL_ESTATE, customerInformation.getRealEstate());
        namedParameters.addValue(DEBT, customerInformation.getDebt());
        namedParameters.addValue(ELEC, customerInformation.getElec());
        namedParameters.addValue(GAS, customerInformation.getGas());

        namedParameterJdbcTemplate
                .update(addCustomerInformationSql, namedParameters, keyHolder,new String[]{"id"});

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void updateCustomerReliability(final Double reliability, final Integer id) throws DataAccessException {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue(RELIABILITY, reliability);
        namedParameters.addValue(ID, id);

        namedParameterJdbcTemplate.update(updateCustomerReliabilitySql, namedParameters);
    }

    public void updateCustomerInformation(final CustomerInformation customerInformation, final Double reliability) throws DataAccessException {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue(ID, customerInformation.getId());
        namedParameters.addValue(AGE, customerInformation.getAge());
        namedParameters.addValue(SALARY, customerInformation.getSalary());
        namedParameters.addValue(REAL_ESTATE, customerInformation.getRealEstate());
        namedParameters.addValue(DEBT, customerInformation.getDebt());
        namedParameters.addValue(ELEC, customerInformation.getElec());
        namedParameters.addValue(GAS, customerInformation.getGas());
        namedParameters.addValue(RELIABILITY, reliability);

        namedParameterJdbcTemplate.update(updateCustomerInformationSql, namedParameters);
    }

    public Integer deleteCustomerInformation(Integer id) throws DataAccessException {
        return namedParameterJdbcTemplate
                .update(deleteCustomerInformationSql, new MapSqlParameterSource(ID,id));
    }
}
