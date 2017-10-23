#include "qcqyf.h"
#include "sys.h"
#include "string.h"
#include "led.h"
#include "usart3.h" 
#include "usart2.h" 
#include "usart.h"
#include "delay.h"	

u8 position = 0;
u8 get_data[50];

u8* gets_data(u8* data)
	{
		u8 *p;
		u8 i=0;
		memset(get_data,0,50);
		p=(u8*)strstr((const char*)data,"get_command:");
		p = p+12;
		while(*p != '\n')
		{
		get_data[i]=*p;
		i++;
		p++;		
		}
		return (u8*)get_data;
	}
	
u8* gets_command_data(u8* data)
	{
		u8 *p;
		p=(u8*)strstr((const char*)data,"have:");
		printf("the phone's request is:%s--------------\r\n",p);
		return p+5;
		
	}
	
	
u8 obd_send_cmd(u8 *cmd,u8 *ack,u16 waittime)
{
	u8 res=0; 
	USART2_RX_STA=0;
	if((u32)cmd<=0XFF)
	{
		while((USART2->SR&0X40)==0);//等待上一次数据发送完成  
		USART2->DR=(u32)cmd;
	}else u2_printf("%s\r\n",cmd);//发送命令
	if(ack&&waittime)		//需要等待应答
	{
		while(--waittime)	//等待倒计时
		{
			delay_ms(10);
			if(USART2_RX_STA&0X8000)//接收到期待的应答结果
			{
				if(obd_check_cmd(ack))break;//得到有效数据 
				USART2_RX_STA=0;
			} 
		}
		if(waittime==0)res=1; 
	}
	return res;
} 

u8* obd_check_cmd(u8 *str)
{
	char *strx=0;
	if(USART2_RX_STA&0X8000)		//接收到一次数据了
	{ 
		USART2_RX_BUF[USART2_RX_STA&0X7FFF]=0;//添加结束符
		strx=strstr((const char*)USART2_RX_BUF,(const char*)str);
		printf("%s \r\n",USART2_RX_BUF);
	} 
	return (u8*)strx;
}


//GSM信息显示(信号质量,电池电量,日期时间)
//返回值:0,正常
//    其他,错误代码
u8 Mysim900a_init(void)
{
	u8 res=0;	
	
	if(sim900a_send_cmd("AT+CPIN?","OK",1000))res|=1<<0;	//查询SIM卡是否在位 
	if(sim900a_send_cmd("AT+COPS?","OK",1000))res|=1<<1;		//查询运营商名字		
	if(sim900a_send_cmd("AT+CSQ","+CSQ:",1000))res|=1<<2;		//查询信号质量
	//关闭http服务
	sim900a_send_cmd("AT+HTTPTERM","OK",100);
	//关闭gprs上下文
	sim900a_send_cmd("AT+SAPBR=0,1","OK",100);
	
	if(sim900a_send_cmd("AT+HTTPINIT","OK",1000))res|=1<<3;//Init http service				
	if(sim900a_send_cmd("AT+HTTPPARA=\"CID\",1","OK",1000))res|=1<<4;//Set parameters for HTTP session
	if(sim900a_send_cmd("AT+SAPBR=1,1","OK",1500))res|=1<<5;//To open a GPRS context.
	if(sim900a_send_cmd("AT+HTTPPARA=\"URL\",\"http://120.77.38.19:8080/CarDiagnoseServer/CarServer\"","OK",1000))res|=1<<6;
	return res;
} 

u8 Post_data(u8* data)
{
	
	char buffer[50];
	u8 len = strlen((const char*)data);

	//格式化发送数据长度
	sprintf(buffer,"AT+HTTPDATA=%d,2000",len);
	if(sim900a_send_cmd((u8*)buffer,"DOWNLOAD",1500))return 1;
	//传输数据
	if(sim900a_send_cmd(data,"OK",500))return 2;
	if(sim900a_send_cmd("AT+HTTPACTION=1","200",1500))return 3;
	if(sim900a_send_cmd("AT+HTTPREAD","OK",1500))return 4;
	
	return 0;
}


