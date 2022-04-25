package local.restweb.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFilter;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;


@JsonFilter("MsgFilter")
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public class User {

    private int id;

    @Size(min = 2, message = "min length is 2")
    private String name;

    @Past
    private Date birthday;

    private String msg1;
    private String msg2;

    public User() {
    }

    public User(int id, String name, Date birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public User(int id, String name, Date birthday, String msg1, String msg2) {
        this(id, name, birthday);
        this.msg1 = msg1;
        this.msg2 = msg2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthday;
    }

    public void setBirthDay(Date birthDay) {
        this.birthday = birthDay;
    }

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }

    @Override
    public String toString() {
        return String.format("User{ id: %d, name: %s, birthday: %s }", id, name, birthday);
    }

}
