# open_cumputer
利用esp8266+舵机实现远程开机，包含服务器端代码实现内网穿透以及安卓端开机APP代码<br>
├─android  安卓端开机APP <br>
├─server   服务端代码，转发APP的开机请求给esp8266 <br>
└─esp8266  单片机代码，接收服务器发来的消息并执行开机操作 <br>
![image](images/structure.png)
