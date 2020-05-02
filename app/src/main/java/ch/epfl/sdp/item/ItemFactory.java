package ch.epfl.sdp.item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemFactory {
    public Item getItem(String itemName){
    String[] parts = itemName.split(" ");

    switch (parts[0]){
        case "Healthpack": return new Healthpack(Integer.parseInt(parts[1]));
        case "Shield": return new Shield(Integer.parseInt(parts[1]));
        case "Shrinker": return new Shrinker(Integer.parseInt(parts[1]),Double.parseDouble(parts[2]));
        case "Scan": return new Scan(Integer.parseInt(parts[2]));
    }

        return null;
    }
}
