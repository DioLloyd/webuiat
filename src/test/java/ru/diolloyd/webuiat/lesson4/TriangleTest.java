package ru.diolloyd.webuiat.lesson4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangleTest {
    @ParameterizedTest
    @CsvFileSource(resources = "/triangles.csv")
    void area(int sideA, int sideB, int sideC, double expected) {
        double area = new Triangle(sideA, sideB, sideC).area();
        assertEquals(expected, area);
    }
}
