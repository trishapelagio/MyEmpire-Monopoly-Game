package Model;
/**
 * This class is an extension of Space class to denote the following <code>Space</code> Types:
 * <code>Chance</code>
 * <code>Luxury Tax</code>
 * <code>Income Tax</code>
 * <code>Start</code>
 * <code>Jail</code>
 * <code>Community Service </code>
 * <code>Parking</code>
 */
public class Spacee extends Space{

    /**
     * This will initialize a Spacee object give the location, name and type
     * @param loc : location of the object
     * @param name : name of the object
     * @param type : type of the object
     */
    public Spacee(int loc, String name, String type)
    {
        super(loc,name,type);
    }

    /**
     * Initialize a Spacee object given the name and type
     * @param name : name of the Spacee object
     * @param type : type of the Spacee object
     */

    public Spacee(String name, String type)
    {
        super(name,type);
    }

}