const u8* command_list[] = {"AT BDAT","AT DRON","AT DTC","AT FCDTC","AT ADTC"};
u8* command_back[] = {"$OBD-BASE DR DAT$","$OBD-DR$","RDTC","CLEAR DTC","$OBD-FREEEZ DAT$"};
u8* command_back_string[] = {"$OBD-BASE DR DAT$VBAT:11.2;RPM:4937;SPD:24;TP:6.6;LOD:14.1;ECT:-23;FLI:6.6;MPH: 1.388;9.19",\
															">$OBD-DR$MAXRPM:4945;MINRPM:4937;MAXSPD:24;AVGSPD:24;MAXACL:0.0;MILE-T:0.0;FUEL-T:0.037;MILES:0.0;FUELS:0.037;TIMES:0.0;STARTS:0;BRAKES:0;RACLS:0;POWER:1",\
															"RDTC:2,&P0023&P0126",\
															"EDTC:1,&P2029",\
															"$OBD-FREEEZ DAT$ NONE"};
u8* return_command_back_head(u8* command_data)
{
u8* back="null";
u8 len,i;
len = sizeof(command_list)/sizeof(0);

for(i=0;i<len;i++)
{
if(strstr((const char*)command_data,(const char*)command_list[i]))
{
//找到了
back=command_back[i];
	position = i;
	break;

}
}
printf("the back _data is: %s \r\n",back);

return back;


}
u8* get_car_data(u8* command,u8 mode)
{	
	u8* back_head;
	u8* obd_back_data="null";
	back_head = return_command_back_head(command);
	
	if(!strstr((const char*)back_head,(const char*)"null"))
	{
		
		printf("%s\r\n","wait to obd back data.........");
		if(mode)//仿真读取数据
		obd_back_data=send_command_toOBD_simulation(command,back_head);
		else 
		{
			USART_ITConfig(USART2, USART_IT_RXNE, ENABLE);//开启中断 
			obd_back_data=send_command_to_OBD(command,back_head);
			USART_ITConfig(USART2, USART_IT_RXNE, DISABLE);
		}
		
	}
	
	return obd_back_data;
}


u8* send_command_to_OBD(u8* command, u8* back_head)
{
	u8* obd_back_data;
	//改变获取模式
	if(strstr((const char*)command,(const char*)"DRON"))
		command = "AT DRON2";
	 if(obd_send_cmd(command,back_head,1000)==0)
	 {
	//获取obd返回的数据(USART2_RX_BUF)	
			obd_back_data = USART2_RX_BUF;
		 return obd_back_data;
		}
	 else printf("%s\r\n","bad obd back data.........");
		return "null";
}

u8* send_command_toOBD_simulation(u8* command, u8* back_head)
{
	return command_back_string[position];
}

//向sim900a发送命令
//cmd:发送的命令字符串(不需要添加回车了),当cmd<0XFF的时候,发送数字(比如发送0X1A),大于的时候发送字符串.
//ack:期待的应答结果,如果为空,则表示不需要等待应答
//waittime:等待时间(单位:10ms)
//返回值:0,发送成功(得到了期待的应答结果)
//       1,发送失败
u8 sim900a_send_cmd(u8 *cmd,u8 *ack,u16 waittime)
{
	u8 res=0; 
	USART3_RX_STA=0;
	if((u32)cmd<=0XFF)
	{
		while((USART3->SR&0X40)==0);//等待上一次数据发送完成  
		USART3->DR=(u32)cmd;
	}else {u3_printf("%s\r\n",cmd);
		printf("%s\r\n",cmd);
	}//发送命令
	if(ack&&waittime)		//需要等待应答
	{
		while(--waittime)	//等待倒计时
		{
			
			if(USART3_RX_STA&0X8000)//接收到期待的应答结果
			{
				if(sim900a_check_cmd(ack))break;//得到有效数据 
				USART3_RX_STA=0;
			} 
			delay_ms(10);
		}
		if(waittime==0)res=1; 
	}
	return res;
} 

/////////////////////////////////////////////////////////////////////////////////////////////////////////// 
//ATK-SIM900A 各项测试(拨号测试、短信测试、GPRS测试)共用代码

//sim900a发送命令后,检测接收到的应答
//str:期待的应答结果
//返回值:0,没有得到期待的应答结果
//    其他,期待应答结果的位置(str的位置)
u8* sim900a_check_cmd(u8 *str)
{
	char *strx=0;
	if(USART3_RX_STA&0X8000)		//接收到一次数据了
	{ 
		USART3_RX_BUF[USART3_RX_STA&0X7FFF]=0;//添加结束符
		strx=strstr((const char*)USART3_RX_BUF,(const char*)str);
		printf("%s\r\n",USART3_RX_BUF);
	} 
	return (u8*)strx;
}

