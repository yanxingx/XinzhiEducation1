package bean;

public class UserInfo {
	private int id;
	private String username;
	private String password;
	private String token;
	private int isTeacher;
	private int heartCoin;
	private String nickName;
	public UserInfo(){}
	public UserInfo(int id,String username,String password){
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public UserInfo(int id, String username,String nickName,int heartCoin) {
		super();
		this.id = id;
		this.username = username;
		this.heartCoin = heartCoin;
		this.nickName = nickName;
	}
	public UserInfo(int id, String username, String token, int isTeacher,
			int heartCoin, String nickName) {
		super();
		this.id = id;
		this.username = username;
		this.token = token;
		this.isTeacher = isTeacher;
		this.heartCoin = heartCoin;
		this.nickName = nickName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getIsTeacher() {
		return isTeacher;
	}
	public void setIsTeacher(int isTeacher) {
		this.isTeacher = isTeacher;
	}
	public int getHeartCoin() {
		return heartCoin;
	}
	public void setHeartCoin(int heartCoin) {
		this.heartCoin = heartCoin;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
