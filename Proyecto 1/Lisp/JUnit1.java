
//JUnit clase main Proyecto 1
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class MainTest {

    @Test
    public void testArreglarExpresion() {
        String expresionOriginal = "(setq x 10)\n    ( + 1 2 )   \t( * 3 4 )";
        String expresionArreglada = Main.ArreglarExpresion(expresionOriginal);
        String expected = "(setq x 10)( + 1 2 )( * 3 4 )";
        assertEquals(expected, expresionArreglada);
    }

    @Test
    public void testExpresionCompleta() {
        assertTrue(Main.expresionCompleta("(setq x 10)"));
        assertTrue(Main.expresionCompleta("( + 1 2 )"));
        assertTrue(Main.expresionCompleta("( * 3 4 )"));
        assertFalse(Main.expresionCompleta("(setq x 10) ( + 1 2 )"));
        assertFalse(Main.expresionCompleta("(setq x 10)( + 1 2 )"));
        assertFalse(Main.expresionCompleta("(setq x 10) ( + 1 2 ) ( * 3 4"));
    }
}
