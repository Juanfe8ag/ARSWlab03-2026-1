package edu.eci.arsw.immortals;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class ScoreBoard {
  private final AtomicLong totalFights = new AtomicLong();
  private final AtomicInteger activeFights = new AtomicInteger();
  public void enterFight() { activeFights.incrementAndGet(); }
  public void leaveFight() { activeFights.decrementAndGet(); }
  public int activeFights() { return activeFights.get(); }
  public void recordFight() { totalFights.incrementAndGet(); }
  public long totalFights() { return totalFights.get(); }
}
