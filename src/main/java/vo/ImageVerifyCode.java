package vo;

/**
 * author:      shfq
 * create date: 2015/12/14.
 */
public class ImageVerifyCode {
    private final String verifyCode;
    private final long generateTime;
    private String errorMessage;

    public ImageVerifyCode(String verifyCode) {
        if (isBlank(verifyCode)) {
            throw new IllegalArgumentException("验证码不能为空");
        }

        this.verifyCode = verifyCode;
        this.generateTime = System.currentTimeMillis();
    }

    public boolean isVerifyCodeLegal(String verifyCode, int expiredMinutes) {
        if (!(this.verifyCode.equalsIgnoreCase(verifyCode))) {
            this.errorMessage = "验证码不正确";
            return false;
        }
        if (isVerifyCodeTimeOutOfDate(expiredMinutes)) {
            this.errorMessage = "验证码已超时";
            return false;
        }
        return true;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    private boolean isVerifyCodeTimeOutOfDate(int expiredMinutes) {
        return (System.currentTimeMillis() - this.generateTime > 60000 * expiredMinutes);
    }

    private boolean isBlank(String s) {
        if (s == null || s.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        double d1 = 20;
        double d2 = 20.00000;
        System.out.println(d1 == d2);
    }
}
