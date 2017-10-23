#ifndef __QCQYF_H
#define __QCQYF_H	 
#include "sys.h"



#define CAR_ID "carID:15296002587"
#define POST_DATA "visit"
#define _NO_ "no"
#define _NULL_ "null"
#define HAVE "have"
#define VISIT "visit"
#define GET_COMMAND "get_command:"

u8* gets_data(u8* data);
u8* gets_data2(u8* data);
u8 Mysim900a_init(void);
u8 Post_data(u8* data);
u8* gets_command_data(u8* data);
u8* get_car_data(u8* command,u8 mode);
u8* obd_check_cmd(u8 *str);
u8 obd_send_cmd(u8 *cmd,u8 *ack,u16 waittime);
u8* return_command_back_head(u8* command);
u8* send_command_to_OBD(u8* command, u8* back_head);
u8* send_command_toOBD_simulation(u8* command, u8* back_head);
u8 sim900a_send_cmd(u8 *cmd,u8 *ack,u16 waittime);
u8* sim900a_check_cmd(u8 *str);
#endif
