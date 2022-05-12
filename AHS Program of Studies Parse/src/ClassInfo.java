public class ClassInfo {
    String ID;
    String Name;
    String Type;
    String Grade;
    String Credit;
    String Level;
    String Description;
    String Length;

    public ClassInfo(){
        ID = null;
        Name = null;
        Grade = null;
        Credit = null;
        Type = null;
        Level = null;
        Description = null;
        Length = "1"; //@TODO make length be based on class
    }

    // not using all set/get methods but added them to make stuff easier in the future
    public void setClassCode(String code){ID = code;}
    public void setClassName(String name){Name = name;}
    public void setClassGrades(String grades){Grade = grades;}
    public void setClassCredits(String credits){Credit = credits;}
    public void setClassCategory(String category){Type = category;}
    public void setClassLevel(String level){Level = level;}
    public void setClassDescription(String description){Description = description;}
    public void setClassLength(String length){Length = length;}


    public String getID(){return ID;}
    public String getName(){return  Name;}
    public String getGrade(){return Grade;}
    public String getCredit(){return Credit;}
    public String getType(){return Type;}
    public String getLevel(){return Level;}
    public String getDescription(){return Description;}
    public String getLength(){return Length;}
}
