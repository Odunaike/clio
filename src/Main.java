import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Clio clio = new Clio();
        Scanner sys = new Scanner(System.in);

        System.out.print("Welcome to Clio v1.0");
        System.out.println("********************************");
        System.out.println("Type commands to see more");        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text// to see how IntelliJ IDEA suggests fixing it.
        while (true) {
            String command = sys.nextLine();
            try{
                clio.processCommand(command);
            }catch (Exception e){
                System.err.println(e.getMessage());
            }

        }

    }
}