import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVLoader {

    public static void load(BinarySearchTree tree, String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String nickname = parts[0].trim();
                    int ranking = Integer.parseInt(parts[1].trim());
                    tree.insert(new Player(nickname, ranking));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
    }
}