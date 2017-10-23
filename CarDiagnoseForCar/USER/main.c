#include "sys.h"
#include "delay.h"  
#include "usart.h" 
#include "usart3.h"
#include "usart2.h"   
#include "qcqyf.h"
#include "string.h"
#include "exti.h"
#include "led.h"

			//��ȡobd��������
			//mode:0:��obdģ���ȡ
			//		 1:�����ȡ
u8 MODE=0;
int main(void)
{
	u8* get_data;
	u8 res;
	u8* p;
	u8* command_data;
	u8* car_data;
	
	NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2);//����ϵͳ�ж����ȼ�����2
	delay_init(168);  //��ʼ����ʱ����
	LED_Init();
	EXTIX_Init();
	uart_init(38400);		//��ʼ�����ڲ�����Ϊ115200
	usart3_init(38400);		//��ʼ������3 	
	usart2_init(38400);		//��ʼ������3 


	printf("%s\r\n","start-----------------------------------------start");
	
	while(sim900a_send_cmd("AT","OK",500))//����Ƿ�Ӧ��ATָ�� 
	{
		printf("%s\r\n","not found sim900A");
		delay_ms(500);
	} 
	
	sim900a_send_cmd("ATE0","OK",200);//������
	
	while(Mysim900a_init())
	{
		printf("%s\r\n","try to connect sim900A");
		delay_ms(500);
	}
delay_ms(500);
	
	//���ͱ���carID
while(Post_data((u8 *)CAR_ID)||(strstr((const char*)USART3_RX_BUF,(const char*)"OK"))==NULL)
	{
			printf("%s\r\n","try to send carID");
		  delay_ms(500);
	}
	
  while(1){
		
			//��������ݣ����������Ƿ�����������
		res = Post_data((u8 *)POST_DATA);
		printf("res:%d...\r\n",res);	
		
		if(res == 0)
			{
				//��ȡ���������ص�����(USART3_RX_BUF)	
				p = USART3_RX_BUF;
				if(strstr((const char*)p,(const char*)GET_COMMAND))
				{
						//�õ���Ч����
				get_data = gets_data(p);	
				if(strstr((const char*)get_data,(const char*)HAVE))//�ϴ�����
					{
						//��ȡ��������
					command_data =	gets_command_data(get_data);
					
						//��ȡobd��������
						//mode:0:��obdģ���ȡ
						//		 1:�����ȡ
					car_data=get_car_data(command_data,MODE);
						if( !strstr((const char*)car_data,(const char*)_NULL_))
						{
							//�����Ч��obd�������ݣ��ϴ���������
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
			sim900a_send_cmd("ATE0","OK",200);//������
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
