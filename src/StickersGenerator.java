import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class StickersGenerator {
    

    //create an image
    public void create(InputStream inputStream, String fileName) throws Exception {

        //read an image file from local
        BufferedImage originalImage = ImageIO.read(inputStream);
        
        //create a new image with new size and alpha transparency
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        int newHeight = height + 200;
        BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);

        //copy the original image to the new image
        Graphics2D graphics = (Graphics2D) newImage.getGraphics();
        graphics.drawImage(originalImage, 0, 0, null);

        //configure text font
        var my_font = new Font("Comic Sans MS", Font.BOLD, 64);
        graphics.setFont(my_font);
        graphics.setColor(Color.YELLOW);

        //write a funny phrase inside the new image
        graphics.drawString("BEST MOVIE2", 100, newHeight - 100);


        //write new image in a new file
        File outputFile = new File("output/" + fileName + ".png");
        outputFile.getParentFile().mkdirs();
        ImageIO.write(newImage, "png", outputFile);

    }
}
