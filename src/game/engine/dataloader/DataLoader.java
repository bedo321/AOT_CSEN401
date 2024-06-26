package game.engine.dataloader;
import game.engine.exceptions.InvalidCSVFormat;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.WeaponRegistry;

import java.io.*;
import java.util.*;

public class DataLoader {
    private final static String TITANS_FILE_NAME = "titans.csv";
    private final static String WEAPONS_FILE_NAME = "weapons.csv";

    public static HashMap<Integer, TitanRegistry> readTitanRegistry() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(TITANS_FILE_NAME));
        String line;
        HashMap<Integer, TitanRegistry> titanProperties = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] properties = line.split(",");
            if (properties.length != 7)
                throw new InvalidCSVFormat(line);
            int code = Integer.parseInt(properties[0]);
            TitanRegistry titan = new TitanRegistry(code,Integer.parseInt(properties[1]),Integer.parseInt(properties[2]),Integer.parseInt(properties[3]),Integer.parseInt(properties[4]),Integer.parseInt(properties[5]),Integer.parseInt(properties[6]));
            titanProperties.put(code,titan);
        }
        br.close();
        return titanProperties;
    }

    public static HashMap<Integer, WeaponRegistry> readWeaponRegistry() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(WEAPONS_FILE_NAME));
        String line;
        HashMap<Integer, WeaponRegistry> weaponProperties = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] properties = line.split(",");
            int code = Integer.parseInt(properties[0]);
            if (properties.length != 6 && properties.length != 4)
                throw new InvalidCSVFormat(line);
            WeaponRegistry weapon;
            if (properties.length == 4)
                weapon = new WeaponRegistry(code, Integer.parseInt(properties[1]), Integer.parseInt(properties[2]), properties[3]);
            else
                weapon = new WeaponRegistry(code, Integer.parseInt(properties[1]), Integer.parseInt(properties[2]), properties[3], Integer.parseInt(properties[4]), Integer.parseInt(properties[5]));
            weaponProperties.put(code,weapon);
        }
        br.close();
        return weaponProperties;
    }
}
