package ru.diolloyd.webuiat.lesson4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Triangle {
    private static final Logger logger = LoggerFactory.getLogger(Triangle.class);
    private final int sideA;
    private final int sideB;
    private final int sideC;

    public Triangle(int sideA, int sideB, int sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    public double area() {
        int halfP = (this.sideA + this.sideB + this.sideC) / 2;
        logger.debug("halfP=({}+{}+{})/2={}", sideA, sideB, sideC, halfP);
        double result = Math.sqrt(halfP * (halfP - this.sideA) * (halfP - this.sideB) * (halfP - this.sideC));
        logger.debug("result=sqrt({}*({}-{})*({}-{})*({}-{}))={}",
                halfP, halfP, sideA, halfP, sideB, halfP, sideC, result);
        return result;
    }
}
