import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TestOpen {
    public static void main(String[] args){
        new Thread(TestOpen::start).start();
    }

    public static void start(){
        // 建立连接
        try {
            Socket socket = new Socket("192.168.226.1", 9949);
            InputStream input=socket.getInputStream();
            while (true){
                byte[] bys=new byte[4];
                input.read(bys);

                String cmd=new String(bys,"UTF-8");
                System.out.println(cmd);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
