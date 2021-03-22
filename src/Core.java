import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.IOException;
import java.io.*;

public class Core {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filepath = "src/data.txt";

        System.out.println("Planer");

        while (true){
            System.out.println(
                    "[1] Neuer Eintrag \n" +
                    "[2] Eintrag löschen \n" +
                    "[3] Einträge anzeigen \n" +
                    "[4] Beenden");
            int option = scanner.nextInt();
            switch (option){
                case 1:
                    // Neuer Eintrag
                    Scanner input = new Scanner(System.in);
                    try(BufferedWriter bw = new BufferedWriter(new FileWriter (filepath, true))){
                        System.out.println("Neuer Eintrag [TT.MM.JJJJ: Name]\n");
                        String s = input.nextLine();
                        bw.write(s + "\n");
                        bw.close();
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    // Eintrag löschen
                    Scanner input2 = new Scanner(System.in);        //Kann man den selben Scanner aus case 1 nutzen?
                    System.out.println("Zu löschender Eintrag: ");
                    String toRemove = input2.nextLine();            //Zu löschender Eintrag
                    try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/data_copy.txt", true))){   //Kopie anlegen
                        BufferedReader br = new BufferedReader(new FileReader("src/data.txt"));
                        String line;
                        while((line = br.readLine()) != null){      //Original auslesen
                            if(line.equals(toRemove)){              //Wenn der zu löschende Eintrage gefunden wurde, diesen nicht in die Kopie
                                continue;                           //schreiben, sondern in die nächste Zeile übergehen
                            }
                            bw.write(line + "\n");              //Zeile in die Kopie schreiben
                        }
                        bw.close();                                 //IO-Objekte schließen
                        br.close();
                        String oldFilePath = "src/data_copy.txt";
                        Files.delete(Paths.get(filepath));          //Original löschen
                        try{
                            Files.move(Paths.get(oldFilePath), Paths.get(filepath));    //Kopie in Original umbenennen
                        }catch (IOException e){
                            e.getMessage();
                        }
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    // Einträge anzeigen
                    try(BufferedReader br = new BufferedReader(new FileReader(filepath))){
                        String line;
                        while ((line = br.readLine()) != null){
                            System.out.println(line);
                        }
                        br.close();
                    }catch(IOException e){
                        e.getMessage();
                    }
                    break;
                case 4:
                    // Beenden
                    break;
            }
            if(option==4) {
                break;
            }
        }

        try(Scanner fileScanner = new Scanner(Paths.get(filepath))){

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
