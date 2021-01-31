public class Main {
    public static void main(String[] args){
        CmpServer cs=new CmpServer();
        cs.start(9949);

        new ReciverServer().start(9959, cs);
        while (true);
    }
}
