package com.bestrookie.springframework.dto;

/**
 * @Author bestrookie
 * @Date 2023/12/12 10:41
 * @Desc
 */
public class UserService {
    private String uId;
    private UserDao userDao;
    private String location;
    private String company;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String queryUserInfo(){
        return userDao.queryUserName(uId) + ","+ company + "," + location;
    }
    public String getId() {
        return uId;
    }

    public void setId(String uId) {
        this.uId = uId;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
