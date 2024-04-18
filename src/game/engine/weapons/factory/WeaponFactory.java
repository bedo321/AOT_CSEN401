package game.engine.weapons.factory;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;

import java.util.*;
import java.io.*;

public class WeaponFactory {
    private final HashMap<Integer, WeaponRegistry> weaponShop;

    public WeaponFactory() throws IOException {
        super();
        weaponShop = DataLoader.readWeaponRegistry();
    }

    public HashMap<Integer, WeaponRegistry> getWeaponShop() {
        return weaponShop;
    }

    public FactoryResponse buyWeapon(int resources, int weaponCode) throws InsufficientResourcesException {
        HashMap<Integer, WeaponRegistry> weaponShop = getWeaponShop();
        WeaponRegistry schematic = weaponShop.get(weaponCode);
        int price = schematic.getPrice();
        if (resources >= price) {
            Weapon weapon = schematic.buildWeapon();
            return new FactoryResponse(weapon,(resources - price));
        }
        else
            throw new InsufficientResourcesException(resources);
    }
    public void addWeaponToShop(int code, int price) {
        WeaponRegistry weaponRegistry = new WeaponRegistry(code,price);
        HashMap<Integer, WeaponRegistry> weaponShop = getWeaponShop();
        weaponShop.put(code,weaponRegistry);
    }
    public void addWeaponToShop(int code, int price,int damage,String name) {
        WeaponRegistry weaponRegistry = new WeaponRegistry(code,price,damage,name);
        HashMap<Integer, WeaponRegistry> weaponShop = getWeaponShop();
        weaponShop.put(code,weaponRegistry);
    }
    public void addWeaponToShop(int code, int price, int damage, String name, int minRange,int maxRange) {
        WeaponRegistry weaponRegistry = new WeaponRegistry(code,price,damage,name,minRange,maxRange);
        HashMap<Integer, WeaponRegistry> weaponShop = getWeaponShop();
        weaponShop.put(code,weaponRegistry);
    }
}


