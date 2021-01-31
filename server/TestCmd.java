import java.io.IOException;
import java.net.Socket;

public class TestCmd {
    public static void main(String[] args){
        new Thread(TestCmd::start).start();
    }

    public static void start(){
        // 建立连接
        try {
            Socket socket = new Socket("192.168.226.1", 9959);
            socket.getOutputStream().write("open".getBytes("UTF-8"));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
