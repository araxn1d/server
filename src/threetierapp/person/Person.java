/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threetierapp.person;

/**
 *
 * @author Жека
 */
public class Person {

    public Person() {
    }

    public Person(int id2, String aFirstName, String aLastName, String d, String phone, String mail, String url) {
        firstName = aFirstName;
        lastName = aLastName;
        this.birthDate = d;
        id = id2;
        phoneNum = phone;
        eMail = mail;
        imgUrl = url;
        bigField = "";
        file = null;
        imgData = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int iD) {
        id = iD;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUrl(String s) {
        imgUrl = s;
    }

    public String getUrl() {
        return imgUrl;
    }

    public byte[] getImgData() {
        return imgData;
    }

    public void setImgData(byte[] imgData) {
        this.imgData = imgData;
    }

    public void setBirthDate(String d) {
        birthDate = d;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setPhoneNum(String s) {
        phoneNum = s;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void seteMail(String s) {
        eMail = s;
    }

    public String geteMail() {
        return eMail;
    }

    public void setbigField(String s) {
        bigField = s;
    }

    public String getbigField() {
        return bigField;
    }

    public void setFile(byte[] f) {
        file = f;
    }

    public byte[] getFile() {
        return file;
    }
    private String imgUrl;
    private int id;
    private String firstName;
    private String lastName;
    private byte[] imgData;
    private String birthDate;
    private String phoneNum;
    private String eMail;
    private String bigField;
    private byte[] file;
}