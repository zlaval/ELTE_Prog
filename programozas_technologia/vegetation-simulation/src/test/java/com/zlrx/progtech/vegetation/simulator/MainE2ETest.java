package com.zlrx.progtech.vegetation.simulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

@ExtendWith(MockitoExtension.class)
public class MainE2ETest {

    @Mock
    private Main.ScannerWrapper scanner;

    private Main underTest;

    @BeforeEach
    public void init() {
        var path = getClass().getClassLoader().getResource("test_vegetation.txt").getFile();
        Mockito.when(scanner.next()).thenReturn(path).thenReturn("exit");
        underTest = new Main(scanner);
    }

    @Test
    @DisplayName("Run test of the system")
    public void testRun() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () ->
                underTest.start()
        );
    }

}
