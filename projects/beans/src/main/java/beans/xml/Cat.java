package beans.xml;

public class Cat implements Pettable {

    private String action;

    public Cat() {
    }

    public Cat(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public void pet() {
        System.out.println("meow");
    }

    @Override
    public String toString() {
        return "Cat{" + "action='" + action + '\'' + '}';
    }
}
