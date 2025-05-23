package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeCheckerTest {
    private TimeChecker timeChecker;

    @BeforeEach
    void setUp() {
        timeChecker = new TimeChecker();
    }

    @Test
    void getCurrentYearTest() {
        Integer year = timeChecker.getCurrentYear();
        assertEquals(LocalDate.now().getYear(), year);
    }


}
