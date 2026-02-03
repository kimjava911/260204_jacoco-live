package kr.java.usejacoco;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalculatorServiceTest {

    @Autowired
    private CalculatorService calculatorService;

    @Test
    @Tag("base") // 'base' 태그는 20% 커버리지 프로파일의 기준이 됩니다.
    void testAdd() {
        assertEquals(5, calculatorService.add(2, 3));
    }

    @Test
    @Tag("medium-add") // 'medium-add' 태그는 55% 커버리지 프로파일에 추가됩니다.
    void testSubtract() {
        assertEquals(1, calculatorService.subtract(3, 2));
    }

    @Test
    @Tag("medium-add")
    void testMultiply() {
        assertEquals(6, calculatorService.multiply(2, 3));
    }

    @Test
    @Tag("high-add") // 'high-add' 태그는 80% 커버리지 프로파일에 추가됩니다.
    void testDivide() {
        assertEquals(2, calculatorService.divide(6, 3));
    }

    @Test
    @Tag("high-add")
    void testModulus() {
        assertEquals(1, calculatorService.modulus(3, 2));
    }
}
