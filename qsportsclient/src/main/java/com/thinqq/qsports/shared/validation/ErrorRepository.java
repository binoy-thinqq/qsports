package com.thinqq.qsports.shared.validation;


public interface ErrorRepository {
	public static final ErrorVo invalidUserNamePassword = new ErrorVo(1,"Email and Password given do not match","","");
	public static final ErrorVo unableToProceed = new ErrorVo(2,"Unable to Sign In. Contact administrator@thinqq.com , if the problem persists","","");
	public static final ErrorVo accountLocked = new ErrorVo(3,"Your account is locked. Contact administrator@thinqq.com","","");
	public static final ErrorVo invalidEmailFormat = new ErrorVo(4,"Email given in not valid. Ex:email@domain.com","","");
	public static final ErrorVo emailNotGiven = new ErrorVo(5,"Email is not given","","");
	public static final ErrorVo invalidPasswordFormat = new ErrorVo(6,"Password contains only alphabets, numbers and length between 6 and 30 characters","","");
	public static final ErrorVo passwordNotGiven = new ErrorVo(7,"Password not given","","");
	public static final ErrorVo unableToRegisterError = new ErrorVo(8,"Unable to Register. Try again. If the problem continues contact administrator@thinqq.com","","");
	public static final ErrorVo emailAlreadyRegistered = new ErrorVo(8,"Email is already Registered. Please use a different email","","");
	public static final ErrorVo nameFormatError = new ErrorVo(9,"Name should be of length betwen 2 and 100 characters","","");
	public static final ErrorVo nameNotGiven = new ErrorVo(10,"Name not given","","");
	public static final ErrorVo passwordMisMatch = new ErrorVo(11,"Password and Confirm Password should match","","");
	public static final ErrorVo confirmPasswordNotGiven = new ErrorVo(12,"Confirm Password not given","","");
	public static final ErrorVo sexNotGiven = new ErrorVo(13,"Choose Male/Female","","");
	public static final ErrorVo dobNotGiven = new ErrorVo(14,"Choose your date of birth","","");
	public static final ErrorVo dobFormatError = new ErrorVo(15,"Date of Birth is not given in right format","","");
	public static final ErrorVo cityFormatError = new ErrorVo(16,"City should be of length betwen 2 and 60 characters","","");
	public static final ErrorVo cityNotGiven = new ErrorVo(17,"City not given","","");
	public static final ErrorVo stateFormatError = new ErrorVo(18,"State/Province should be of length betwen 2 and 60 characters","","");
	public static final ErrorVo stateNotGiven = new ErrorVo(19,"State/Province not given","","");
	public static final ErrorVo countryNotGiven = new ErrorVo(20,"Country not given","","");
	public static final ErrorVo handTypeNotGiven = new ErrorVo(21,"Please select the hand used","","");
	public static final ErrorVo battingOrderNotGiven = new ErrorVo(22,"Please select your usual batting order","","");
	public static final ErrorVo bowlingMethodNotGiven = new ErrorVo(23,"Please select your usual bowling style","","");
	public static final ErrorVo fieldingPositionNotGiven = new ErrorVo(24,"Please select your usual fielding position","","");
	public static final ErrorVo descExceedsChars = new ErrorVo(25,"Exceeds 1000 characters limit","","");
	public static final ErrorVo selectAValueError = new ErrorVo(26,"Please select a value","","");
	
	public static final ErrorVo matchAlreadyStarted = new ErrorVo(27,"Match is already started","","");
	public static final ErrorVo min11Players = new ErrorVo(28,"Each team needs a minimum of 11 players for starting the match","","");
	public static final ErrorVo oppsSomethingBroke = new ErrorVo(29,"OOPS! Something broke!","","");
	

}
