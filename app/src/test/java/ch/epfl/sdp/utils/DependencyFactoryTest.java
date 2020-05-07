package ch.epfl.sdp.utils;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import ch.epfl.sdp.database.firebase.api.ClientMockDatabaseAPI;

import static org.junit.Assert.*;

public class DependencyFactoryTest {
    @Test
    public void testGetAndSet() {
        DependencyFactory.setTestMode(true);
        assertEquals(true, DependencyFactory.isTestMode());

        ClientMockDatabaseAPI clientMockDatabaseAPI = new ClientMockDatabaseAPI();
        DependencyFactory.setClientDatabaseAPI(clientMockDatabaseAPI);
        assertEquals(clientMockDatabaseAPI, DependencyFactory.getClientDatabaseAPI());
    }

    @After
    public void after() {
        DependencyFactory.setTestMode(false);
        DependencyFactory.setClientDatabaseAPI(null);
    }
}
