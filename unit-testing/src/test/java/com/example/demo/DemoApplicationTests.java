package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

class DemoApplicationTests {

	Calculator underTest = new Calculator();

	@Test //Junit method
	void itShouldAddTwoNumbers() {
	//	given
		int number1 = 20;
		int number2 = 30;
		int result = underTest.add(number1, number2);

	//	then
		assertThat(result).isEqualTo(50); //assertj
	}

	static class Calculator {
		int add(int a, int b) {
			return a + b;
		}
	}

}
