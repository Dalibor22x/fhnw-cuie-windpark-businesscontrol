package cuie.dalibor22x;

import javafx.application.Platform;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CantonControlTest {

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {
            //nothing else to do
        });
        Platform.setImplicitExit(false);
    }

    private CantonControl tc;

    @BeforeEach
    void setup() {
        tc = new CantonControl(SkinType.DEFAULT_TYPE);
    }

    @Test
    void testSomething() {
        //given

        //when

        //then
    }

}