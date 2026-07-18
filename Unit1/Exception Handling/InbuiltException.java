/*
 * Program: Built-in Exception Handling Example
 * Exception Used: ArithmeticException
 *
 * Objective:
 * Demonstrate how Java automatically throws a built-in exception
 * and how try-catch-finally works.
 */

public class BuiltInExceptionDemo {

    public static void main(String[] args) {

        // Program execution starts here
        System.out.println("Program Started");

        /*
         * The try block contains code that might generate an exception.
         * If no exception occurs, all statements inside try execute normally.
         * If an exception occurs, the remaining statements inside try are skipped,
         * and control immediately moves to the matching catch block.
         */
        try {

            int a = 10;
            int b = 0;

            System.out.println("Performing Division...");

            /*
             * This statement causes an ArithmeticException.
             *
             * Java detects that division by zero is illegal.
             * Java automatically creates an ArithmeticException object.
             * The remaining code inside the try block is skipped.
             */
            int result = a / b;

            // This line will NEVER execute because an exception occurs above.
            System.out.println("Result = " + result);

        }

        /*
         * This catch block executes only when an ArithmeticException occurs.
         *
         * The exception object created by Java is stored in variable 'e'.
         */
        catch (ArithmeticException e) {

            System.out.println("Exception Caught!");

            /*
             * getMessage() returns the actual message generated
             * by Java for this exception.
             */
            System.out.println("Message : " + e.getMessage());

            /*
             * Some useful methods available in Exception objects:
             *
             * e.getMessage();      // Returns only the error message
             * e.toString();        // Returns exception class + message
             * e.printStackTrace(); // Prints complete error details
             */
        }

        /*
         * The finally block executes whether an exception occurs or not.
         *
         * It is generally used for cleanup operations like:
         * - Closing files
         * - Closing database connections
         * - Closing network sockets
         * - Releasing resources
         */
        finally {

            System.out.println("Finally Block Executed");

        }

        // Program continues normally after exception handling.
        System.out.println("Program Ended");

    }
}
