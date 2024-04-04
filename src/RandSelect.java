import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RandSelect {
    public static void main(String[] args) throws IOException {
        ArrayList<String> names = new ArrayList<>();
        File file = new File("src/name.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String name;
        while((name = br.readLine()) != null) {
            names.add(name);
        }
        br.close();
        fr.close();

        HashMap<String, Double> weights = new HashMap<>();
        for(String nam : names) {
            weights.put(nam, 12.5);
        }

//        System.out.println(names);
//        System.out.println(weights);

        SelectJfram sjf = new SelectJfram(names, weights);

    }
}