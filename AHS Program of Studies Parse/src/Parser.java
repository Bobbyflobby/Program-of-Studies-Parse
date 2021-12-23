/*
Walter Vincent
copy and paste Program of Studies into Input.txt
only works if course codes end in a "Z"
Started Dec 2021
 */

import java.io.File;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Parser {
    File input;
    Boolean debugOn;
    Map<String, String> courseKeys = new HashMap<>();
    ArrayList<ArrayList<String>> classes = new ArrayList<>();

    public Parser(){
        input = new File("src/Input.txt");
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

    }
    public void run(){
        try {
            Scanner inputReader = new Scanner(input);
            boolean lastLineHasCode = false;
            while (inputReader.hasNextLine()) {
                String data = inputReader.nextLine();
                data = data.trim();
                if(data.contains("Z ")) {
                    ArrayList<String> pr = parseLine(data);
                    //System.out.println(pr);
                    classes.add(pr);
                    lastLineHasCode = true;
                }else if(lastLineHasCode && !data.equals("")){
                    classes = addDiscription(classes, data);
                }else if (data.equals("")){
                    lastLineHasCode = false;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("no file");}

        for(ArrayList<String> course:classes){
            System.out.println(course);
        }
    }

    private ArrayList<String> parseLine(String line){

        ArrayList<String> rt = new ArrayList<>();

        line = line.trim();// Remove spaces on each end
        String editedLine = line; //copy of line that is edited

        String courseCode = line.substring(0,line.indexOf("Z")+1);
        rt.add(courseCode); //adds course code
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

            editedLine += "  ";
            editedLine = editedLine.substring(0, editedLine.indexOf(grades)) + editedLine.substring(editedLine.indexOf(grades)+grades.length());

            if(editedLine.toUpperCase(Locale.ROOT).contains("GRADES")){
                editedLine = editedLine.substring(0,editedLine.toUpperCase(Locale.ROOT).indexOf("GRADES")) + editedLine.substring(editedLine.toUpperCase(Locale.ROOT).indexOf("GRADES") + 6);
            }else if(editedLine.toUpperCase(Locale.ROOT).contains("GRADE")){
                editedLine = editedLine.substring(0,editedLine.toUpperCase(Locale.ROOT).indexOf("GRADE")) + editedLine.substring(editedLine.toUpperCase(Locale.ROOT).indexOf("GRADE") + 5);
            }

            grades = grades.trim();
            rt.add("Grades " + grades);
        }else{
            rt.add("no grade data");
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
            rt.add(txt);
            editedLine = editedLine.substring(0, editedLine.indexOf(txt)) + editedLine.substring(editedLine.indexOf(txt)+txt.length());
            if(editedLine.contains("()")){
                editedLine = editedLine.substring(0,editedLine.indexOf("(")) + editedLine.substring(editedLine.indexOf(")") +1);
            }
        }else{
            rt.add("no credit data");
        }

        debug(line);

        editedLine = editedLine.trim();
        rt.add(editedLine);

        rt.add(courseKeys.get(courseCode.substring(0,2)));// add subject to end of list

        return rt;
    }

    private ArrayList<ArrayList<String>> addDiscription(ArrayList<ArrayList<String>> classList, String line){
        ArrayList<String> classInfo = classList.get(classList.size()-1);
        if(classInfo.size() == 5){
            classInfo.add(line);
        }else if(classInfo.size() == 6){
            classInfo.set(5, classInfo.get(5));
        }

        classList.set(classList.size()-1, classInfo);

        return  classList;
    }

    private void debug(String str){
        if(debugOn){
            System.out.println(str);
        }
    }
}
