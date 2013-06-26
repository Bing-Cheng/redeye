package shutterfly.com;
////github
import java.awt.Color;
import java.awt.Container;
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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
	 static JButton eyeButton;
	 static JButton pickButton;
	 static JButton moveButton;
	 static JButton clearButton;
	 static Boolean pickEye;
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
	RedeyeReduction originalImage;
	public class MouseMotionEvent implements MouseMotionListener {

		@Override
		public void mouseDragged(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("git Drag X = " + e.getX() +"   Y = " + e.getY());
			if (moveImage == true) {
				int X = e.getX() - startX;
				int Y = e.getY() - startY;
				System.out.println("Drag enter Y= " + Y +"   startY = " + startY+"   getY = " + e.getY());
				if (Math.abs(X)>10||Math.abs(Y)>10){
					offsetX = offsetX + X;
					offsetY = offsetY +Y;
					System.out.println("Drag enter offsetX = " + offsetX +"   offsetY = " + offsetY);
					originalImage = new RedeyeReduction(imgOriginal,offsetX,offsetY);

					System.out.println("Drag got image X = " + e.getX() +"   Y = " + e.getY());
					originalArea.removeAll();
					originalArea.add(originalImage);
					System.out.println("Drag added to panel X = " + e.getX() +"   Y = " + e.getY());
					RedeyeReduction imgOriginalClone= new RedeyeReduction(imgOriginal,offsetX,offsetY);
					processedArea.removeAll();
					processedArea.add(imgOriginalClone);
					displayImages();
					startX = e.getX();
					startY = e.getY();
					System.out.println("Drag leave Y= " + Y +"   startY = " + startY+"   getY = " + e.getY());

				}
			}
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
		
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mousePressed");
		System.out.println(" X = " + e.getX() +"   Y = " + e.getY());
		startX = e.getX();
		  startY = e.getY();
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouseReleased");
		System.out.println(" X = " + e.getX() +"   Y = " + e.getY());
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
			  offsetX = 0;
			  offsetY = 0;
			  startX = 0;
			  startY = 0;
			  pickEye = false;
			  moveImage = false;
		  f = new JFrame("Redeye Reduction");
 		 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.setSize(1800, 800);
	         content = f.getContentPane();
	        content.setBackground(Color.white);
	        openButton = new JButton("Open Image");
	        pickButton = new JButton("Pick Eyes");
	        
	        clearButton = new JButton("Clear List");
	        moveButton = new JButton("Move Image");
			defaultButtonColor = pickButton.getBackground();
	        eyeButton = new JButton("Reduce Redeye");
	        buttonsPanel = new JPanel();
	        buttonsPanel.setSize(800,100);
	        buttonsPanel.add(openButton);
	        buttonsPanel.add(pickButton);
	        buttonsPanel.add(clearButton);
	        buttonsPanel.add(moveButton);
	        buttonsPanel.add(eyeButton);
	        originalArea = new JPanel();
	        originalArea.setSize(800, 600);
	        processedArea = new JPanel();
	        processedArea.setSize(800, 600);
	        model = new DefaultListModel();
//	        model.addElement("This is a short text");
//	        model.addElement("This is a long text. This is a ");
//	        model.addElement("This is an even longer text. T ev. r t");
//	        model.addElement("This is a short text");
//	        model.addElement("This is a short text");
//	        model.addElement("This is a long text. This is a ");
//	        model.addElement("This is an even longer text. T ev. r t");
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
				        try {
				        	System.out.println(fName );
				        	imgOriginal = ImageIO.read(new File(fName));
				            originalImage = new RedeyeReduction(imgOriginal,offsetX,offsetY);

				            originalArea.removeAll();
				            originalArea.add(originalImage);
				            RedeyeReduction imgOriginalClone= new RedeyeReduction(imgOriginal,offsetX,offsetY);
				            processedArea.removeAll();
				            processedArea.add(imgOriginalClone);
				        } catch (IOException ex) {
				     	   System.out.println(fName + "does not exist");
				        }
				        displayImages();
				    }
				}
			});
	        
	        pickButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("pick pressed");
	
				    if (pickEye == true) {
				    	pickEye = false;
				    	pickButton.setBackground(defaultButtonColor);
				    	originalImage.removeMouseListener(mouseEvent);
				    	originalImage.removeMouseMotionListener(mouseMotionEvent);
				    }
				    else{
				    	pickEye = true;
				    	pickButton.setBackground(defaultButtonColor.brighter());
				    	moveImage = false;
				    	moveButton.setBackground(defaultButtonColor);
				    	originalArea.removeMouseListener(mouseEvent);
			            originalArea.removeMouseMotionListener(mouseMotionEvent);
			            originalImage.addMouseListener(mouseEvent);
			            originalImage.addMouseMotionListener(mouseMotionEvent);
				    	model.addElement("This is a short text");
				    }
				}
			});
	        
	        moveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				    if (moveImage == true) {
				    	moveImage = false;
				    	moveButton.setBackground(defaultButtonColor);
				    	originalArea.removeMouseListener(mouseEvent);
			            originalArea.removeMouseMotionListener(mouseMotionEvent);
				    }
				    else{
				    	moveImage = true;
				    	moveButton.setBackground(defaultButtonColor.brighter());
				    	pickEye = false;
				    	pickButton.setBackground(defaultButtonColor);
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
				}
			});
	        
	        eyeButton.addActionListener(new ActionListener() {
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
		        RedeyeReduction processedImage = new RedeyeReduction(imgProcessed,offsetX,offsetY);
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
