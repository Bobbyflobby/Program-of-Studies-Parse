/*
Walter Vincent
copy and paste Program of Studies into Input.txt
only works if course codes end in a "Z"
Started Dec 2021
 */

import java.io.File;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.run();
    }
}
