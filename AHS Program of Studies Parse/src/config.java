import java.util.ArrayList;

public class config {
    boolean debug;
    ArrayList<String[]> courseKeys;

    public config(){
        debug = false;
        courseKeys = new ArrayList<>();
    }

    public boolean getDebug(){return debug;}
    public ArrayList<String[]> getCourseKeys(){return courseKeys;}

    public void setDebug(boolean debug){this.debug = debug;}
    public void setCourseKeys(ArrayList<String[]> courseKeys){this.courseKeys = courseKeys;}
}
