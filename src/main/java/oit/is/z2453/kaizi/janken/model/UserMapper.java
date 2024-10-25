package oit.is.z2453.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT * FROM users")
  ArrayList<User> selectAllUser();

  @Select("SELECT * FROM users where id = #{id}")
  User selectById(int id);

  @Select("SELECT * FROM users where name = #{name}")
  User selectByName(String name);
}
