package Model;
/**
 * <p>This class <code>Space</code> contains methods and properties needed to identify each field or block in the game.</p>
 *
 * These properties include :
 * <ul>
 *     <li>location defines the index in which the Space instance is placed in the <code>Board</code></li>
 *     <li>name contains the <code>String</code> name of the <code>Space</code></li>
 *     <li>type identifies which type of <code>Space</code> is instantiated. The following are the defined types which extends <code>Space</code> :
 *        <ul>
 *            <li><code>Property</code></li>
 *            <li><code>Railroad</code></li>
 *            <li><code>Utility</code></li>
 *            <li><code>Spacee</code></li>
 *        </ul>
 *     </li>
 * </ul>
 *
 *
 */
public abstract class Space{
  private int location;
  private String name;
  private String type;

  /**
   * Creates a <code>Space</code> with its location, name, and type indicated as arguments.
   * @param loc the location index of the <code>Space</code> in the <code>Board</code> object
   * @param name A parameter that indicates the name assigned to this object
   * @param type A <code>String</code> type argument which defines the type of <code>Space</code>
   */
  public Space(int loc, String name, String type)
  {
    location = loc;
    this.name = name;
    this.type = type;
  }

  /**
   * Creates a <code>Space</code> with its name and type indicated as arguments.
   * @param name A parameter that indicates the name assigned to this object
   * @param type A <code>String</code> type argument which defines the type of <code>Space</code>
   */
  public Space(String name, String type)
  {
    this.name = name;
    this.type = type;
  }

  /**
   * Returns the location of the <code>Space</code> implemented in the <code>Board</code>
   * @return the location index of the <code>Space</code> object
   */
  public int getLocation()
  {
    return location;
  }

  /**
   * Returns the name of <code>Space</code>
   * @return the name of the <code>Space</code> instance
   */
  public String getName()
  {
    return name;
  }

  /**
   * Returns the type of <code>Space</code> with the data type of <code>String</code>
   * @return <code>Space</code> type
   */
  public String getType()
  {
    return type;
  }

  /**
   * Allocates the <code>location</code> property of the <code>Space</code> indicated in the parameter.
   * @param loc location index
   */
  public void setLocation(int loc)
  {
    location = loc;
  }


}
