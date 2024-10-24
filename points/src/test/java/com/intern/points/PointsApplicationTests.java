package com.intern.points;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PointsApplicationTests {

    private PointsService service1;

    @BeforeEach
    public void setUp() {
        service1 = new PointsService();
    }

    @Test
    public void testAddPayment() {
        service1.addPayment(new Payment("DANNON", 100, LocalDateTime.now()));
        service1.addPayment(new Payment("MILLER", 100, LocalDateTime.now()));
        service1.addPayment(new Payment("CLAIRE", 100, LocalDateTime.now()));

        LinkedHashMap<String, Integer> expectedMap = new LinkedHashMap<>();
        expectedMap.put("DANNON", 100);
        expectedMap.put("MILLER", 100);
        expectedMap.put("CLAIRE", 100);

        assertEquals(expectedMap, service1.getStore());
        assertEquals(300, service1.getAllPoints());
    }

    @Test
    public void testSpendPoints() {
        service1.addPayment(new Payment("DANNON", 300, LocalDateTime.parse("2022-10-31T10:00:00")));
        service1.addPayment(new Payment("UNILEVER", 200, LocalDateTime.parse("2022-10-31T11:00:00")));
        service1.addPayment(new Payment("DANNON", -200, LocalDateTime.parse("2022-10-31T15:00:00")));
        service1.addPayment(new Payment("MILLER COORS", 10000, LocalDateTime.parse("2022-11-01T14:00:00")));
        service1.addPayment(new Payment("DANNON", 1000, LocalDateTime.parse("2022-11-02T14:00:00")));

        LinkedHashMap<String, Integer> expectedMap = service1.pay(5000);
        assertEquals(3, expectedMap.size());

        LinkedHashMap<String, Integer> balance = service1.getStore();
        assertEquals(1000, balance.get("DANNON"));
        assertEquals(0, balance.get("UNILEVER"));
        assertEquals(5300, balance.get("MILLER COORS"));
    }

    @Test
    public void testSpendPointsNotEnough() {
        service1.addPayment(new Payment("DANNON", 300, LocalDateTime.now()));
        assertThrows(IllegalArgumentException.class, () -> {
            service1.pay(500);
        });
    }
}
