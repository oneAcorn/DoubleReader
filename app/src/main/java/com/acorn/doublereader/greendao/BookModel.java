package com.acorn.doublereader.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Objects;

/**
 * GreenDao实体类得用Java写
 * Created by acorn on 2021/3/27.
 */
@Entity
public class BookModel {
    @Id(autoincrement = true)
    private Long id;
    private String path;
    private String name;
    private String img;
    //0:txt,1:epub,2:pdf
    private int type;
    @Generated(hash = 826324259)
    public BookModel(Long id, String path, String name, String img, int type) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.img = img;
        this.type = type;
    }
    @Generated(hash = 1421733684)
    public BookModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImg() {
        return this.img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookModel)) return false;
        BookModel bookModel = (BookModel) o;
        return type == bookModel.type &&
                path.equals(bookModel.path) &&
                name.equals(bookModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, name, type);
    }
}