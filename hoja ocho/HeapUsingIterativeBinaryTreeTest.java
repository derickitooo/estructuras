import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeapUsingIterativeBinaryTreeTest {

    @Test
    void testInsert() {
        HeapUsingIterativeBinaryTree<Integer, String> heap = new HeapUsingIterativeBinaryTree<>(Integer::compare);
        heap.Insert(5, "Proceso1");
        heap.Insert(3, "Proceso2");
        heap.Insert(7, "Proceso3");
        assertEquals(3, heap.count());
        assertEquals("Proceso3", heap.get());
    }

    @Test
    void testRemove() {
        HeapUsingIterativeBinaryTree<Integer, String> heap = new HeapUsingIterativeBinaryTree<>(Integer::compare);
        heap.Insert(5, "Proceso1");
        heap.Insert(3, "Proceso2");
        heap.Insert(7, "Proceso3");
        assertEquals("Proceso3", heap.remove());
        assertEquals(2, heap.count());
        assertEquals("Proceso1", heap.get());
    }
}
