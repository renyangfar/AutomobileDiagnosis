package com.development.qcqyf.automobilediagnosis.http;

public class ATCommand {
	
	public static final String COMMAND = "command";//发送头
	public static final String STYLE = "style";//请求方式
	public static final String LOGIN = "login";//登录方式请求
	public static final String REGISTER = "register";//注册方式请求
	public static final String CARID	 = "carID";//获取汽车ID

	public static final String AT_BDAT = "AT BDAT";//车辆实时数据流
	public static final String AT_DRON = "AT DRON";//驾驶行为习惯数据流
	public static final String AT_DTC = "AT DTC";//获取当前车辆故障码
	public static final String AT_FCDTC = "AT FCDTC";//强行清除故障码
	public static final String AT_ADTC = "AT ADTC";//分析故障码
	
	public static final String GET_DATA = "get_data:";
	

}
