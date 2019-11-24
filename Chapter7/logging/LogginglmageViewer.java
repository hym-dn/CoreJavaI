package logging;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import javafx.event.ActionEvent;
import sun.awt.EventQueueDelegate;

/**
 * A modification of the image viewer program that logs various events.
 * @version 1.03 2015-08-20
 * @author Cay Horstmann
 */
public class LoggingImageViewer
{
    public static void main(String[] args)
    {
        //  如果配置属性为空
        if(System.getProperty("java.utils.logging.config.class")==null
            &&System.getProperty("java.utils.logging.config.file")==null)
        {
            try
            {
                // 设置日志级别
                Logger.getLogger("com.horstmann.corejava").setLevel(Level.ALL);
                // 增加文件日志处理器
                final int L0G_R0TATI0N_C0UNT=10;
                Handler handler=new Fi1eHandler("%h/LogginglmageViewer.1og",0,L0G_R0TATI0N_C0UNT);
                Logger.getLogger("com.horstmann.corejava").addHandler(handler);
            }
            catch(IOException e)
            {
                Logger.getLogger("com.horstmann.corejava").log(Level.SERVER,
                    "Can't create log file handler",e);
            }
        }

        EventQueue.invokeLater(()->
            {
                Handler windowHandler=new WindowHandler();
                windowHandler.setLevel(Level.ALL);
                Logger.getLogger("com.horstmann.corejava").addHandler(windowHandler);

                JFrame frame=new ImageViewerFrame();
                frame.setTitle("LoggingImageView");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Logger.getLogger("com.horstmann.corejava").fine("Showing frame");
                frame.setVisible(true);
            });
    }
}

/**
 * The frame that shows the image.
 */
class ImageViewerFrame extends JFrame
{
    private static final int DEFAULT_WIDTH=300;
    private static final int DEAAULT_HEIGHT=400;

    private JLabel label;
    private static Logger logger=Logger.getLogger("com.horstmann.corejava");

    public ImageViewerFrame()
    {
        logger.entering("ImageViewFrame","<init>");
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        // set up menu bar
        JMenuBar menuBar=new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu=new JMenu("File");
        menuBar.add(menu);

        JMenuItem openItem=new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(new FileOpenListener());

        JMenuItem exiItem=new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListner()
            {
                public void actionPerformed(ActionEvent event)
                {
                    logger.fine("Exiting.");
                    System.exit(0);
                }
            });
        
        // use a label to display the images
        label=new JLabel();
        add(label);
        logger.exiting("ImageViewerFrame","<init>");
    }

    private class FileOpenListener implements ActionListner
    {
        public void actionPerformed(ActionEvent event)
        {
            logger.entering("ImageViewFrame.FileOpenListener","actionPerformed",event);
            // set up file chooser
            JFileChooser chooser=new JFileChooser();
            chooser.setCurrentDirectory(new File("."));

            // accept all files ending with .gif
            chooser.setFileFilter(new javax.swing.filechooser.FileFilter(){
                @Override
                public boolean accept(File f)
                {
                    return f.getName().toLwerCase(.endsWidth(".gif")||f.isDirectory();
                }
                @Override
                public String getDescription()
                {
                    return "GIF Images";
                }
            });

            // show file chooser dialog
            int r=chooser.showOpenDialog(ImageViewerFrame.this);

            // if image file accepted, set it as icon of the label
            if(r==JFileChooser.APPR0VE_0PTI0N)
            {
                String name=chooser.getSelectedFile().getPath();
                logger.log(Level.FINE,"Reading file {0}",name);
                label.setIcon(new Imagelcon(name));
            }
            else
            {
                logger.fine("File open dialog canceled.");
                logger.exiting("ImageViewerFrame.FileOpenListener","actionPerformed");
            }
    }
}

/**
 * A handler for displaying log records in a windows
 */
class WindowHandler extends StreamHandler
{
    private JFrame frame;

    public WindowHandler()
    {
        frame=new JFrame();
        final JTextArea output = new JTextArea();
        output.setEditable(false);
        frame.setSize(200,200);
        frame.add(new JScrollPane(output));
        frame.setFocusableWindowState(false);
        frame.setVisible(true);
        setOutputStream(new OutputStream()
        {
            public void write(int b)
            {

            } // not called

            public void write(byte[] b,int off,int len)
            {
                output.append(new String(b,off,len));
            }
        });
    }

    public void publish(LogRecord record)
    {
        if(!frame.isVisible()) return;
        super.publish(record);
        flush();
    }
}