import java.util.ArrayList;

/**
 * Created by Agrim Sachdeva on 5/9/2017.
 */
public class LoginObject extends DataObject {

    String username;
    String password;
    ArrayList<UserObject> userList;



    public ArrayList<UserObject> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<UserObject> userList) {
        this.userList = userList;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
