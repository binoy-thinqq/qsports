package com.thinqq.qsports.persistence.conf;

import java.util.List;

import org.hibernate.Session;

import com.thinqq.qsports.persistence.dataobjects.User;
import com.thinqq.qsports.persistence.dto.UserVo;
import com.thinqq.qsports.persistence.validation.ValidationUtil;

public class ServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserVo userVo = new UserVo();
		userVo.setCity("Chennai");
		userVo.setFirstName("Binoy");
		userVo.setEmail("binoybtgmail.com");
		User user = new User();
		user.setCity("Chennai");
		user.setFirstName("Binoy");
		user.setEmail("binoybtgmail.com");
		List<String> errors = ValidationUtil.validate(userVo);
		if (errors.isEmpty()) {
			Session session = SessionManager.getSessionFactory().openSession();
			session.getTransaction().begin();
		session.save(user);
		session.getTransaction().commit();
		} else {
			System.out.println(errors);
		}
		
		

		
		

	}

}
