/*
Walter Vincent
copy and paste Program of Studies into Input.txt
only works if course codes end in a "Z"
Started Dec 2021

using com.fasterxml.jackson.core:jackson-core:2.7.5 and com.fasterxml.jackson.core:jackson-databind:2.7.5 for json stuff (I think it is Marven name)
 */

public class Main {

    public static void main(String[] args) {
        //ConfigManager configManager = new ConfigManager();
        //configManager.loadConfig();


        Parser parser = new Parser();
        parser.run();


    }
}
