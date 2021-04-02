package com.acorn.doublereader.greendao;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import java.util.Objects;

/**
 * GreenDao实体类得用Java写
 * Created by acorn on 2021/3/27.
 */
@Entity
public class BookModel implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    private String path;
    private String name;
    private String img;
    //0:txt,1:epub,2:pdf
    private int type;
    //txt编码格式
    private String charset;
    private long readTotalSecond;
    //上次阅读时间
    private Date latestReadDate;
    //添加书籍时间
    private Date addDate;

    @Generated(hash = 79605098)
    public BookModel(Long id, String path, String name, String img, int type,
            String charset, long readTotalSecond, Date latestReadDate,
            Date addDate) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.img = img;
        this.type = type;
        this.charset = charset;
        this.readTotalSecond = readTotalSecond;
        this.latestReadDate = latestReadDate;
        this.addDate = addDate;
    }

    @Generated(hash = 1421733684)
    public BookModel() {
    }

    protected BookModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        path = in.readString();
        name = in.readString();
        img = in.readString();
        type = in.readInt();
        charset = in.readString();
        readTotalSecond = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(path);
        dest.writeString(name);
        dest.writeString(img);
        dest.writeInt(type);
        dest.writeString(charset);
        dest.writeLong(readTotalSecond);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookModel> CREATOR = new Creator<BookModel>() {
        @Override
        public BookModel createFromParcel(Parcel in) {
            return new BookModel(in);
        }

        @Override
        public BookModel[] newArray(int size) {
            return new BookModel[size];
        }
    };

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

    public Date getLatestReadDate() {
        return this.latestReadDate;
    }

    public void setLatestReadDate(Date latestReadDate) {
        this.latestReadDate = latestReadDate;
    }

    public Date getAddDate() {
        return this.addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public long getReadTotalSecond() {
        return this.readTotalSecond;
    }

    public void setReadTotalSecond(long readTotalSecond) {
        this.readTotalSecond = readTotalSecond;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
