import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.*;

public class Clio {

    public static final String FILE_PATH = "clio.txt";
    Path filePath = Paths.get(FILE_PATH);

    public void processCommand(String input) throws Exception{
        String[] arr = deserialize(input);
        if(arr.length > 2){
            throw new Exception("Invalid command declaration");
        }
        String cmd = arr[0];
        String dir = arr[1];
        switch (cmd){
            case "list" :

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
            System.err.println("Something occurred when adding dir\n" + e);
        }
    }
}
