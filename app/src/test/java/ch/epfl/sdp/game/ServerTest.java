//package ch.epfl.sdp.game;
//
//import org.junit.Test;
//
//import ch.epfl.sdp.artificial_intelligence.EnemyGenerator;
//import ch.epfl.sdp.artificial_intelligence.EnemyManager;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//public class ServerTest {
//    @Test
//    public void update_shouldGenerateRandomEnemy() {
//        EnemyManager enemyManagerMock = mock(EnemyManager.class);
//        EnemyGenerator enemyGeneratorMock = mock(EnemyGenerator.class);
//
//        Server server = new Server(enemyManagerMock, enemyGeneratorMock, 50);
//        server.update();
//        verify(enemyManagerMock, times(0)).addEnemy(any());
//        try {
//            Thread.sleep(60);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        server.update();
//        verify(enemyManagerMock, times(1)).addEnemy(any());
//    }
//
//    @Test
//    public void update_shouldNotGenerateRandomEnemy_WhenTimeNotUp() {
//        EnemyManager enemyManagerMock = mock(EnemyManager.class);
//        EnemyGenerator enemyGeneratorMock = mock(EnemyGenerator.class);
//
//        Server server = new Server(enemyManagerMock, enemyGeneratorMock, 10000);
//        server.update();
//        verify(enemyManagerMock, times(0)).addEnemy(any());
//        server.update();
//        verify(enemyManagerMock, times(0)).addEnemy(any());
//    }
//}
