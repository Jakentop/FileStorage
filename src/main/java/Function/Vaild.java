package Function;

/**
 * 这是验证类，由于遵循前端不可信原则，所有的代码在后端都需要验证，因此将一些常用方法封装在这里
 * @author zyz
 */
public abstract class Vaild {

    public static boolean E_mail(String e_mail) {
        String EMAIL_REGEX = "^[\\w-\\.+]*[\\w-\\.]\\@([\\w-]+\\.)+[\\w]+[\\w]{1}";
        return e_mail.matches(EMAIL_REGEX);
    }

}
