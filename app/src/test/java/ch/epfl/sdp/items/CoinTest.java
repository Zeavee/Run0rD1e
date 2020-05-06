package ch.epfl.sdp.items;

import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Coin;
import ch.epfl.sdp.item.Item;

import static org.junit.Assert.assertTrue;

public class CoinTest {

    private Player originalPlayer = PlayerManager.getInstance().getCurrentUser();

    @Test
    public void clonedCoinHasSameValue(){
        Coin dime = new Coin(10);
        Item clonedDime = dime.clone();
        assertTrue(dime.getValue() == ((Coin)clonedDime).getValue());
    }

    @Test
    public void userBankIncreasesWhenCoinUsed(){
        Player broke = new Player(20.0, 20.0, 100, "amroa", "amro.abdrabo@gmail.com");
        broke.removeMoney(broke.getMoney());
        PlayerManager.getInstance().setCurrentUser(broke);
        Coin dime  = new Coin(10);
        dime.useOn(broke);
        assertTrue(broke.getMoney() == dime.getValue());
    }

}
