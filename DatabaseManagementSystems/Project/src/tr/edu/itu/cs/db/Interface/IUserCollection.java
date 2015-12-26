package tr.edu.itu.cs.db.Interface;

import java.util.List;

import tr.edu.itu.cs.db.Class.User;


public interface IUserCollection {
    public List<User> getUser();

    public void addUser(User user);

    public void deleteUser(User user);

    public void updateUser(User user);

    public String getVerification(User user);

    public void activated(User user);

    public User login(String Username);
}
