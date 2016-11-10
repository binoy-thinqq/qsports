package com.thinqq.qsports.server.process;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jdo.PersistenceManager;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.Base64Utils;
import com.thinqq.qsports.server.constants.ServerConstants;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.persistence.dao.UserDAO;
import com.thinqq.qsports.server.pesistence.dataobjects.User;
import com.thinqq.qsports.shared.UserStatusEnum;
import com.thinqq.qsports.shared.registration.ConfirmationRequestVo;
import com.thinqq.qsports.shared.registration.ConfirmationResponseVo;
import com.thinqq.qsports.shared.registration.RegistrationRequestVo;
import com.thinqq.qsports.shared.registration.RegistrationResponseVo;

public class RegistrationProcess {
	private static RegistrationProcess s_instance = new RegistrationProcess();
	private Logger logger = Logger.getLogger(RegistrationProcess.class.getCanonicalName());
	private RegistrationProcess(){}
	public static RegistrationProcess getInstance(){
		return s_instance;
	}
	public RegistrationResponseVo register(RegistrationRequestVo registrationRequest){
		RegistrationResponseVo responseVo = new RegistrationResponseVo();
		PersistenceManager pm = DAO.getPersistenceManager();
		try{

			UserDAO userDAO = new UserDAO();
			User user =userDAO.findUserByEmail(registrationRequest.getEmail(), pm);
			if(user!=null && !UserStatusEnum.UNCONFIRMED.name().equals(user.getStatus())){
				responseVo.setEmailAlreadyRegistered(true);
				responseVo.setRegisteredSuccessfully(false);
				return responseVo;
			}
			User newUser = new User();
			newUser.setEmail(registrationRequest.getEmail());
			newUser.setIsoCountryCode(registrationRequest.getIsoCountryCode());
			String activationKey = getRandom5DigitCode();
			newUser.setConfirmationCode(activationKey);
			Date creationTime = Calendar.getInstance().getTime();
			newUser.setCreatedTime(creationTime);
			newUser.setUpdatedTime(creationTime);

			pm.currentTransaction().begin();
			userDAO.insert(newUser, pm);
			sendConformationEmail(registrationRequest.getEmail(),activationKey,registrationRequest);
			pm.currentTransaction().commit();
			responseVo.setConfirmationEmailId(registrationRequest.getEmail());
			responseVo.setRegisteredSuccessfully(true);
		}catch(Exception e){
			logger.log(Level.SEVERE, "User Not Registered", e);
			responseVo.setRegisteredSuccessfully(false);
		}finally{
			pm.close();
		}
		return responseVo;
	}
	private String getRandom5DigitCode() {
		for(int i=0;i<20;i++){
		String code = Double.toString(Math.random()*100000);
		if(code.length() >= 5){
			code = code.substring(0, 5);
			return code;
		}
		}
		return "180999";
	}
	private boolean sendConformationEmail(String toAddress, String activationKey, RegistrationRequestVo registrationRequest){

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String img = "images/cricket-icon.png";

		String htmlText = "<html>";
		String style = "<head><style type=\"text/css\">" +
				".headimg{" +
				"	float:left;}" +
				".heading{" +
				"	float:left;" +
				"	padding-left:15px;}" +
				".heading p{" +
				"	font-family: arial, sans-serif;" +
				"	color: #3366cc;" +
				"	font-weight: bold;" +
				"	font-size: 20px;" +
				"	padding-left:15px;" +
				"	vertical-align:middle;}" +
				".content{" +
				"	padding-top:20px;" +
				"	font-family: \"Arial\", Verdana;" +
				"	font-size: 18px;}" +
				"a{" +
				"font-size:18px;" +
				"color: #15c;" +
				"text-decoration: none;}" +
				"a:active {" +
				"color: #d14836;}" +
				"a:hover {" +
				"text-decoration: underline;}" +
				"</style></head>";
		htmlText += style;
		activationKey = Base64Utils.toBase64((activationKey+ServerConstants.getConfirmationKey()).getBytes());
		String body = "<body><div class=\"headimg\"><a href=\"http://cricket.thinqq.com\"><img src=\"cid:logo\"/>ThinqQ Cricket</a>" +
				"</div><div class=\"heading\"><p>Welcome to ThinqQ Cricket</p></div><br/>" +
				"<div><p class=\"content\"><br/>" +
				"Thank you for registering with cricket.thinqq.com<br/>" +
				"You can login using your registered email id " + toAddress + "<br/><br/>" +
				"Please click the link below to confirm yourself<br/><br/></p></div>" +
				"<div><a href=\"http://"+registrationRequest.getServerName()+"?email="+ toAddress +"&confirmationKey="+activationKey+"#ConfirmationPlace:\">Confirm your email and activate your account</a></div><body>";
		htmlText += body + "</html>";
System.out.print(htmlText);
		Message message = new MimeMessage(session);
		try {
			message.setSubject("Welcome to ThinqQ Cricket");
			message.setFrom(new InternetAddress("administrator@thinqq.com"));
			message.addRecipient(Message.RecipientType.TO, 
					new InternetAddress(toAddress));
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(htmlText, "text/html");

			MimeMultipart multipart = new MimeMultipart("related");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource(img);
			messageBodyPart.setFileName(img);
			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID","<logo>");

			multipart.addBodyPart(messageBodyPart);			
			message.setContent(multipart);
			Transport.send(message);
			return true;
		} catch (AddressException e) {
			logger.log(Level.SEVERE, "Confirmation mail not sent", e);
		} catch (MessagingException e) {
			logger.log(Level.SEVERE, "Confirmation mail not sent", e);
		}
		return false;

	}
	
