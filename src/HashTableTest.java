
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.NoSuchElementException;

public class HashTableTest {
    HashTable hashTbl1;
    HashTable hashTbl2;
    HashTable hashTbl3, hashTbl4, hashTbl5, hashTbl6;

    @Before
    public void setUp() throws Exception  {
        hashTbl1 = new HashTable();
        hashTbl2 = new HashTable();
        hashTbl3 = new HashTable(20);
        hashTbl4 = new HashTable();
        hashTbl5 = new HashTable(10);
    }
    @Test (expected = IllegalArgumentException.class)
    public void setUpException() throws Exception{
        hashTbl6 = new HashTable(4);
    }

    @Test
    public void testAllMethods() {
        assertTrue(hashTbl1.insert("Keagan"));
        hashTbl1.insert("Baby Jesus");
        hashTbl1.insert("12345Hi");
        hashTbl1.insert("How does this work");
        assertFalse(hashTbl1.insert("Keagan"));
        hashTbl1.insert("Smart Fella");

        assertEquals(5, hashTbl1.size());
        assertEquals(15, hashTbl1.capacity());

        assertTrue(hashTbl1.delete("Keagan"));
        assertFalse(hashTbl1.delete("Keagan"));
        assertEquals(4, hashTbl1.size());
        assertTrue(hashTbl1.delete("12345Hi"));
        assertTrue(hashTbl1.lookup("Baby Jesus"));

        hashTbl1.insert("Smart");
        hashTbl1.insert("Fart");
        hashTbl1.insert("Fart smellar");
        hashTbl1.insert("Big Sauce");
        hashTbl1.insert("Ristin");
        hashTbl1.insert("1");
        assertEquals(15, hashTbl1.capacity());
        hashTbl1.insert("10");
        assertEquals(10, hashTbl1.size());
        assertFalse(hashTbl1.lookup("12345Hi"));
        assertTrue(hashTbl1.lookup("10"));
        assertTrue(hashTbl1.lookup("Big Sauce"));
        assertEquals(30, hashTbl1.capacity());
        assertEquals("Before rehash #1: load factor 0.60, 8 collison(s).\n",hashTbl1.getStatsLog());
        hashTbl1.insert("Smart1");
        hashTbl1.insert("Fart1");
        hashTbl1.insert("Fart smellar1");
        hashTbl1.insert("Big Sauce1");
        hashTbl1.insert("Ristin1");
        hashTbl1.insert("123");
        hashTbl1.insert("Smart12");
        hashTbl1.insert("Fart12");
        hashTbl1.insert("Fart smellar12");
        hashTbl1.insert("Big Sauce12");
        hashTbl1.insert("Ristin12");
        hashTbl1.insert("12322");
        assertEquals("Before rehash #1: load factor 0.60, 8 collison(s).\nBefore rehash #2: load factor 0.57," +
                " 3 collison(s).\n",hashTbl1.getStatsLog());
    }
    @Test (expected = NullPointerException.class)
    public void testInsertElementException1() {
        hashTbl5.insert(null);
    }
    @Test (expected = NullPointerException.class)
    public void testNullPointerException2() {
        hashTbl5.delete(null);
    }
    @Test (expected = NullPointerException.class)
    public void testNullPointerException3() {
        hashTbl5.lookup(null);
    }
}