import java.util.Scanner;

public class GenericLPPSolver {

    public static double calculateObjectiveFunction(double[] coefficients, double[] variables) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * variables[i];
        }
        return result;
    }

    public static boolean isFeasible(double[][] constraintCoefficients, double[] constraints, double[] variables) {
        for (int i = 0; i < constraintCoefficients.length; i++) {
            double lhs = 0;
            for (int j = 0; j < variables.length; j++) {
                lhs += constraintCoefficients[i][j] * variables[j];
            }
            if (lhs > constraints[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Define the objective function coefficients
        System.out.println("Enter the number of variables in the objective function:");
        int numVariables = scanner.nextInt();
        double[] objectiveFunctionCoefficients = new double[numVariables];
        System.out.println("Enter the coefficients of the objective function:");
        for (int i = 0; i < numVariables; i++) {
            objectiveFunctionCoefficients[i] = scanner.nextDouble();
        }

        // Define the constraint coefficients and constraints
        System.out.println("Enter the number of constraints:");
        int numConstraints = scanner.nextInt();
        double[][] constraintCoefficients = new double[numConstraints][numVariables];
        double[] constraints = new double[numConstraints];
        System.out.println("Enter the coefficients of the constraints and the right-hand side values:");
        for (int i = 0; i < numConstraints; i++) {
            for (int j = 0; j < numVariables; j++) {
                constraintCoefficients[i][j] = scanner.nextDouble();
            }
            constraints[i] = scanner.nextDouble();
        }

        // Solve the LPP graphically
        System.out.println("Graphical representation:");

        for (double x = 0; x <= 5; x += 0.5) {
            for (double y = 0; y <= 5; y += 0.5) {
                double[] variables = {x, y};
                if (isFeasible(constraintCoefficients, constraints, variables)) {
                    System.out.printf("(%0.1f, %0.1f) [Z = %.1f]\t",
                            x, y, calculateObjectiveFunction(objectiveFunctionCoefficients, variables));
                } else {
                    System.out.print("----\t\t\t\t");
                }
            }
            System.out.println();
        }
    }
}

