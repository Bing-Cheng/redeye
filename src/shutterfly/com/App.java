package shutterfly.com;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shutterfly.com.RedeyeReduction;

public class App {
	static BufferedImage imgOriginal;
	 static JPanel processedArea;
	 static JPanel originalArea;
	 static Container content;
	 static JButton openButton;
	 static JButton processEyesButton;
	 static JButton pickEyesButton;
	 static JButton pickColorButton;
	 static JButton moveButton;
	 static JButton clearButton;
	 static JLabel colorLabel;
	 static Boolean pickEye;
	 static Boolean pickColor;
	 static Boolean moveImage;
	 static JFrame f;
	 static GroupLayout layout;
	 static JList eyeLocList;
	 static JScrollPane listPane;
	 static JPanel listPanel;
	 static JPanel buttonsPanel;
	static	Color defaultButtonColor;
	static  DefaultListModel model;
	static MouseEvent mouseEvent;
	static MouseMotionEvent mouseMotionEvent;
	static int offsetX;
	static int offsetY;
	static int startX;
	static int startY;
	static RedeyeReduction originalImage;
	static RedeyeReduction processedImage;
	static ArrayList<EyeLocation> eyeLocations;
	static Boolean locPicked;
	static EyeLocation eyeLoc;
	public class MouseMotionEvent implements MouseMotionListener {

		@Override
		public void mouseDragged(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Drag enter offsetX = " + offsetX +"   offsetY = " + offsetY);
			System.out.println("Drag X = " + e.getX() +"   Y = " + e.getY());
			
			if (moveImage == true) {
				int X = e.getX() - startX;
				int Y = e.getY() - startY;
				System.out.println("Drag enter Y= " + Y +"   startY = " + startY+"   getY = " + e.getY());
				if (Math.abs(X)>10||Math.abs(Y)>10){
					offsetX = offsetX + X;
					offsetY = offsetY +Y;
					System.out.println("Drag enter offsetX = " + offsetX +"   offsetY = " + offsetY);
					//originalImage = new RedeyeReduction(imgOriginal,offsetX,offsetY,locPicked, eyeLocations);
					originalImage.setParam(offsetX,offsetY,locPicked, eyeLocations);
					originalImage.repaint();
					processedImage.setParam(offsetX,offsetY,locPicked, eyeLocations);
					processedImage.repaint();
					startX = e.getX();
					startY = e.getY();
					System.out.println("Drag leave Y= " + Y +"   startY = " + startY+"   getY = " + e.getY());

				}
				
			}else if (pickEye == true) {
				int xMin, yMin, width, height;
				if (e.getX()> startX){
					xMin = startX;
					width = e.getX()- startX;
				}else{
					xMin = e.getX();
					width = startX - e.getX();
				}
				if (e.getY()> startY){
					yMin = startY;
					height = e.getY()- startY;
				}else{
					yMin = e.getY();
					height = startY - e.getY();
				}
				eyeLoc.set(xMin,yMin,width,height);
				originalImage.setParam(offsetX,offsetY,locPicked, eyeLocations);
				originalImage.repaint();
			}//pickEye
		}

