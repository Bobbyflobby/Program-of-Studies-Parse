/*
Walter Vincent
copy and paste Program of Studies into input.txt
only works if course codes end in a "Z"
Started Dec 2021

using com.fasterxml.jackson.core:jackson-core:2.7.5 and com.fasterxml.jackson.core:jackson-databind:2.7.5 for json stuff (Marven coordinates)
 */

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.run();


    }
}

//original keys "courseKeys" : [["EN", "English"], ["SC", "Science"],["PE", "Physical Education"], ["CS", "Life Skills"], ["IN", "Internship"], ["AC", "Art"], ["SS", "Social Studies"], ["MA", "Math"], ["PA", "Music"], ["ML", "Foreign Language"], ["IS", "Independent Study"], ["AP", "AP"], ["SP", "Special"], ["CD", "Technology Intern"], ["OL", "Foreign Language"], ["CL", "Latin"], ["FR", "Freshman Seminar"], ["TW", "Workplace Course"], ["EL","English Language Learners"]],
