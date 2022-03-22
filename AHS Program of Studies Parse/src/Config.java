import java.util.ArrayList;

public class Config {
    boolean debug;
    ArrayList<String[]> courseKeys;

    public Config(){
        debug = false;
        courseKeys = new ArrayList<>();
        String[] tmp = {"i","i"};
        courseKeys.add(tmp);
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

    public void setDebug(boolean debug){this.debug = debug;}
    public void setCourseKeys(ArrayList<String[]> courseKeys){this.courseKeys = courseKeys;}
}
