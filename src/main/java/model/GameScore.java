package model;

import java.util.ArrayList;
import java.util.Comparator;

public class GameScore {
    private final String username;
    private final int lastWave;
    private final int kills;
    private final int hardness;
    private final double accuracy;
    private static ArrayList<GameScore> allGameScores = new ArrayList<>();

    public GameScore(String username, int lastWave, int kills, int hardness, double accuracy) {
        this.username = username;
        this.lastWave = lastWave;
        this.kills = kills;
        this.hardness = hardness;
        this.accuracy = accuracy;
        allGameScores.add(this);
    }

    public static ArrayList<GameScore> getAllGameScores() {
        return allGameScores;
    }

    public static void setAllGameScores(ArrayList<GameScore> allGameScores) {
        GameScore.allGameScores = allGameScores;
    }

    public static void refresh(int selected) {
        allGameScores.sort(new Comparator<GameScore>() {
            @Override
            public int compare(GameScore gs1, GameScore gs2) {
                return Integer.compare(gs2.getLastWave(), gs1.getLastWave());
            }
        });
        if (selected == 0) {
            allGameScores.sort(new Comparator<GameScore>() {
                @Override
                public int compare(GameScore gs1, GameScore gs2) {
                    return Integer.compare(gs2.getKills(), gs1.getKills());
                }
            });
        } else if (selected == 1) {
            allGameScores.sort(new Comparator<GameScore>() {
                @Override
                public int compare(GameScore gs1, GameScore gs2) {
                    return Integer.compare(gs2.getHardness() * gs2.getKills(), gs1.getHardness() * gs1.getKills());
                }
            });
        } else if (selected == 2) {
            allGameScores.sort(new Comparator<GameScore>() {
                @Override
                public int compare(GameScore gs1, GameScore gs2) {
                    return Integer.compare((int) gs2.getAccuracy(), (int) gs1.getAccuracy());
                }
            });
        }
    }

    public int getKills() {
        return kills;
    }

    public int getHardness() {
        return hardness;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public int getLastWave() {
        return lastWave;
    }

    public String getUsername() {
        return username;
    }
}
