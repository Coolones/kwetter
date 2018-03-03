package services;

import dao.IUserDao;
import dao.JPA;
import domain.Kweet;
import domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UserService {
    @Inject
    @JPA
    IUserDao dao;

    public UserService() {
        super();
    }

    public User addUser(User entity) {
        return dao.create(entity);
    }

    public User getUserById(long id) {
        return dao.findById(id);
    }

    public User getUserByUsername(String username) {
        if (username.length() == 0) {
            return null;
        }

        return dao.findByUsername(username);
    }

    public List<User> getAll() {
        return dao.findAll();
    }

    public void followUser(User user, User toFollow) {
        user.addFollower(toFollow);
        toFollow.addFollowing(user);
    }

    public List<User> getFollowing(long id) {
        return dao.findFollowing(id);
    }

    public List<User> getFollowers(long id) {
        return dao.findFollowers(id);
    }

    public User editUser(User user) {
        return dao.update(user);
    }

    public Kweet likeKweet(Kweet kweet, User user) {
        return user.addLike(kweet) ? kweet : null;
    }
}