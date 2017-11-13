
package jinhua_yan_3;

/**
 *
 * The SocialMedia class is to create a social media with name, user Id and contact number.
 * 
 * @author Jinhua Yan
 */
public class SocialMedia {

    private String name;
    private String id;
    private int no;

    /**
     *
     * Class constructor specifying name, user Id and No. contacts of social media.
     * 
     * @param name is social media name
     * @param id is a id which user input
     * @param no is the user's contacts number
     */
    public SocialMedia(String name, String id, int no) {
        this.name = name;
        this.id = id;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getNo() {
        return no;
    }
    /**
     * 
     * @return social media name 
     */
    @Override
    public String toString() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNo(int no) {
        this.no = no;
    }
    
}
