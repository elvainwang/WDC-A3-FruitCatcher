package fruitcatcher;


import processing.core.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {

    @Test
    public void simpleTest() {
        App app = new App();
        assertEquals(false, app.die);
        assertEquals(false, app.gameOver);
        assertEquals(false, app.nextLevel);
        assertEquals(false, app.win);

    }
}
