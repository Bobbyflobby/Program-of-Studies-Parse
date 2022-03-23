import java.util.ArrayList;

public class Config {
    boolean debug;
    ArrayList<String[]> courseKeys;
    String inputFileLocation;
    String outputFileLocation;

    public Config(){
        debug = false;
        courseKeys = new ArrayList<>();
    }

    public String toString(){
        String rt = "Debug mode: " + debug + " ";

        rt += "Course Keys: ";
        for(String[] list:courseKeys){
            rt += "[" + list[0] + "," + list[1] + "] ";
        }

        return  rt;
    }

    public boolean getDebug(){return debug;}
    public ArrayList<String[]> getCourseKeys(){return courseKeys;}
    public String getInputFileLocation(){return inputFileLocation;}
    public String getOutputFileLocation(){return outputFileLocation;}

    public void setDebug(boolean debug){this.debug = debug;}
    public void setCourseKeys(ArrayList<String[]> courseKeys){this.courseKeys = courseKeys;}
    public void setInputFileLocation(String inputFileLocation){this.inputFileLocation = inputFileLocation;}
    public void setOutputFileLocation(String outputFileLocation){this.outputFileLocation = outputFileLocation;}
}
