import functions.*;
import functions.basic.Cos;
import functions.basic.Sin;

import java.io.*;

public class Main {
    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException{
        double[] mas = {0, 1, 2, 3};

        TabulatedFunction f = new ArrayTabulatedFunction(0, 3, mas);

        for (FunctionPoint p : f) {
            System.out.println(p);
        }
        System.out.println();
        
        Function ff = new Cos();
        TabulatedFunction tf;
        tf = TabulatedFunctions.tabulate(ff, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(ff, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(ff, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        System.out.println();

        TabulatedFunction f1;
        f1 = TabulatedFunctions.createTabulatedFunction(0, 10, 3, ArrayTabulatedFunction.class);
        System.out.println(f1.getClass());
        System.out.println(f1);

        f1 = TabulatedFunctions.tabulate(new Sin(), 0, Math.PI, 11, LinkedListTabulatedFunction.class);
        System.out.println(f1.getClass());
        System.out.println(f1);

        f1 = TabulatedFunctions.createTabulatedFunction(new FunctionPoint[] {new FunctionPoint(0, 0),
                new FunctionPoint(5, 5), new FunctionPoint(10, 10)}, LinkedListTabulatedFunction.class );
        System.out.println(f1.getClass());
        System.out.println(f1);

        f1 = TabulatedFunctions.createTabulatedFunction(0, 10, new double[] {0, 5, 10},
                ArrayTabulatedFunction.class);
        System.out.println(f1.getClass());
        System.out.println(f1);

    }
}