package Week17.day04;

public class Actor {

    private long id;
    private String name;

    public Actor(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
