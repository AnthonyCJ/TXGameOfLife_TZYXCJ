package test.tx.gameOfLife.entity; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Ignore;
import tx.gameOfLife.entity.GameMap;

/** 
* GameMap Tester. 
* 
* @author <Authors name> 
* @since <pre>5ÔÂ 18, 2021</pre> 
* @version 1.0 
*/ 
public class GameMapTest {
    private int temp1 [][] = {{1, 1, 1, 0, 0}, {1, 0, 0, 0, 0}, {0, 1, 1, 1, 0}, {0, 0, 0, 0, 0},{0, 0, 0, 0, 0}};
    private int temp2 [][] = {{1, 1, 0, 0, 0}, {1, 0, 0, 1, 0}, {0, 1, 1, 0, 0}, {0, 0, 1, 0, 0},{0, 0, 0, 0, 0}};
    private int temp3 [][] = {{1, 1, 0, 0, 0}, {1, 0, 0, 0, 0}, {0, 1, 1, 1, 0}, {0, 1, 1, 0, 0},{0, 0, 0, 0, 0}};
    private int temp4 [][] = {{1, 1, 0, 0, 0}, {1, 0, 0, 0, 0}, {1, 0, 0, 1, 0}, {0, 1, 0, 1, 0},{0, 0, 0, 0, 0}};
    private int temp5 [][] = {{1, 1, 0, 0, 0}, {1, 0, 0, 0, 0}, {1, 1, 1, 0, 0}, {0, 0, 1, 0, 0},{0, 0, 0, 0, 0}};
    private int temp6 [][] = {{1, 1, 0, 0, 0}, {0, 0, 1, 0, 0}, {1, 0, 1, 0, 0}, {0, 0, 1, 0, 0},{0, 0, 0, 0, 0}};
    private int temp7 [][] = {{0, 1, 0, 0, 0}, {1, 0, 1, 0, 0}, {0, 0, 1, 1, 0}, {0, 1, 0, 0, 0},{0, 0, 0, 0, 0}};
    private int temp8 [][] = {{0, 1, 0, 0, 0}, {0, 0, 1, 1, 0}, {0, 0, 1, 1, 0}, {0, 0, 1, 0, 0},{0, 0, 0, 0, 0}};
    private GameMap gameMap = new GameMap(5, 5, 200, 0, temp1);
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: transform()
    *
    */
    @Test
    public void testTransform() throws Exception {
        gameMap.transform();
        assertArrayEquals(temp2, gameMap.getMatrix());
        gameMap.transform();
        assertArrayEquals(temp3, gameMap.getMatrix());
        gameMap.transform();
        assertArrayEquals(temp4, gameMap.getMatrix());
        gameMap.transform();
        assertArrayEquals(temp5, gameMap.getMatrix());
        gameMap.transform();
        assertArrayEquals(temp6, gameMap.getMatrix());
        gameMap.transform();
        assertArrayEquals(temp7, gameMap.getMatrix());
        gameMap.transform();
        assertArrayEquals(temp8, gameMap.getMatrix());
    }

    /**
    *
    * Method: findLivedNum(int y, int x)
    *
    */
    @Ignore
    @Test
    public void testFindLivedNum() throws Exception {

        assertEquals(2,gameMap.findLivedNum(0, 0));
        assertEquals(1,gameMap.findLivedNum(0, 2));
        assertEquals(3,gameMap.findLivedNum(1, 0));
        assertEquals(2,gameMap.findLivedNum(2, 2));
        assertEquals(0,gameMap.findLivedNum(4, 2));
        assertEquals(1,gameMap.findLivedNum(2, 4));
        assertEquals(0,gameMap.findLivedNum(4, 4));
    }

    /**
    *
    * Method: toString()
    *
    */
    @Ignore
    @Test
    public void testToString() throws Exception {
        String sTestToString = "[1, 1, 1, 0, 0]" + "\n" + "[1, 0, 0, 0, 0]" + "\n" + "[0, 1, 1, 1, 0]" + "\n" + "[0, 0, 0, 0, 0]" + "\n" + "[0, 0, 0, 0, 0]" + "\n";
        assertEquals(sTestToString, gameMap.toString());
    }
    /**
    *
    * Method: getLength()
    *
    */
    @Ignore
    @Test
    public void testGetLength() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getWidth()
    *
    */
    @Ignore
    @Test
    public void testGetWidth() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getMatrix()
    *
    */
    @Ignore
    @Test
    public void testGetMatrix() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getTransfromNum()
    *
    */
    @Ignore
    @Test
    public void testGetTransfromNum() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getDuration()
    *
    */
    @Ignore
    @Test
    public void testGetDuration() throws Exception {
    //TODO: Test goes here...
    }


} 
