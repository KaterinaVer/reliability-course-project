package com.course.reliability.dao;

import com.course.reliability.model.CustomerInformation;
import org.springframework.beans.factory.annotation.Autowired;
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
        String findAllSql = "select * from customer_information";
        return namedParameterJdbcTemplate.query(findAllSql, customerInformationRowMapper);

    }

    public CustomerInformation getCustomerInformationById(final Integer id) throws DataAccessException {
        String getCustomerInformationByIdSql = "select * from customer_information where id=:id";
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

        String addCustomerInformationSql = "insert into customer_information(age,salary,real_estate,debt,elec,gas) values(:age,:salary,:realEstate,:debt,:elec,:gas)";
        namedParameterJdbcTemplate
                .update(addCustomerInformationSql, namedParameters, keyHolder,new String[]{"id"});

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void updateCustomerReliability(final Double reliability, final Integer id) throws DataAccessException {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue(RELIABILITY, reliability);
        namedParameters.addValue(ID, id);

        String updateCustomerReliabilitySql = "update customer_information set reliability=:reliability where id=:id";
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

        String updateCustomerInformationSql = "update customer_information set age=:age, salary=:salary, real_estate=:realEstate, debt=:debt, elec=:elec, gas=:gas, reliability=:reliability where id=:id";
        namedParameterJdbcTemplate.update(updateCustomerInformationSql, namedParameters);
    }

    public Integer deleteCustomerInformation(Integer id) throws DataAccessException {
        String deleteCustomerInformationSql = "delete from customer_information where id=:id";
        return namedParameterJdbcTemplate
                .update(deleteCustomerInformationSql, new MapSqlParameterSource(ID,id));
    }
}
