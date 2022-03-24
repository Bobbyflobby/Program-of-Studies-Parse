/*
Walter Vincent
copy and paste Program of Studies into input.txt
only works if the course codes end in a "Z"
Started Dec 2021
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Parser {
    File input;
    final String inputLocation;
    File output;
    final String outputLocation;
    Boolean debugOn;
    Map<String, String> courseKeys = new HashMap<>();
    ArrayList<ClassInfo> classes = new ArrayList<>();
    Config config;
    ConfigManager configManager;

    public Parser(){

        configManager = new ConfigManager();
        config = configManager.loadConfig();
        debugOn = config.getDebug();
        inputLocation = config.getInputFileLocation();
        outputLocation = config.getOutputFileLocation();

        input = new File(inputLocation);

        for(String[] keyPair : config.getCourseKeys()){
            courseKeys.put(keyPair[0],keyPair[1]);
        }


        try {
            output = new File(outputLocation);
            if (output.createNewFile()) {
                System.out.println("File created: " + output.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    public void run(){
        try {
            Scanner inputReader = new Scanner(input);
            boolean lastLineHasCode = false; // is true if last line processed was a line of description or had a course code in it
            while (inputReader.hasNextLine()) {
                String data = inputReader.nextLine();
                data = data.trim();
                if(data.contains("Z ")) { // looks for the "Z" in the end of the course code to find if the line contains a course codes or not
                    ClassInfo pr = parseLine(data);
                    mergeData(classes, pr);
                    lastLineHasCode = true;
                }else if(lastLineHasCode && !data.equals("")){ // checks if the line is part of a description if it doesn't have a course code in it
                    addDescription(classes, data);
                }else if (data.equals("")){
                    lastLineHasCode = false;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("no file");}

        if(debugOn){
            for(ClassInfo course:classes){
               System.out.println(course);
            }
        }

        // creates json
        // used https://www.tutorialspoint.com/jackson/jackson_first_application.htm
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            // creates the json
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(classes);
        } catch (JsonProcessingException e) { e.printStackTrace(); }

        //writes json to file
        try {
            FileWriter writer = new FileWriter(output);
            writer.write(jsonString);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //takes line as input and returns [course code, grades, credits, name, course category] (need to and levels)
    private ClassInfo parseLine(String line){

        ClassInfo rt = new ClassInfo();

         while(line.contains("\t")){ // removing tab characters
            line = line.substring(0, line.indexOf("\t")) + " " + line.substring(line.indexOf("\t")+1);
         }

        line = line.trim();// Remove spaces on each end
        String editedLine = line; //copy of line that is edited

        String courseCode = line.substring(0,line.indexOf("Z")+1);
        rt.setClassCode(courseCode); //adds course code
        editedLine = editedLine.substring(editedLine.indexOf(courseCode) + courseCode.length());

        // gets grade numbers
        if(line.contains("Grades ") || line.contains("Grade ")){
            rt.setClassGrades(findGrades(line));

            // removes from edited line
            editedLine += "  ";
            editedLine = editedLine.substring(0, editedLine.indexOf(rt.getGrade())) + editedLine.substring(editedLine.indexOf(rt.getGrade())+rt.getGrade().length());

            if(editedLine.toUpperCase(Locale.ROOT).contains("GRADES")){
                editedLine = editedLine.substring(0,editedLine.toUpperCase(Locale.ROOT).indexOf("GRADES")) + editedLine.substring(editedLine.toUpperCase(Locale.ROOT).indexOf("GRADES") + 6);
            }else if(editedLine.toUpperCase(Locale.ROOT).contains("GRADE")){
                editedLine = editedLine.substring(0,editedLine.toUpperCase(Locale.ROOT).indexOf("GRADE")) + editedLine.substring(editedLine.toUpperCase(Locale.ROOT).indexOf("GRADE") + 5);
            }
        }

        // Find credits if exists
        if(line.contains("credits")){
            rt.setClassCredits(findCredits(line));

            editedLine = editedLine.substring(0, editedLine.indexOf(rt.getCredit())) + editedLine.substring(editedLine.indexOf(rt.getCredit())+rt.getCredit().length()+8);
            if(editedLine.contains("()")){
                editedLine = editedLine.substring(0,editedLine.indexOf("(")) + editedLine.substring(editedLine.indexOf(")") +1);
            }
        }

        debug(line);

        editedLine = editedLine.trim();
        rt.setClassName(editedLine); // uses anything left after taking out the course code, grades, and credits as the course title

        rt.setClassCategory(courseKeys.get(courseCode.substring(0,2)));// add subject to end of list (gets from course keys map)

        rt.setClassLevel(findLevel(rt.getName())); // finds class level

        return rt;
    }

    private String findGrades(String line){
        int z = line.indexOf("Grade");
        String txt = line.substring(z); // make this smarter
        int startGradeNumIndex = txt.indexOf(" ") + 1;

        int endIndex;
        if(txt.contains("credit")){
            endIndex = txt.indexOf(txt.substring(txt.indexOf("credit")-5, txt.indexOf("credit")));// make smarter
        }else{
            endIndex = txt.length();
        }
        String grades = txt.substring(startGradeNumIndex, endIndex); // make this smarter

        if(grades.indexOf(")") == grades.length()-1) grades = grades.substring(0, grades.length()-1);

        grades = grades.trim();
        return grades;
    }

    private String findCredits(String line){
        int index = line.indexOf("credits");
        int startIndex = line.substring(0, index-2).lastIndexOf(" ");
        String txt = line.substring(startIndex + 1, index);
        txt = txt.trim();

        if(txt.contains("(")){ // sometimes parentheses would be counted to
            txt = txt.substring(txt.indexOf("(")+1);
        }

        return txt;
    }

    // looks to see if the is there is a level for the course stated in the title of the course
    private String findLevel(String classTitle){
        String level;
        classTitle = " " + classTitle + " ";
        classTitle = classTitle.toUpperCase(Locale.ROOT);

        if(classTitle.contains(" AP ") || classTitle.toLowerCase(Locale.ROOT).contains("advanced placement")){
            level = "AP";
        }else if(classTitle.contains(" H ") || classTitle.toLowerCase(Locale.ROOT).contains(" honors ")){
            level = "H";
        }else if (classTitle.contains(" A ")){
            level = "A";
        }else if(classTitle.toLowerCase(Locale.ROOT).contains("heterogeneous")){
            level = "Heterogeneous";
        }else{
            level = null;
        }

        return level;
    }

    // adds given line to the end of last list in the list of classes
    private void addDescription(ArrayList<ClassInfo> classList, String line){
        ClassInfo editedClass = classList.get(classList.size()-1);

        if(editedClass.getDescription() == null) {
            editedClass.setClassDescription(line);
        }else{
            editedClass.setClassDescription(editedClass.getDescription() + line);
        }

        classList.set(classList.size()-1, editedClass);
    }

    private void mergeData(ArrayList<ClassInfo> classList, ClassInfo classAdding){
        for(ClassInfo clas:classList){
            if(clas.getID().equals(classAdding.getID())){
                if(clas.getName() == null) clas.setClassName(classAdding.getName());
                if(clas.getGrade() == null) clas.setClassGrades(classAdding.getGrade());
                if(clas.getCredit() == null) clas.setClassCredits(classAdding.getCredit());
                if(clas.getLevel() == null) clas.setClassLevel(classAdding.getLevel());
                if(clas.getDescription() == null) clas.setClassDescription(classAdding.getDescription());
                //not passing class type because it comes from the class code

                return;
            }
        }
        classList.add(classAdding);
    }

    // prints string if debug mode is on
    private void debug(String str){
        if(debugOn){
            System.out.println(str);
        }
    }
}
