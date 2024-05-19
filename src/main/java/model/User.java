package model;

public class User {
    private String username;
    private String password;
    private boolean isGuest;
    private int kill;
    private int lives = 10;
    private String avatarPath;
    private int score = 0;
    private int nuclearBombNumber = 0;
    private int rocketNumber = 1;
    private int clusterBombNumber = 0;
    private int shootingCount;
    private int successfulShootingCount;
    private int accuracy;
    public User(String username, String password, boolean isGuest, String avatarPath) {
        this.username = username;
        this.password = password;
        this.isGuest = isGuest;
        this.avatarPath = avatarPath;
        kill = 0;
        shootingCount = 0;
        successfulShootingCount = 0;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getAvatarPath() {
        return avatarPath;
    }
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getKill() {
        return kill;
    }
    public void addKill(int kill){
        this.kill += kill;
    }
    public int getNuclearBombNumber() {
        return nuclearBombNumber;
    }

    public int getRocketNumber() {
        return rocketNumber;
    }
   public void reduceNuclearBomb(){
        nuclearBombNumber--;
   }
    public void addNuclearBomb(){
        nuclearBombNumber++;
    }
    public void addCluster(){
        clusterBombNumber++;
    }
    public void reduceCluster(){
        clusterBombNumber--;
    }
    public int getClusterBombNumber() {
        return clusterBombNumber;
    }

    public int getLives() {
        return lives;
    }
    public void increaseShootingCount(){
        shootingCount++;
    }
    public void increaseSuccessfulShootingCount(){
        successfulShootingCount++;
    }
    public int getShootingCount() {
        return shootingCount;
    }
    public int getSuccessfulShootingCount() {
        return successfulShootingCount;
    }

    public void reduceClusterBomb() {
        clusterBombNumber--;
    }
    public int getAccuracy(){
        System.out.println(shootingCount);
        if (shootingCount == 0)
            return 0;
        return (successfulShootingCount * 100) / shootingCount;
    }

    public void setShootingCount(int i) {
        shootingCount = i;
    }

    public void setSuccessfulShootingCount(int i) {
        successfulShootingCount = i;
    }
}
