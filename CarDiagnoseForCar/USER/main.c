#include "sys.h"
#include "delay.h"  
#include "usart.h" 
#include "usart3.h"
#include "usart2.h"   
#include "qcqyf.h"
#include "string.h"
#include "exti.h"
#include "led.h"

			//获取obd返回数据
			//mode:0:从obd模块读取
			//		 1:仿真读取
u8 MODE=0;
int main(void)
{
	u8* get_data;
	u8 res;
	u8* p;
	u8* command_data;
	u8* car_data;
	
	NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2);//设置系统中断优先级分组2
	delay_init(168);  //初始化延时函数
	LED_Init();
	EXTIX_Init();
	uart_init(38400);		//初始化串口波特率为115200
	usart3_init(38400);		//初始化串口3 	
	usart2_init(38400);		//初始化串口3 


	printf("%s\r\n","start-----------------------------------------start");
	
	while(sim900a_send_cmd("AT","OK",500))//检测是否应答AT指令 
	{
		printf("%s\r\n","not found sim900A");
		delay_ms(500);
	} 
	
	sim900a_send_cmd("ATE0","OK",200);//不回显
	
	while(Mysim900a_init())
	{
		printf("%s\r\n","try to connect sim900A");
		delay_ms(500);
	}
delay_ms(500);
	
	//发送本地carID
while(Post_data((u8 *)CAR_ID)||(strstr((const char*)USART3_RX_BUF,(const char*)"OK"))==NULL)
	{
			printf("%s\r\n","try to send carID");
		  delay_ms(500);
	}
	
  while(1){
		
			//传输的数据，检测服务器是否有数据请求
		res = Post_data((u8 *)POST_DATA);
		printf("res:%d...\r\n",res);	
		
		if(res == 0)
			{
				//获取服务器返回的数据(USART3_RX_BUF)	
				p = USART3_RX_BUF;
				if(strstr((const char*)p,(const char*)GET_COMMAND))
				{
						//得到有效数据
				get_data = gets_data(p);	
				if(strstr((const char*)get_data,(const char*)HAVE))//上传数据
					{
						//获取命令数据
					command_data =	gets_command_data(get_data);
					
						//获取obd返回数据
						//mode:0:从obd模块读取
						//		 1:仿真读取
					car_data=get_car_data(command_data,MODE);
						if( !strstr((const char*)car_data,(const char*)_NULL_))
						{
							//获得有效的obd返回数据，上传至服务器
								Post_data(car_data);
						}
						else{
							Post_data(car_data);
						}
			
					}
					else printf("%s","no request...\r\n");
				}
					
				}
		else{
			printf("%s\r\n","warnning!!!!!there is an eror!-!-!-!-!-!-!-!-!-");
			sim900a_send_cmd("ATE0","OK",200);//不回显
			while(Mysim900a_init())
			{
				printf("%s\r\n","try to connect sim900A");
				delay_ms(500);
			}
	}
		if(MODE)
			LED0 = !LED0;
			LED1 = !LED1;
	}
}
