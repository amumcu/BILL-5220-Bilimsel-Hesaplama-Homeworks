package bill5220.bilimsel_hesaplama.image_editing.poission;//A GUI for selecting, resizing, rotating, and flipping images that are
//used in the Poisson Image Editing application
//(c) Chris Tralie, 2012
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JOptionPane;
import java.awt.image.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImageSelector extends JFrame implements ActionListener, DocumentListener {
	public JButton selectFileButton;
	public JButton selectImageButton;

	public Display canvas;
	public BufferedImage currentImage = null;
	public BufferedImage originalImage = null;
	public double scale = 1.0;
	public double rot = 0.0;
	
	public static String lastFilename = "";

    public ImageSelector() {
    	Container content = getContentPane();
    	content.setLayout(new BorderLayout());
    	
    	JPanel selectionFrame = new JPanel();
    	selectionFrame.setLayout(new GridLayout(5, 1));


    	selectFileButton = new JButton("Select File from Your Computer");
    	selectFileButton.addActionListener(this);
    	selectionFrame.add(selectFileButton);


    	

    	content.add(selectionFrame, BorderLayout.NORTH);
    	canvas = new Display();
    	content.add(canvas, BorderLayout.CENTER);
    	selectImageButton = new JButton("Select Image");
    	selectImageButton.addActionListener(this);
    	content.add(selectImageButton, BorderLayout.SOUTH);
    	
    	setSize(800, 800);
    	show();
    }

	public void insertUpdate(DocumentEvent e) {

	}

	public void removeUpdate(DocumentEvent e) {

	}

	public void changedUpdate(DocumentEvent e) {

	}

	public class Display extends JPanel {
    	public void paintComponent(Graphics g) {
    		g.setColor(Color.WHITE);
    		g.fillRect(0, 0, 1000, 1000);
    		if (currentImage != null)
    			g.drawImage(currentImage, 0, 0, this);
    		else {
    			g.setColor(Color.red);
    			g.drawString("Preview Image Here", 200, 200);
    		}
    	}
    }
    
    
    //Helper functions for determining how to resize an image around a rotation
    //(similar to code I wrote for Princeton COS 426 Assignment 1)
    double getMax(double[] a) {
    	double max = a[0];
    	for (int i = 1; i < a.length; i++) {
    		if (a[i] > max)
    			max = a[i];
    	}
    	return max;
    }
    double getMin(double[] a) {
    	double min = a[0];
    	for (int i = 1; i < a.length; i++) {
    		if (a[i] < min)
    			min = a[i];
    	}
    	return min;    	
    }
    public int getNewWidth(double w, double h, double theta) {
    	double cosT = Math.cos(theta), sinT = Math.sin(theta);
    	double[] candidates = {0, w*cosT, w*cosT-h*sinT, -h*sinT};
    	return (int)(getMax(candidates) - getMin(candidates));
    }
    public int getNewHeight(double w, double h, double theta) {
    	double cosT = Math.cos(theta), sinT = Math.sin(theta);
    	double[] candidates = {0, w*sinT, w*sinT+h*cosT, h*cosT};
    	return (int)(getMax(candidates) - getMin(candidates));
    }
    public int getHorizBias(double w, double h, double theta) {
    	double cosT = Math.cos(theta), sinT = Math.sin(theta);
    	double[] candidates = {0, w*cosT, w*cosT-h*sinT, -h*sinT};
    	return (int)getMin(candidates);
    }
    public int getVertBias(double w, double h, double theta) {
    	double cosT = Math.cos(theta), sinT = Math.sin(theta);
    	double[] candidates = {0, w*sinT, w*sinT+h*cosT, h*cosT};
    	return (int)getMin(candidates);
    }    
    
    public void updateImage(int typedText) {
    	if (originalImage == null)
    		return;
    	double w = scale*originalImage.getWidth();
    	double h = scale*originalImage.getHeight();
    	if (w < 4 || h < 4)
    		return;
    	double theta = rot*Math.PI/180;
    	int width = getNewWidth(w, h, theta);
    	int height = getNewHeight(w, h, theta);
    	int dx = -getHorizBias(w, h, theta);
    	int dy = -getVertBias(w, h, theta);
    	BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	Graphics2D graphics = scaledImage.createGraphics();
    	graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    	graphics.translate(dx, dy);
    	graphics.rotate(theta);
    	graphics.drawImage(originalImage, 0, 0, (int)Math.round(w), (int)Math.round(h), null);
    	graphics.dispose();
    	currentImage = scaledImage;
    	if (typedText != 1)
    	if (typedText != 2)
    	canvas.repaint();
    }
    

    
    public void actionPerformed(ActionEvent evt) {

		if (evt.getSource() == selectImageButton) {
			dispose();
    	}

    	else if (evt.getSource() == selectFileButton) {
    		try {
	    		JFileChooser chooser = new JFileChooser(lastFilename);
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					lastFilename = chooser.getSelectedFile().getPath();
					originalImage = ImageIO.read(chooser.getSelectedFile());
					scale = 1.0;
					rot = 0.0;
					updateImage(0);
				}
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    		}   		
    	}
    	canvas.repaint();
    }

}