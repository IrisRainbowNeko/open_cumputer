import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class CmpServer {

    public volatile ArrayList<Socket> sc_list=new ArrayList<Socket>();
    public int port;

    public void start(int port){
        this.port=port;
        new Thread(this::start_server).start();
    }

    public void start_server() {
        try {

            //监听端口号
            ServerSocket ss = new ServerSocket(port);

            while (true) {
                //实例化客户端，固定套路，通过服务端接受的对象，生成相应的客户端实例
                Socket socket = ss.accept();

                sc_list.add(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendCmd(String cmd){
        ArrayList<Socket> sc_rm=new ArrayList<Socket>(); //删除断开连接的
        for (Socket socket : sc_list) {
            if(!isServerClose(socket)) {
                try {
                    socket.getOutputStream().write(cmd.getBytes("UTF-8"));

                    byte[] bys=new byte[4];
                    socket.getInputStream().read(bys);
                    String feed_back=new String(bys,"UTF-8");
                    if(feed_back.equals("opok")){
                        return true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                sc_rm.add(socket);
            }
        }

        sc_list.removeAll(sc_rm);
        return false;
    }

    public Boolean isServerClose(Socket socket){
        try{
            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        }catch(Exception se){
            return true;
        }
    }
}