import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.*;

public class Clio {

    public static final String FILE_PATH = "clio.txt";
    private final Path filePath = Paths.get(FILE_PATH);

    public void processCommand(String input) throws Exception{
        String[] arr = deserialize(input);
        if(arr.length > 2){
            throw new Exception("Invalid command declaration");
        }
        String cmd = arr[0];
        String dir = arr[1];
        switch (cmd){
            case "list" :
                listDir();
                break;
            case "add":
                addDir(dir);
                break;
            case "rem":
                break;
            default:
                throw new IllegalStateException("Invalid command: " + cmd);
        }
    }

    private String[] deserialize(String input){
        return input.split(" ");
    }

    private void addDir(String dir){
        String directory = dir + "\n";
        try(FileChannel fc = FileChannel.open(filePath, CREATE, WRITE)){
            long lastPosition = fc.size();
            fc.position(lastPosition);
            if (lastPosition < 0){
                fc.position(0);
            }

            ByteBuffer buffer = ByteBuffer.wrap(directory.getBytes());
            int bytesWritten = fc.write(buffer);
            System.out.println("bytes written:" + bytesWritten );
        }catch (IOException e){
            System.err.println("Problem occurred  adding dir" );
            System.out.println(e);
        }
    }
    private void listDir(){
        try(FileChannel fc = FileChannel.open(filePath, READ)){
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            fc.read(buffer);

            buffer.flip();
            int bufferSize = buffer.remaining();
            byte[] bytes = new byte[bufferSize];

            buffer.get(bytes); //transfer the data from buffer to the byte array

            String readData = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(readData);
        }catch (IOException e){
            System.err.println("Problem listing saved the directories");
            System.out.println(e);
        }
    }
}
