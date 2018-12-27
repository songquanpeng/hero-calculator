public enum HeroType {
    TANK, AD, AP, WILD, AID
}

public enum ItemType {

}

public class Skill {
    String name;
    String description;
}

public class Buff {
    int speedGain;
    int acGain;
    int apGain;
    int ShieldGain;
    String Others;
}

public abstract class BasicItem {
    int id;
    String name;
    String picturePath;
    String description;
    public getName();
    public getPicturePath();
    public getDescription();
}

public class Hero extends BasicItem {
    Skill[] skills;
    HeroType type; 
    public Hero(int ID);
}

public class Item extends BasicItem {
    Buff buff;
    ItemType type;
    public Item(int ID);
}
