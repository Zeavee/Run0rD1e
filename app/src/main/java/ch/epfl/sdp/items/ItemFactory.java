package ch.epfl.sdp.items;

import ch.epfl.sdp.items.items.Healthpack;
import ch.epfl.sdp.items.items.Phantom;
import ch.epfl.sdp.items.items.Shield;
import ch.epfl.sdp.items.items.Shrinker;

public class ItemFactory {
    public Item getItem(String itemName) {
        String[] parts = itemName.split(" ");

        switch (parts[0]) {
            case "Healthpack":
                return new Healthpack(Integer.parseInt(parts[1]));
            case "Shield":
                return new Shield(Integer.parseInt(parts[1]));
            case "Shrinker":
                return new Shrinker(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            case "Phantom":
                return new Phantom(Integer.parseInt(parts[1]));
        }

        return null;
    }
}
