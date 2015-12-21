package servlets;

import constant.Constant;
import vo.ImageVerifyCode;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * author:      shfq
 * create date: 2015/12/10.
 */
public class ImgVerificationCodeGenerateServlet extends HttpServlet {
    private static final int width = 80;
    private static final int height = 36;
    private static final char[] codeArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    String[] fontTypes = { "宋体", "新宋体", "黑体", "楷体", "隶书" };



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0L);
        resp.setContentType("image/jpeg");

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        String verificationCode = createImgVerificationCodes(graphics);

        req.getSession().setAttribute(Constant.IMAGE_VERIFICATION_CODE, new ImageVerifyCode(verificationCode));

        graphics.dispose();
        OutputStream out = resp.getOutputStream();
        ImageIO.write(bufferedImage, "JPEG", out);
        out.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private String createImgVerificationCodes(Graphics graphics) {
        setBackground(graphics);
        addInterferenceLine(graphics);
        return setVerificationCode(graphics);
    }

    private Graphics setBackground(Graphics graphics) {
        graphics.setColor(generateRandColor(220, 250));
        graphics.fillRect(0, 0, width, height);
        return graphics;
    }

    private Graphics addInterferenceLine(Graphics graphics) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            graphics.setColor(generateRandColor(40, 150));
            graphics.drawLine(random.nextInt(width), random.nextInt(height),
                                 random.nextInt(width), random.nextInt(height));
        }
        return graphics;
    }

    private String setVerificationCode(Graphics graphics) {
        Random random = new Random();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 4; ++i) {
            String r = String.valueOf(codeArray[random.nextInt(codeArray.length)]);
            s.append(r);
            graphics.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
            graphics.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)], 1, 26));
            graphics.drawString(r, 15 * i + 5, 19 + random.nextInt(8));
        }
        return s.toString();

    }

    private Color generateRandColor(int lowBound, int upperBound) {
        int low = lowBound;
        int upper = upperBound;

        if (low < 0) {
            low = 0;
        } else if (low > 255){
            low = 255;
        }

        if (upper < 0) {
            upper = 0;
        } else if (low > 255) {
            upper = 255;
        }


        Random random = new Random();
        return new Color(low + random.nextInt(upper - low), low + random.nextInt(upper - low),
                low + random.nextInt(upper - low));
    }
}
