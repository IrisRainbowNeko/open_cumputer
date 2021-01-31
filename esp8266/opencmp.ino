#include <ESP8266WiFi.h>
#include <Servo.h>

const char* RemoteIp = "ip地址"; //TODO: 设置远程主机Ip地址
const int RemotePort = 端口; //TODO: 设置远程主机端口号

WiFiClient client;
Servo myservo;

void setup() {
  // init the serial
  Serial.begin(9600);

  //TODO: init the WiFi connection
  WiFi.begin("wifi名称", "wifi密码");
  Serial.print("Connecting...");
  
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }

  Serial.println();
  Serial.println(WiFi.localIP()); // 打印NodeMCU的IP地址
    
  if (!client.connect(RemoteIp, RemotePort)) // 建立tcp连接
  {
    Serial.println("Connected failed!");
    //return;
  }

  myservo.attach(2);
  myservo.write(0);
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    // Attempt to connect
    if (client.connect(RemoteIp, RemotePort)) {
      delay(1);
    } else {
      delay(5000);
    }
  }
}
void loop() {
  if(!client.connected())
    reconnect();
  
  if (client.available())  
  {    
    if(client.read()=='o' && client.read()=='p' && client.read()=='e' && client.read()=='n'){
      Serial.println("open");
      client.print("opok");
      myservo.write(40);
      delay(500);
      myservo.write(0);
      delay(500);
    }
  }
}


