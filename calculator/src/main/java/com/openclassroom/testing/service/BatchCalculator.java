package com.openclassroom.testing.service;

import com.openclassroom.testing.model.CalculationModel;
import com.openclassroom.testing.model.CalculationType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Supports solving calculations provided in a batch file.
 */

public class BatchCalculator {

    // TODO Although I'm sticking to vanilla Java, the curious student,
    // can check out Project Lombok which saves you having to
    // write customer getters and setters: https://projectlombok.org/
    private BatchCalculationFileService batchCalculationFileService;
    private Calculator calculator;

    /**
     * Constructor
     * @param batchCalculationFileService instance used to read the batch file
     * @param calculator instance used to solve problems
     */
    public BatchCalculator(BatchCalculationFileService batchCalculationFileService, Calculator calculator) {
        this.batchCalculationFileService = batchCalculationFileService;
        this.calculator = calculator;
    }

    public List<CalculationModel> calculateFromFile(String pathToFile) throws IOException {
        Stream<String> calculations = batchCalculationFileService.read(pathToFile);
        ArrayList<CalculationModel> solutions = new ArrayList<>();
        calculations.forEach(calculation -> {
            CalculationModel calculationModel = CalculationModel.fromText(calculation);
            CalculationModel solutionModel = solve(calculationModel);
            solutionModel.setLeftArgument( calculationModel.getLeftArgument() );
            solutions.add(solutionModel);
        });
        return solutions;
    }

    /**
     * Takes a definition of a calculation and solves it
     * @param calculationModel
     * @return A model for calulation
     */
    private CalculationModel solve(CalculationModel calculationModel) {
        CalculationType type = calculationModel.getType();

        double response = 0.0;
//        switch (type) {
//            case ADDITION:
//                response = calculator.addTwoNumbers(
//                        calculationModel.getLeftArgument(),
//                        calculationModel.getRightArgument());
//                break;
//            case MULTIPLICATION:
//                response = calculator.multiplyTwoNumbers(
//                        calculationModel.getLeftArgument(),
//                        calculationModel.getRightArgument());
//                break;
//            default:
//                throw new UnsupportedOperationException("Unsupported calculations");
//        }

        calculationModel.setSolution(response);
        return calculationModel;
    }

}