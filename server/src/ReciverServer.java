import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ReciverServer {

    public int port;
    public volatile CmpServer opener;

    public void start(int port, CmpServer opener){
        this.port=port;
        this.opener=opener;
        new Thread(this::start_server).start();
    }

    public void start_server() {
        try {

            //监听端口号
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Accepting connections on port "+ InetAddress.getLocalHost()+":"+ ss.getLocalPort());

            while (true) {
                //实例化客户端，固定套路，通过服务端接受的对象，生成相应的客户端实例
                Socket socket = ss.accept();
                //获取客户端输入流
                InputStream input=socket.getInputStream();

                byte[] bys=new byte[4];
                input.read(bys);

                String cmd=new String(bys,"UTF-8");
                if(cmd.equals("open")){
                    if(opener.sendCmd("open")){
                        socket.getOutputStream().write("opok".getBytes("UTF-8"));
                    }
                }

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}