package com.napier.sem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testAppCreates() {
        App app = new App();
        assertNotNull(app);
    }

    @Test
    void testAppHasDatabase() {
        App app = new App();
        assertNotNull(app.getDatabase());
    }
}