package Model;

public class Node {
    private Integer id;

    private String name;

    private Integer parentnode;

    private Integer userid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentnode() {
        return parentnode;
    }

    public void setParentnode(Integer parentnode) {
        this.parentnode = parentnode;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}