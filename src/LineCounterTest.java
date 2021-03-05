import org.junit.Test;

import static org.junit.Assert.*;

public class LineCounterTest {

    @Test
    public void main() {
        LineCounter.main(new String[] {"fun1.txt", "fun2.txt"});
    }
}