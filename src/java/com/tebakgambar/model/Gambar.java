package com.tebakgambar.model;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Model Gambar
 * 
 * @author Ade Fruandta
 */
public class Gambar {
    
    private Image image;
    
    public Gambar(byte[] bytes, boolean isThumbnail) throws IOException{
        this.image = getImage(bytes, isThumbnail);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    private Image getImage(byte[] bytes, boolean isThumbnail) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        if (isThumbnail) {
            param.setSourceSubsampling(4, 4, 0, 0);
        }
        return reader.read(0, param);
    }
    
    public static BufferedImage cropMyImage(BufferedImage img, int cropWidth,
            int cropHeight, int cropStartX, int cropStartY) throws Exception {
        BufferedImage clipped;
        Dimension size = new Dimension(cropWidth, cropHeight);

        Rectangle clip = createClip(img, size, cropStartX, cropStartY);

        try {
            int w = clip.width;
            int h = clip.height;

            System.out.println("Crop Width " + w);
            System.out.println("Crop Height " + h);
            System.out.println("Crop Location " + "(" + clip.x + "," + clip.y
                    + ")");

            clipped = img.getSubimage(clip.x, clip.y, w, h);

            System.out.println("Image Cropped. New Image Dimension: "
                    + clipped.getWidth() + "w X " + clipped.getHeight() + "h");
        } catch (RasterFormatException rfe) {
            System.out.println("Raster format error: " + rfe.getMessage());
            return null;
        }
        return clipped;
    }

    /**
     * <span id="IL_AD10" class="IL_AD">This method</span> crops an
     * <span id="IL_AD6" class="IL_AD">original image</span> to the crop
     * parameters provided.
     *
     * If the crop rectangle lies outside the rectangle (even if partially),
     * adjusts the rectangle to be included within
     * <span id="IL_AD4" class="IL_AD">the image</span> area.
     *
     * @param img = Original Image To Be Cropped
     * @param size = Crop area rectangle
     * @param clipX = Starting X-position of crop area rectangle
     * @param clipY = Strating Y-position of crop area rectangle
     * @throws Exception
     */
    private static Rectangle createClip(BufferedImage img, Dimension size,
            int clipX, int clipY) throws Exception {
        Rectangle clip;
        
        /**
         * Some times clip area might lie outside the original image, fully or
         * partially. In such cases, this program will adjust the crop area to
         * fit within the original image.
         *
         * isClipAreaAdjusted flas is usded to denote if there was any
         * adjustment made.
         */
        boolean isClipAreaAdjusted = false;

        /**
         * <span id="IL_AD11" class="IL_AD">Checking</span> for negative X
         * Co-ordinate*
         */
        if (clipX < 0) {
            clipX = 0;
            isClipAreaAdjusted = true;
        }
        /**
         * Checking for negative Y Co-ordinate*
         */
        if (clipY < 0) {
            clipY = 0;
            isClipAreaAdjusted = true;
        }

        /**
         * Checking if <span id="IL_AD5" class="IL_AD">the clip</span> area lies
         * outside the rectangle*
         */
        if ((size.width + clipX) <= img.getWidth()
                && (size.height + clipY) <= img.getHeight()) {

            /**
             * Setting up a clip rectangle when clip area lies within the image.
             */
            clip = new Rectangle(size);
            clip.x = clipX;
            clip.y = clipY;
        } else {

            /**
             * Checking if the width of the clip area lies outside the image. If
             * so, making the image width boundary as the clip width.
             */
            if ((size.width + clipX) > img.getWidth()) {
                size.width = img.getWidth() - clipX;
            }

            /**
             * Checking if the height of the clip area lies outside the image.
             * If so, making the image height boundary as the clip height.
             */
            if ((size.height + clipY) > img.getHeight()) {
                size.height = img.getHeight() - clipY;
            }

            /**
             * Setting up the clip are based on our clip area size adjustment*
             */
            clip = new Rectangle(size);
            clip.x = clipX;
            clip.y = clipY;

            isClipAreaAdjusted = true;

        }
        if (isClipAreaAdjusted) {
            System.out.println("Crop Area Lied Outside The Image."
                    + " Adjusted The Clip Rectangle\n");
        }
        return clip;
    }
    
}
