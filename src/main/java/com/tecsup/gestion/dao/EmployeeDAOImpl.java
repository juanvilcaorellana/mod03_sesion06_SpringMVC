package com.tecsup.gestion.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import com.tecsup.gestion.exception.DAOException;
import com.tecsup.gestion.exception.LoginException;
import com.tecsup.gestion.mapper.EmployeeMapper;
import com.tecsup.gestion.model.Employee;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
	
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Employee validate(String login, String clave) throws LoginException, DAOException {

		String query = "SELECT login, password, employee_id, first_name, last_name, salary, department_id  "
				+ " FROM EMPLOYEES WHERE login=? AND password=?";
	
		Object[] params = new Object[] { login, clave };
	
		try {
	
			Employee emp = (Employee) jdbcTemplate.queryForObject(query, params, new EmployeeMapper());
			//
			return emp;
	
		} catch (EmptyResultDataAccessException e) {
			logger.info("Employee y/o clave incorrecto");
			throw new LoginException();
		} catch (Exception e) {
			logger.info("Error: " + e.getMessage());
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public Employee findEmployeeByLogin(String login) {

		String query = "SELECT employee_id, login, password, first_name, last_name, salary, department_id "
				+ " FROM EMPLOYEES WHERE login = ? ";

		Object[] params = new Object[] { login };
		Employee employee=null;

		try {

			employee = jdbcTemplate.queryForObject(query, params, new EmployeeMapper());
			
		} catch (EmptyResultDataAccessException e) {
			//throw new EmptyResultException();
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("Error: " + e.getMessage());
			//throw new DAOException(e.getMessage());
			e.printStackTrace();
					
		}
		//
		logger.info("empleado: " + employee);
		return employee;
}//end
}