		@Override
		public void mouseMoved(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Move X = " + e.getX() +"   Y = " + e.getY());
		}
	
	}
	public class MouseEvent implements MouseListener {

   	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		System.out.println("mouseClicked");
		if (pickColor == true) {
			int rgb = imgOriginal.getRGB(e.getX() - offsetX, e.getY() - offsetY);
			Color bg = new Color(rgb);
			colorLabel.setBackground(bg);
		}
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mousePressed");
		System.out.println(" X = " + e.getX() +"   Y = " + e.getY());
		startX = e.getX();
		startY = e.getY();
        eyeLoc = new EyeLocation(startX,startY,0,0);
        eyeLocations.add(eyeLoc);
        locPicked = true;
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouseReleased");
		if (pickEye)
		model.addElement(model.getSize() + ". Eye location: x = " + eyeLoc.x + ";  y = " + eyeLoc.y + ";  width = " + eyeLoc.width + ";  height = " + eyeLoc.height);
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouseEntered");
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouseExited");
	}
}

	 private App() {
			  initUI();
		  }
		  private void initUI() { 
			  locPicked = false;
			  eyeLocations = new ArrayList<EyeLocation>();
			  offsetX = 0;
			  offsetY = 0;
			  startX = 0;
			  startY = 0;
			  pickEye = false;
			  pickColor = false;
			  moveImage = false;
		  f = new JFrame("Redeye Reduction");
 		 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.setSize(1800, 800);
	         content = f.getContentPane();
	        content.setBackground(Color.white);
	        openButton = new JButton("Open Image");
	        pickEyesButton = new JButton("Pick Eyes");
	        pickColorButton = new JButton("Pick Color");
	        clearButton = new JButton("Clear List");
	        moveButton = new JButton("Move Image");
			defaultButtonColor = pickEyesButton.getBackground();
	        processEyesButton = new JButton("Process Eyes");
	        pickEyesButton.setEnabled(false);
	        pickColorButton.setEnabled(false);
	        clearButton.setEnabled(false);
	        moveButton.setEnabled(false);
	        processEyesButton.setEnabled(false);
	        colorLabel = new JLabel();
	        colorLabel.setPreferredSize(new Dimension(30,30));
	        Border border = BorderFactory.createLineBorder(Color.black);
	        colorLabel.setBorder(border);
	        colorLabel.setBackground(Color.white);

	        buttonsPanel = new JPanel();
	        buttonsPanel.setSize(800,100);
	        GridLayout bLayout = new GridLayout(2,0);
	        buttonsPanel.setLayout(bLayout);
	        buttonsPanel.add(openButton);
	        buttonsPanel.add(pickEyesButton);
	        buttonsPanel.add(pickColorButton);
	        buttonsPanel.add(colorLabel);
	        buttonsPanel.add(clearButton);
	        buttonsPanel.add(moveButton);
	        buttonsPanel.add(processEyesButton);
	        originalArea = new JPanel();
	        originalArea.setPreferredSize(new Dimension(800, 600));
	        processedArea = new JPanel();
	        processedArea.setPreferredSize(new Dimension(800, 600));
	        model = new DefaultListModel();
	        eyeLocList = new JList(model);
	        eyeLocList.setVisibleRowCount(6);
	        eyeLocList.setSize(800,100);
Font displayFont = new Font("Serif", Font.BOLD, 18);
//eyeLocList.setFont(displayFont);
eyeLocList.addListSelectionListener(new ValueReporter());
 listPane = new JScrollPane(eyeLocList);
 listPanel = new JPanel();
 listPanel.setSize(800,100);
 listPanel.setBackground(Color.white);
 Border listPanelBorder =
   BorderFactory.createTitledBorder("Eye Locations");
 listPanel.setBorder(listPanelBorder);
 listPanel.add(listPane);
	        layout = new GroupLayout(content);
	        content.setLayout(layout);
	       layout.setAutoCreateGaps(true);
	       layout.setAutoCreateContainerGaps(true);
	       displayImages();
	        f.setVisible(true);
	        
	        
	        openButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("open pressed");
					JFileChooser openFile = new JFileChooser();
				    if (openFile.showOpenDialog(openButton) == JFileChooser.APPROVE_OPTION) {
				        File file = openFile.getSelectedFile();
				        String fName = file.toString();
				        System.out.println(fName);
				        mouseEvent = new MouseEvent();
				        mouseMotionEvent = new MouseMotionEvent();
				        pickEyesButton.setEnabled(true);
				        pickColorButton.setEnabled(true);
				        clearButton.setEnabled(true);
				        moveButton.setEnabled(true);
				        processEyesButton.setEnabled(true);
				        try {
				        	System.out.println(fName );
				        	imgOriginal = ImageIO.read(new File(fName));
				            originalImage = new RedeyeReduction(imgOriginal,offsetX,offsetY,locPicked, eyeLocations);
				            originalArea.removeAll();
				            originalArea.add(originalImage);
				            processedImage= new RedeyeReduction(imgOriginal,offsetX,offsetY,locPicked, eyeLocations);
				            processedArea.removeAll();
				            processedArea.add(processedImage);
				        } catch (IOException ex) {
				     	   System.out.println(fName + "does not exist");
				        }
				        displayImages();
				    }
				}
			});
	        
	        pickEyesButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("pick pressed");
	
				    if (pickEye == true) {
				    	pickEye = false;
				    	pickEyesButton.setBackground(defaultButtonColor);
				    	originalImage.removeMouseListener(mouseEvent);
				    	originalImage.removeMouseMotionListener(mouseMotionEvent);
				    }
				    else{
				    	pickEye = true;
				    	pickEyesButton.setBackground(defaultButtonColor.brighter());
				    	moveImage = false;
				    	originalArea.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				    	moveButton.setBackground(defaultButtonColor);
				    	originalArea.removeMouseListener(mouseEvent);
			            originalArea.removeMouseMotionListener(mouseMotionEvent);
			            originalImage.addMouseListener(mouseEvent);
			            originalImage.addMouseMotionListener(mouseMotionEvent);
				    }
				}
			});
	        pickColorButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

				    if (pickColor == true) {
				    	pickColor = false;
				    	pickColorButton.setBackground(defaultButtonColor);
				    	colorLabel.setOpaque(false);
				    }
				    else{
				    	pickColor = true;
				    	pickColorButton.setBackground(defaultButtonColor.brighter());
				    	colorLabel.setOpaque(true);
				    	originalArea.removeMouseListener(mouseEvent);
			            originalImage.addMouseListener(mouseEvent);
				    }
				}
			});
	        
	        moveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				    if (moveImage == true) {
				    	originalArea.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				    	moveImage = false;
				    	moveButton.setBackground(defaultButtonColor);
				    	originalArea.removeMouseListener(mouseEvent);
			            originalArea.removeMouseMotionListener(mouseMotionEvent);
				    }
				    else{
				    	originalArea.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				    	moveImage = true;
				    	moveButton.setBackground(defaultButtonColor.brighter());
				    	pickEye = false;
				    	pickEyesButton.setBackground(defaultButtonColor);
				    	originalImage.removeMouseListener(mouseEvent);
				    	originalImage.removeMouseMotionListener(mouseMotionEvent);
				    	originalArea.addMouseListener(mouseEvent);
			            originalArea.addMouseMotionListener(mouseMotionEvent);
				    }
				}
			});
	        
	        clearButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				    	model.removeAllElements();
				    	eyeLocations.clear();
				    	originalImage.setParam(offsetX,offsetY,locPicked, eyeLocations);
						originalImage.repaint();
				}
			});
	        
	        processEyesButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("eye pressed");
					BufferedImage imgProcessed; 
					imgProcessed= new BufferedImage(imgOriginal.getWidth(), imgOriginal.getHeight(), BufferedImage.TYPE_INT_ARGB);
					imgProcessed.setData(imgOriginal.getData());
					for(int i = 0;i<200;i++){
						for(int j = 0;j<300;j++){
							int rgb = imgOriginal.getRGB(i, j);
							int rgb1 = 0xffff0000;
							imgProcessed.setRGB(i, j, rgb1);
						}
					}
		        RedeyeReduction processedImage = new RedeyeReduction(imgProcessed,offsetX,offsetY,locPicked, eyeLocations);
		        processedArea.removeAll();
		        processedArea.add(processedImage);
		        displayImages();
				}
			});
	}//initUI
		  
	protected static void displayImages() {
       layout.setHorizontalGroup(
          layout.createSequentialGroup()
             .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                  .addComponent(buttonsPanel,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                          GroupLayout.PREFERRED_SIZE)
                  .addComponent(originalArea))
             .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                  .addComponent(listPane)
                  .addComponent(processedArea))
       );
       layout.setVerticalGroup(
          layout.createSequentialGroup()
             .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(buttonsPanel,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                          GroupLayout.PREFERRED_SIZE)
                  .addComponent(listPane,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                          GroupLayout.PREFERRED_SIZE)
                  )
             .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(originalArea)
                  .addComponent(processedArea)
                  )
       );
	}
	  private class ValueReporter implements ListSelectionListener {
		    public void valueChanged(ListSelectionEvent event) {
		     // if (!event.getValueIsAdjusting()) 
		      //  valueField.setText(eyeLocList.getSelectedValue().toString());
		    }
		  }
	  
	 public static void main(String[] args) {
		    SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        App app = new App();
		      }
		    });
		  }

}
