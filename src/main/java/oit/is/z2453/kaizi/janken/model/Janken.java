package oit.is.z2453.kaizi.janken.model;

import java.util.Random;
import org.springframework.ui.ModelMap;

public class Janken {
  private String hand;

  public Janken() {
  }

  public String getHand() {
    return hand;
  }

  public void setHand(String hand) {
    this.hand = hand;
  }

  public String judge(ModelMap model) {
    int playerHandNumber = 0;
    String result = "You Lose";
    Random random = new Random();

    int cpuHandNumber = random.nextInt(3);

    model.addAttribute("hand", hand);

    if (cpuHandNumber == 0) {
      model.addAttribute("cpuhand", "Choki");
    } else if (cpuHandNumber == 1) {
      model.addAttribute("cpuhand", "Gu");
    } else if (cpuHandNumber == 2) {
      model.addAttribute("cpuhand", "Pa");
    }

    if (hand.equals("Pa")) {
      playerHandNumber = 2;
    } else if (hand.equals("Gu")) {
      playerHandNumber = 1;
    }

    if (playerHandNumber == cpuHandNumber) {
      result = "Draw";
    } else if (playerHandNumber == 2 && cpuHandNumber == 1 || playerHandNumber == 1 && cpuHandNumber == 0
        || playerHandNumber == 0 && cpuHandNumber == 2) {
      result = "You Win!";
    }

    return result;
  }
}
