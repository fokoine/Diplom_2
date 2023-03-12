import org.apache.commons.lang3.RandomStringUtils;

public class UserData {

    public String randomEmail;
    public String randomPassword;
    public String randomUsername;
    public String newRandomUsername;
    public String newRandomPassword;
    public String newRandomEmail;

    public UserData() {
        this.randomEmail = RandomStringUtils.randomAlphabetic(8);
        this.randomPassword = RandomStringUtils.randomAlphabetic(8);
        this.randomUsername = RandomStringUtils.randomAlphabetic(8);
        this.newRandomUsername = RandomStringUtils.randomAlphabetic(8);
        this.newRandomEmail = RandomStringUtils.randomAlphabetic(8);
        this.newRandomPassword = RandomStringUtils.randomAlphabetic(8);
    }
}