	public ConfirmationResponseVo confirmUser(ConfirmationRequestVo request, HttpServletRequest httpRequest){
		ConfirmationResponseVo response = new ConfirmationResponseVo();
		/*String email = request.getEmail();
		String confirmationKey = request.getConfirmationKey();
		if(email != null && confirmationKey!=null){
			PersistenceManager pm = DAO.getPersistenceManager();
			UserDAO userDAO = new UserDAO();
			User user =userDAO.findUserByEmail(request.getEmail(), pm);
			if(user!=null && !UserStatusEnum.UNCONFIRMED.name().equals(user.getStatus())){
				String encodedActivationKey = Base64Utils.toBase64((user.getConfirmationCode()+ServerConstants.getConfirmationKey()).getBytes());
				if(confirmationKey.equals(encodedActivationKey)){
					user.setStatus(UserStatusEnum.CONFIRMED.name());
					pm.currentTransaction().begin();
					userDAO.update(user, pm);
					pm.currentTransaction().commit();
					//On Successful confirmation set the user key to session
					response.setConfirmationSuccessful(true);
					response.setSignedInUserId(Long.toString(user.getKey().getId()));
					httpRequest.getSession().setAttribute(ServerConstants.SESSION_USER_KEY, user.getKey());
					//Create Cricket Profile Object
					CricketProfile cricketProfile = new CricketProfile();
					Date date = Calendar.getInstance().getTime();
					FieldingStatistics fielding = new FieldingStatistics();
					fielding.setCatches(0);
					fielding.setStumpings(0);
					fielding.setCreatedTime(date);
					fielding.setCreatedUserKey(user.getKey());
					fielding.setUpdatedTime(date);
					fielding.setUpdatedUserKey(user.getKey());
					


					BattingStatistics batting = new BattingStatistics();
					batting.setBallsFaced(0);
					batting.setFifties(0);
					batting.setFours(0);
					batting.setHighScore(0);
					batting.setHundreds(0);
					batting.setInnings(0);
					batting.setNotouts(0);
					batting.setRuns(0);
					batting.setSixes(0);
					batting.setThirties(0);
					batting.setCreatedTime(date);
					batting.setCreatedUserKey(user.getKey());
					batting.setUpdatedTime(date);
					batting.setUpdatedUserKey(user.getKey());

					BowlingStatistics bowling = new BowlingStatistics();
					bowling.setBalls(0);
					bowling.setFiveWickets(0);
					bowling.setFourWickets(0);
					bowling.setInnings(0);
					bowling.setRuns(0);
					bowling.setTenWickets(0);
					bowling.setThreeWickets(0);
					bowling.setWickets(0);

					FieldingStatisticsDAO fieldingDAO = new FieldingStatisticsDAO();
					BattingStatisticsDAO battingDAO = new BattingStatisticsDAO();
					BowlingStatisticsDAO bowlingDAO = new BowlingStatisticsDAO();
					CricketProfileDAO cricketProfileDAO = new CricketProfileDAO();
					
					
					pm.currentTransaction().begin();
					cricketProfileDAO.insert(cricketProfile, pm);
					pm.currentTransaction().commit();
					
					fielding.setCricketProfile(cricketProfile.getKey());
					pm.currentTransaction().begin();
					fieldingDAO.insert(fielding, pm);
					pm.currentTransaction().commit();
					
					batting.setCricketProfile(cricketProfile.getKey());
					pm.currentTransaction().begin();
					battingDAO.insert(batting, pm);
					pm.currentTransaction().commit();
					
					bowling.setCricketProfile(cricketProfile.getKey());
					pm.currentTransaction().begin();
					bowlingDAO.insert(bowling, pm);
					pm.currentTransaction().commit();
					
				//	cricketProfile.setFieldingStat(fielding.getKey());
					//cricketProfile.setBattingStat(batting.getKey());
					//cricketProfile.setBowlingStatistics(bowling.getKey());
					//cricketProfile.setUser(user.getKey());
					pm.currentTransaction().begin();
					cricketProfileDAO.insert(cricketProfile, pm);
					pm.currentTransaction().commit();
					
				}

			}
			pm.close();
		}else{
			response.setConfirmationSuccessful(false);
		}
*/
		return response;
	}
/*	public SigninResponseVo signInUser(SigninRequestVo request, HttpServletRequest httpRequest){
		SigninResponseVo response = new SigninResponseVo();
		String email = request.getUserEmail();
		String password = request.getPassword();
		if(email != null && password!=null){
			PersistenceManager pm = DAO.getPersistenceManager();
			UserDAO userDAO = new UserDAO();
			User user =userDAO.findUserByEmail(request.getUserEmail(), pm);
			if(user!=null && user.getPassword().equals(password)){
				if(UserStatusEnum.CONFIRMED.name().equals(user.getStatus())){
					httpRequest.getSession().setAttribute(ServerConstants.SESSION_USER_KEY, user.getKey());
					response.setInvalidEmailPassword(false);
					response.setUserDeactivated(false);
					response.setSignedInUserId(Long.toString(user.getKey().getId()));
				}else{
					response.setInvalidEmailPassword(false);
					response.setUserDeactivated(true);
				}
			}else{
				response.setInvalidEmailPassword(true);
			}
			pm.close();
		}else{
			response.setInvalidEmailPassword(true);
		}
		return response;
		
	}*/
}
