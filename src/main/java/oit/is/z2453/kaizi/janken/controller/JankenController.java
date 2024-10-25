package oit.is.z2453.kaizi.janken.controller;

import oit.is.z2453.kaizi.janken.model.Janken;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2453.kaizi.janken.model.User;
import oit.is.z2453.kaizi.janken.model.Match;
import oit.is.z2453.kaizi.janken.model.MatchInfo;
import oit.is.z2453.kaizi.janken.model.UserMapper;
import oit.is.z2453.kaizi.janken.model.MatchMapper;
import oit.is.z2453.kaizi.janken.model.MatchInfoMapper;

@Controller
@RequestMapping("/")
public class JankenController {

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @GetMapping("janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    ArrayList<User> allUsers = userMapper.selectAllUser();
    ArrayList<Match> allMatches = matchMapper.selectAllMatches();
    model.addAttribute("allUsers", allUsers);
    model.addAttribute("allMatches", allMatches);
    model.addAttribute("name", loginUser);
    return "janken.html";
  }

  @PostMapping("janken")
  public String janken(@RequestParam String name, ModelMap model) {
    model.addAttribute("name", name);
    return "janken.html";
  }

  @GetMapping("jankengame")
  public String jankengame(@RequestParam String hand, ModelMap model) {
    Janken jn = new Janken();
    jn.setHand(hand);
    String result;
    result = jn.judge(model);
    model.addAttribute("result", result);
    return "janken.html";
  }

  @GetMapping("match")
  public String match(Principal prin, @RequestParam int id, ModelMap model) {
    String loginUser = prin.getName();
    User user = userMapper.selectById(id);
    model.addAttribute("name", loginUser);
    model.addAttribute("opp", user);
    return "match.html";
  }

  @GetMapping("fight")
  public String fight(Principal prin, @RequestParam String hand, @RequestParam int id, ModelMap model) {
    String loginUser = prin.getName();
    User user = userMapper.selectByName(loginUser);
    MatchInfo matchInfo = new MatchInfo();

    matchInfo.setUser1(user.getId());
    matchInfo.setUser2(id);
    matchInfo.setUser1Hand(hand);
    matchInfo.setActive(true);
    matchInfoMapper.insertMatcheInfo(matchInfo);

    model.addAttribute("name", loginUser);

    return "wait.html";
  }
}
