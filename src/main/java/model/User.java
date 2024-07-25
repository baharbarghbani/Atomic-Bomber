package model;

public class User {
    private String username;
    private String password;
    private int kill;
    private String avatarPath;
    private int nuclearBombNumber = 0;
    private final int rocketNumber = 1;
    private int clusterBombNumber = 0;
    private int shootingCount;
    private int successfulShootingCount;
    private int accuracy;
    private GameScore gameScore;

    public User(String username, String password,String avatarPath) {
        this.username = username;
        this.password = password;
        this.avatarPath = avatarPath;
        kill = 0;
        shootingCount = 0;
        successfulShootingCount = 0;
    }
    public void setGameScore(GameScore gameScore) {
        this.gameScore = gameScore;
    }
    public GameScore getGameScore() {
        return gameScore;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public int getKill() {
        return kill;
    }

    public void addKill(int kill) {
        this.kill += kill;
    }

    public int getNuclearBombNumber() {
        return nuclearBombNumber;
    }

    public int getRocketNumber() {
        return rocketNumber;
    }

    public void reduceNuclearBomb() {
        nuclearBombNumber--;
    }

    public void addNuclearBomb() {
        nuclearBombNumber++;
    }

    public void addCluster() {
        clusterBombNumber++;
    }

    public void reduceCluster() {
        clusterBombNumber--;
    }

    public int getClusterBombNumber() {
        return clusterBombNumber;
    }

    public void increaseShootingCount() {
        shootingCount++;
    }

    public void increaseSuccessfulShootingCount() {
        successfulShootingCount++;
    }

    public void reduceClusterBomb() {
        clusterBombNumber--;
    }

    public int getAccuracy() {
        if (shootingCount == 0) {
            this.accuracy = 0;
            return 0;
        }
        this.accuracy = (successfulShootingCount * 100) / shootingCount;
        return this.accuracy;
    }

    public void setShootingCount(int i) {
        shootingCount = i;
    }

    public void setSuccessfulShootingCount(int i) {
        successfulShootingCount = i;
    }
}
