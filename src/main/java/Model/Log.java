package Model;

public class Log {
    private Integer id;

    private Integer userid;

    private String objtype;

    private Integer objid;

    private Integer objevent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getObjtype() {
        return objtype;
    }

    public void setObjtype(String objtype) {
        this.objtype = objtype == null ? null : objtype.trim();
    }

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public Integer getObjevent() {
        return objevent;
    }

    public void setObjevent(Integer objevent) {
        this.objevent = objevent;
    }
}