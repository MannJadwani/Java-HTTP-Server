import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class main {
    public static void main(String[] args) throws Exception{
        try(ServerSocket serverSocket=new ServerSocket(8080)){
            System.out.println("Server has started");
            while(true){
                try(Socket client =serverSocket.accept()){
                    System.out.println("this is the client "+client.toString());
                    InputStreamReader isr = new InputStreamReader(client.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder request = new StringBuilder();
                    String line;
                    line  = br.readLine();
                    while (!line.isBlank()){
                        request.append(line+"\r\n");
                        line=br.readLine();
                    }

                    String firstLine=request.toString().split("\n")[0];
                    String resource = firstLine.split(" ")[1];
                    System.out.println(resource);

                    if (Objects.equals(resource, "/")){
                        OutputStream clientOutput = client.getOutputStream();

                    clientOutput.write((" HTTP/1.1 200 OK\r\n").getBytes());
                    clientOutput.write(("\r\n").getBytes());
                    clientOutput.write(("Mann Jadwani\r\n").getBytes());
                    }else if(Objects.equals(resource,"/mann")){
                        FileInputStream image = new FileInputStream("mann.jpg");
                    System.out.println(image.toString());

                    OutputStream clientOutput = client.getOutputStream();
                    clientOutput.write((" HTTP/1.1 200 OK\r\n").getBytes());
                    clientOutput.write(("\r\n").getBytes());
                    clientOutput.write(image.readAllBytes());
                    image.close();
                    }else{
                        OutputStream clientOutput = client.getOutputStream();

                        clientOutput.write((" HTTP/1.1 200 OK\r\n").getBytes());
                        clientOutput.write(("\r\n").getBytes());
                        clientOutput.write(("Error 404\r\n").getBytes());
                    }



                    //Send my image

//                    FileInputStream image = new FileInputStream("mann.jpg");
//                    System.out.println(image.toString());

//                    OutputStream clientOutput = client.getOutputStream();
//                    clientOutput.write((" HTTP/1.1 200 OK\r\n").getBytes());
//                    clientOutput.write(("\r\n").getBytes());
//                    clientOutput.write(image.readAllBytes());
//                    image.close();

//                    clientOutput.flush();
                }
            }
        }
    }
}
