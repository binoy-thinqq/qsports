package com.thinqq.qsports.persistence.conf;

import java.util.List;

import org.hibernate.Session;

import com.thinqq.qsports.persistence.dataobjects.User;
import com.thinqq.qsports.persistence.dto.UserVo;
import com.thinqq.qsports.persistence.validation.BasicValidatorAdapter;

public class ServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserVo userVo = new UserVo();
		userVo.setCity("Chennai");
		userVo.setFirstName("Binoy");
		userVo.setEmail("binoybt@gmail.com");
		userVo.setRegistrationType("THIRD_PARTY");
		User user = new User();
		user.setCity("Chennai");
		user.setFirstName("Binoy");
		user.setEmail("binoybtgmail.com");
		//List<String> errors = BasicValidatorAdapter.validate(userVo);
		/*if (errors.isEmpty()) {
			Session session = SessionManager.getSessionFactory().openSession();
			session.getTransaction().begin();
		session.save(user);
		session.getTransaction().commit();
		} else {
			System.out.println(errors);
		}*/
		
		

		
		

	}

}
