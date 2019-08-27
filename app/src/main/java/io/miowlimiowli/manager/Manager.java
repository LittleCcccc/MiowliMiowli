package io.miowlimiowli.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.miowlimiowli.exceptions.UsernameAlreadExistError;
import io.miowlimiowli.exceptions.UsernameorPasswordError;

/**
 * 使用 getManager 获取Manager单例
 */
public class Manager {

    private static Manager manager = new Manager();
    private Manager() {

    }
    public static Manager getInstance(){
        return manager;
    }
    Map<String, User> users = new HashMap<>();
    User user = null;

    /**
     * @param username 用户名
     * @param password 密码
     * @throws UsernameorPasswordError 登陆失败抛出异常
     */
    public void login(final String username, final String password) throws UsernameorPasswordError {
        if(!users.containsKey(username))
            throw new UsernameorPasswordError();
        User user = users.get(username);
        if(user.getPassword() != password)
            throw new UsernameorPasswordError();
        this.user = user;
    }

    /**
     * 注册用户，注册完应登录！
     * @param username 用户名
     * @param password 密码
     * @throws UsernameAlreadExistError 注册失败（用户重名）抛出异常
     */
    public void register(final String username, final String password) throws UsernameAlreadExistError{
        if(users.containsKey(username))
            throw new UsernameAlreadExistError();
        users.put(username, new User(username, password));
    }

}
