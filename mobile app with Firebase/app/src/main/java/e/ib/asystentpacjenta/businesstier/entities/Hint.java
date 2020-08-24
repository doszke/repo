package e.ib.asystentpacjenta.businesstier.entities;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Hint implements Serializable {

    public static class Comparator implements java.util.Comparator<Hint>{

        @Override
        public int compare(Hint o1, Hint o2) {
            return (int) (o1.id - o2.id);
        }
    }

    public static final long serialVersionUID = 1L;

    private long id;
    private String token;
    private String info;

    private String modified;

    public Hint(){ }

    public Hint(Long id, String token, String info, String modified) {
        this.id = id;
        this.token = token;
        this.info = info;
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hint hint = (Hint) o;
        return id==hint.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "Hint{" +
                "getId=" + id +
                ", getToken='" + token + '\'' +
                ", getInfo='" + info + '\'' +
                ", getModified='" + modified + '\'' +
                '}';
    }
}
