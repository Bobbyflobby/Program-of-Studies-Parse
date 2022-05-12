import java.util.ArrayList;

public class Config {
    boolean debug;
    private ArrayList<String[]> courseKeys;
    private ArrayList<String> excludedCourseKeys;
    private String inputFileLocation;
    private String outputFileLocation;

    public Config(){
        debug = false;
        courseKeys = new ArrayList<>();
        excludedCourseKeys = new ArrayList<>();
        inputFileLocation = "none set";
        outputFileLocation = "none set";
    }

    public String toString(){
        String rt = "Debug mode: " + debug + " ";

        rt += "\nCourse Keys: ";
        for(String[] list:courseKeys){
            rt += "[" + list[0] + "," + list[1] + "] ";
        }

        rt += "\nExcluded Course Keys: ";
        for(String key:excludedCourseKeys){
            rt += key + ", ";
        }

        rt += "\nInput file location: " + inputFileLocation;
        rt += "\nOutput file location: " + outputFileLocation;

        return  rt;
    }

    public boolean getDebug(){return debug;}
    public ArrayList<String[]> getCourseKeys(){return courseKeys;}
    public ArrayList<String> getExcludedCourseKeys(){return excludedCourseKeys;}
    public String getInputFileLocation(){return inputFileLocation;}
    public String getOutputFileLocation(){return outputFileLocation;}

    public void setDebug(boolean debug){this.debug = debug;}
    public void setCourseKeys(ArrayList<String[]> courseKeys){this.courseKeys = courseKeys;}
    public void setExcludedCourseKeys(ArrayList<String> excludedCourseKeys){this.excludedCourseKeys = excludedCourseKeys;}
    public void setInputFileLocation(String inputFileLocation){this.inputFileLocation = inputFileLocation;}
    public void setOutputFileLocation(String outputFileLocation){this.outputFileLocation = outputFileLocation;}
}
