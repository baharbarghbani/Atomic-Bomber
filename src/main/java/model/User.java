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
    public User(String username, String password, boolean isGuest, String avatarPath) {
        this.username = username;
        this.password = password;
        this.isGuest = isGuest;
        this.avatarPath = avatarPath;
        kill = 0;
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

    public int getLives() {
        return lives;
    }

}
