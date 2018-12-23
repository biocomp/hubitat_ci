package biocomp.HubitatCiTest

class MyTestCase extends GroovyTestCase {
    void testAssertions() {
        assertTrue(1 == 2)
        assertEquals("test", "test")

        def x = "42"
        assertNotNull "x must not be null", x
        assertNull null

        assertSame x, x

        Script.runScript("some.groovy")
    }

}