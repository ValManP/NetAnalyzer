/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.controllers;

import lpsolve.LpSolve;
import lpsolve.LpSolveException;

import java.util.Arrays;

public class LpSolveController {
    //public static Logger LOG = LogManager.getLogger("logger");

    public static void setUpperConstraint(double[][] matrix, LpSolve solver, int pos, int size) {
        double[] constraint = new double[size * size];
        Arrays.fill(constraint, 0);

        for (int i = 0; i < size; i++) {
            if (pos != i && matrix[pos][i] > 0) {
                constraint[pos * size + i] = 1.0;
            }
        }

        try {
            solver.addConstraint(constraint, LpSolve.LE, matrix[pos][pos]);
        } catch (LpSolveException e) {
            //LOG.error("Error in lp_solve");
        }
    }

    public static void setLowerConstraint(double[][] matrix, LpSolve solver, int pos, int size) {
        double[] constraint = new double[size * size];
        Arrays.fill(constraint, 0);

        for (int i = 0; i < size; i++) {
            if (pos != i && matrix[i][pos] > 0) {
                constraint[i * size + pos] = 1.0;
            }
        }

        try {
            solver.addConstraint(constraint, LpSolve.GE, matrix[pos][pos]);
        } catch (LpSolveException e) {
            //LOG.error("Error in lp_solve");
        }
    }

    public static void setEqualConstraint(double[][] matrix, LpSolve solver, int pos, int size) {
        double[] constraint = new double[size * size];
        Arrays.fill(constraint, 0);

        for (int i = 0; i < size; i++) {
            if (pos != i && matrix[pos][i] > 0) {
                constraint[pos * size + i] = 1.0;
            }
        }

        for (int i = 0; i < size; i++) {
            if (pos != i && matrix[i][pos] > 0) {
                constraint[i * size + pos] = -1.0;
            }
        }

        try {
            solver.addConstraint(constraint, LpSolve.EQ, 0.0);
        } catch (LpSolveException e) {
            //LOG.error("Error in lp_solve");
        }
    }

    public static void setUniqueLowerConstraint(double[][] matrix, LpSolve solver, int pos, int size) {
        double[] constraint = new double[size * size];
        Arrays.fill(constraint, 0);

        constraint[pos * size + pos] = 1.0;

        try {
            solver.addConstraint(constraint, LpSolve.GE, matrix[pos][pos]);
        } catch (LpSolveException e) {
            //LOG.error("Error in lp_solve");
        }
    }

    public static void setObjectiveFunction(double[][] matrix, LpSolve solver, int pos, int size) {
        double[] constraint = new double[size * size];
        Arrays.fill(constraint, 0);

        for (int i = 0; i < size; i++) {
            if (pos != i && matrix[i][pos] > 0) {
                constraint[i * size + pos] = 1.0;
            }
        }

        try {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    solver.setColName(i * size + j, "x" + String.valueOf(i) + String.valueOf(j));
                }
            }

            solver.setObjFn(constraint);
        } catch (LpSolveException e) {
            //LOG.error("Error in lp_solve");
        }
    }

    public static void setObjectiveFunction(double[][] matrix, LpSolve solver, int[] pos, int posSize, int size) {
        double[] constraint = new double[size * size];
        Arrays.fill(constraint, 0);

        for (int j = 0; j < posSize; j++) {
            for (int i = 0; i < size; i++) {
                if (pos[j] != i && matrix[i][pos[j]] > 0) {
                    constraint[i * size + pos[j]] = 1.0;
                }
            }
        }

        try {
            solver.setObjFn(constraint);
        } catch (LpSolveException e) {
            //LOG.error("Error in lp_solve");
        }
    }

    public static LpSolve getLpSolver(int varSize) {
        try {
            return LpSolve.makeLp(0, varSize);
        } catch (LpSolveException e) {
            //LOG.error("Error in lp_solve");
        }
        return null;
    }

    public static double solve(LpSolve solver) {
        try {
            solver.setMaxim();
            solver.printLp();
            if (solver.solve() == 0) {
                return solver.getObjective();
            }

        } catch (LpSolveException e) {
            //LOG.error("Error in lp_solve");
        }
        return Double.MAX_VALUE;
    }
}
