import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.*;

public class CS435StarCounter {

	public static void main(String[] args) throws IOException {
		System.out.println("hello world");
		BufferedImage image = ImageIO.read(new File("starsbw.jpg"));
		
		
		BufferedImage brightnessMap = createBrightnessMap(image);

		System.out.println("\n\nTotal Stars Found: "+countStars(brightnessMap));
		System.out.println("Two output images were made. output_uncounted.png and output_counted.png");

	}

	public static int countStars(BufferedImage im) {
		BufferedImage image = im;
		int w = image.getWidth();
		int h = image.getHeight();
		Color c = new Color(image.getRGB(0, 0));
		int starCount = 0;

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				
				if (image.getRGB(i, j) == -16777216
						&& !areAdjacentPixelsRed(image, i, j)) {
					starCount++;
					im.setRGB(i, j, Color.red.getRGB());
					System.out.println("new star found! star #"+starCount);
				} else if (image.getRGB(i, j) == -16777216
						&& areAdjacentPixelsRed(image, i, j)) {
					im.setRGB(i, j, Color.red.getRGB());
					
				}

			}
			
		}

		BufferedImage output = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		File outputfile = new File("output_counted.png");
		try {
			ImageIO.write(im, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done.");

		return starCount;
	}

	public static boolean areAdjacentPixelsRed(BufferedImage image, int x, int y) {
		if (y > 0) {
			if (x > 0) {
				if (image.getRGB(x - 1, y - 1) == -65536) {
					return true;
				}
			}
			if (image.getRGB(x, y - 1) == -65536) {
				return true;
			}
			if (x < image.getWidth()-1)
				if (image.getRGB(x + 1, y - 1) == -65536) {
					return true;
				}
		}

		if (x > 0) {
			if (image.getRGB(x - 1, y) == -65536) {
				return true;
			}
		}
		if (x < image.getWidth()-1) {
			if (image.getRGB(x + 1, y) == -65536) {
				return true;
			}
		}

		if (y < image.getHeight()-1) {
			if (x > 0) {
				if (image.getRGB(x - 1, y + 1) == -65536) {
					return true;
				}
			}
			if (image.getRGB(x, y + 1) == -65536) {
				return true;
			}
			if (x < image.getWidth()-1) {
				if (image.getRGB(x + 1, y + 1) == -65536) {
					return true;
				}
			}
		}
		return false;
	}

	public static float getLuminance(Color color) {
		float lum = color.getRed() * .21f + color.getGreen() * .71f
				+ color.getBlue() * .07f;

		return lum;

	}

	public static BufferedImage createBrightnessMap(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		int rgb = image.getRGB(0, 0);
		Color c = new Color(rgb);

		BufferedImage outputImage = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				rgb = image.getRGB(i, j);
				c = new Color(rgb);
				float luminance = getLuminance(c);

				// System.out.println(luminance);
				if (luminance > 150) {
					outputImage.setRGB(i, j, Color.black.getRGB());
				} else {
					outputImage.setRGB(i, j, Color.white.getRGB());
				}

			}
		}
		File outputfile = new File("output_uncounted.png");
		try {
			ImageIO.write(outputImage, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done.");
		return outputImage;
	}

}
