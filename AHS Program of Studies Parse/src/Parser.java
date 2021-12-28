/*
Walter Vincent
copy and paste Program of Studies into Input.txt
only works if course codes end in a "Z"
Started Dec 2021
 */

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;  // Import this class to handle errors

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {
    File input;
    final String inputLocation = "src/Input.txt";
    File output;
    final String outputLocation = "src/output.json";
    Boolean debugOn;
    Map<String, String> courseKeys = new HashMap<>();
    ArrayList<classInfo> classes = new ArrayList<>();

    public Parser(){
        input = new File(inputLocation);
        debugOn = false; // extra outputs if true

        // setting course keys (take from file?)
        courseKeys.put("EN", "English");
        courseKeys.put("SC", "Science");
        courseKeys.put("PE", "Physical Education");
        courseKeys.put("CS", "Life Skills");
        courseKeys.put("IN", "Internship");
        courseKeys.put("AC", "Art");
        courseKeys.put("SS", "Social Studies");
        courseKeys.put("MA", "Math");
        courseKeys.put("PA", "Music");
        courseKeys.put("ML", "Foreign Language(not latin)");
        courseKeys.put("OL", "Foreign Language(not latin)");
        courseKeys.put("CL", "Latin");
        courseKeys.put("FR", "Freshman Seminar");
        courseKeys.put("IS", "Independent Study");
        courseKeys.put("AP", "AP");
        courseKeys.put("SP", "Special");
        courseKeys.put("CD", "Technology Intern");
        courseKeys.put("TW", "enter something for tw");

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
                    classInfo pr = parseLine(data);
                    classes.add(pr);
                    lastLineHasCode = true;
                }else if(lastLineHasCode && !data.equals("")){ // checks if the line is part of a description if it dosn't have a course code in it
                    classes = addDescription(classes, data);
                }else if (data.equals("")){
                    lastLineHasCode = false;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("no file");}

        if(debugOn){
            for(classInfo course:classes){
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
        }
        catch (JsonParseException e) { e.printStackTrace();}
        catch (JsonMappingException e) { e.printStackTrace(); }
        catch (JsonProcessingException e) {e.printStackTrace();}

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

    //takes line as input and returns [course code, grades, credits, name, course catogory] (need to and levels)
    private classInfo parseLine(String line){

        classInfo rt = new classInfo();

        line = line.trim();// Remove spaces on each end
        String editedLine = line; //copy of line that is edited

        String courseCode = line.substring(0,line.indexOf("Z")+1);
        rt.setClassCode(courseCode); //adds course code
        editedLine = editedLine.substring(editedLine.indexOf(courseCode) + courseCode.length());

        // gets grade numbers
        if(line.contains("Grades ") || line.contains("Grade ")){
            int z = line.indexOf("Grade");
            String txt = line.substring(z); // make this smarter
            int startGradeNumIndex = txt.indexOf(" ") +1;

            int endIndex;
            if(txt.contains("credit")){
                endIndex = txt.indexOf(txt.substring(txt.indexOf("credit")-5, txt.indexOf("credit")));// make smarter
            }else{
                endIndex = txt.length();
            }
            String grades = txt.substring(startGradeNumIndex, endIndex); // make this smarter

            // removes from edited line
            editedLine += "  ";
            editedLine = editedLine.substring(0, editedLine.indexOf(grades)) + editedLine.substring(editedLine.indexOf(grades)+grades.length());

            if(editedLine.toUpperCase(Locale.ROOT).contains("GRADES")){
                editedLine = editedLine.substring(0,editedLine.toUpperCase(Locale.ROOT).indexOf("GRADES")) + editedLine.substring(editedLine.toUpperCase(Locale.ROOT).indexOf("GRADES") + 6);
            }else if(editedLine.toUpperCase(Locale.ROOT).contains("GRADE")){
                editedLine = editedLine.substring(0,editedLine.toUpperCase(Locale.ROOT).indexOf("GRADE")) + editedLine.substring(editedLine.toUpperCase(Locale.ROOT).indexOf("GRADE") + 5);
            }

            grades = grades.trim();
            rt.setClassGrades("Grades " + grades);
        }

        // Find credits if exists
        if(line.contains("credits")){
            int index = line.indexOf("credits");
            int startIndex = line.substring(0, index-2).lastIndexOf(" ");
            String txt = line.substring(startIndex + 1, index + 7);
            txt = txt.trim();

            if(txt.contains("(")){ // sometimes parentheses would be counted to
                txt = txt.substring(txt.indexOf("(")+1);
            }
            rt.setClassCredits(txt);
            editedLine = editedLine.substring(0, editedLine.indexOf(txt)) + editedLine.substring(editedLine.indexOf(txt)+txt.length());
            if(editedLine.contains("()")){
                editedLine = editedLine.substring(0,editedLine.indexOf("(")) + editedLine.substring(editedLine.indexOf(")") +1);
            }
        }

        debug(line);

        editedLine = editedLine.trim();
        rt.setClassName(editedLine); // uses anything left after taking out the course code, grades, and credits as the course title

        rt.setClassCategory(courseKeys.get(courseCode.substring(0,2)));// add subject to end of list (gets from course keys map)

        rt.setClassLevel(findLevel(rt.getClassName())); // finds class level

        return rt;
    }

    // looks to see if the is there is a level for the course stated in the title of the course
    private String findLevel(String classTitle){
        String level = "";
        classTitle = " " + classTitle + " ";
        classTitle = classTitle.toUpperCase(Locale.ROOT);

        if(classTitle.contains(" AP ")){
            level = "AP";
        }else if(classTitle.contains(" H ") || classTitle.contains(" HONORS ")){
            level = "H";
        }else{
            level = "A";
        }

        return level;
    }

    // adds given line to the end of last list in the list of classes
    private ArrayList<classInfo> addDescription(ArrayList<classInfo> classList, String line){
        classInfo editedClass = classList.get(classList.size()-1);

        if(editedClass.getClassDescription() == null) {
            editedClass.setClassDescription(line);
        }else{
            editedClass.setClassDescription(editedClass.getClassDescription() + line);
        }

        classList.set(classList.size()-1, editedClass);

        return  classList;
    }

    // prints string if debug mode is on
    private void debug(String str){
        if(debugOn){
            System.out.println(str);
        }
    }
}
