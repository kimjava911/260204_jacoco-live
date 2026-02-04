package kr.java.usejacoco;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    // 변동사항을 만들기 위한 주석

    // 20% 프로파일에 포함 (base 태그)
    public int add(int a, int b) {
        return a + b;
    }

    // 55% 프로파일에 포함 (base + medium-add 태그)
    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    // 80% 프로파일에 포함 (base + medium-add + high-add 태그)
    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }

    public int modulus(int a, int b) {
        return a % b;
    }
}
