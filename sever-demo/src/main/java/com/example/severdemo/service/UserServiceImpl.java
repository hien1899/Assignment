package com.example.severdemo.service;

import com.example.severdemo.constant.DateTime;
import com.example.severdemo.dto.UserDto;
import com.example.severdemo.entity.User;
import com.example.severdemo.exception.NotFoundException;
import com.example.severdemo.repository.UserRepository;
import com.example.severdemo.service.interfaces.UserService;
import com.example.severdemo.service.interfaces.base.BaseService;
import com.example.severdemo.utils.StringUtils;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends BaseService<User, UserDto, Integer, UserRepository>
        implements UserService {

    public UserServiceImpl(MapperFacade mapper, UserRepository repository){
        super(User.class, UserDto.class, repository, mapper);
    }

    @Override
    public User findUserById(int id) {
        var user = this.getRepository().findById(id).orElseThrow(() -> new NotFoundException("Can not find user with id: " + id));
        return user;
    }

    @Override
    public List<User> findUserByName(String name, String field) {
        if(field.equalsIgnoreCase("fname")){
            return this.getRepository().findUserByFName("%" + name + "%");
        } else if(field.equalsIgnoreCase("lname")) {
            return this.getRepository().findUserByLName("%" + name + "%");
        } else if(field.equalsIgnoreCase("all")){
            return this.getRepository().findUserByAllName("%" + name + "%");
        }
        else{
            return this.getRepository().findAll();
        }
    }

    @Override
    public List<User> findUserByDob(String startDate, String endDate) {
        List<User> users = this.getRepository().findUserByDob(StringUtils.stringToInstant(startDate, DateTime.DATE_FORMAT_YYYY_MM_DD, false),
                StringUtils.stringToInstant(endDate, DateTime.DATE_FORMAT_YYYY_MM_DD, true));
        return users;
    }

    @Override
    public int createNewUser(String fName, String lName, int age, String dob) {
        var user = User.builder().fName(fName).lName(lName).age(age).dob(StringUtils.stringToInstant(dob, DateTime.DATE_FORMAT_YYYY_MM_DD, false)).build();
        int id = this.getRepository().save(user).getUserId();
        return id;
    }

    @Override
    public List<Integer> createUsersUsingList(List<User> users){
        List<Integer> ids = new ArrayList<>();
        for (User user: users) {
            ids.add(this.getRepository().save(user).getUserId());
        }
        return ids;
    }

    @Override
    public boolean deleteUser(int id){
        this.getRepository().deleteById(id);
        return true;
    }

    @Override
    public void updateUser(int id, String fName, String lName, int age, String dob){
        User user = this.getRepository().findById(id).orElseThrow(() -> new NotFoundException("Can not find user with id: " + id));
        user.setFName(fName);
        user.setLName(lName);
        user.setAge(age);
        user.setDob(StringUtils.stringToInstant(dob, DateTime.DATE_FORMAT_YYYY_MM_DD, false));
        this.getRepository().save(user);
    }

    public void testService() {
//        List<User> users = findUserByName("Jame", true, true);
//        System.out.println(users);
    }

}
