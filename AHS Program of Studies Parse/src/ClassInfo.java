public class ClassInfo {
    String classCode;
    String className;
    String classGrades;
    String classCredits;
    String classCategory;
    String classLevel;
    String classDescription;

    public ClassInfo(){
        classCode = null;
        className = null;
        classGrades = null;
        classCredits = null;
        classCategory = null;
        classLevel = null;
        classDescription = null;
    }

    // not using all set/get methods but added them to make stuff easier in the future
    public void setClassCode(String code){classCode = code;}
    public void setClassName(String name){className = name;}
    public void setClassGrades(String grades){classGrades = grades;}
    public void setClassCredits(String credits){classCredits = credits;}
    public void setClassCategory(String category){classCategory = category;}
    public void setClassLevel(String level){classLevel = level;}
    public void setClassDescription(String description){classDescription = description;}

    public String getClassCode(){return classCode;}
    public String getClassName(){return  className;}
    public String getClassGrades(){return classGrades;}
    public String getClassCredits(){return classCredits;}
    public String getClassCategory(){return classCategory;}
    public String getClassLevel(){return classLevel;}
    public String getClassDescription(){return classDescription;}
}
