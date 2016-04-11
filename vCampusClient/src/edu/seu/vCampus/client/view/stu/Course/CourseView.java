package edu.seu.vCampus.client.view.stu.Course;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.CardLayout;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.CourseInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.CourseSelectionInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.module.CourseManager.CourseAdminModel;
import edu.seu.vCampus.client.biz.module.CourseManager.CourseSelectionModel;
import edu.seu.vCampus.client.view.stu.Library.LibraryView;
import edu.seu.vCampus.client.view.util.CustomizableTableModel;
import edu.seu.vCampus.client.view.util.FadingPanel;
import edu.seu.vCampus.client.view.util.MyScrollBarUI;

import javax.swing.JPanel;


import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;


public class CourseView extends FadingPanel {
    private static final long serialVersionUID = 1L;
    private CardLayout cardLayout = new CardLayout();
	public JButton btn_Return = new JButton();
	private JTable tblAvai, tblChosen;
	String Coursetype = null;

	/**
	 * Create the application.
	 */
	public CourseView() {	
		setBackground(Color.WHITE);
		setLayout(null);
		
		final JPanel CardCourse = new JPanel();
		CardCourse.setBackground(Color.WHITE);
		CardCourse.setBounds(10, 31, 620, 407);
		add(CardCourse);
		CardCourse.setLayout(cardLayout);

		//按下选课后的界面显示
		JPanel pal_ChooseCourse = new JPanel();
		pal_ChooseCourse.setBackground(Color.WHITE);
		CardCourse.add(pal_ChooseCourse, "1");
		pal_ChooseCourse.setLayout(null);
		
		JScrollPane scrollPane0 = new JScrollPane();
        scrollPane0.setBounds(0, 0, 620, 340);
        pal_ChooseCourse.add(scrollPane0);
        
        tblAvai = new JTable();
        scrollPane0.setViewportView(tblAvai);
        scrollPane0.getViewport().setBackground(Color.WHITE);
        scrollPane0.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane0.getVerticalScrollBar().setBackground(Color.WHITE);
        tblAvai.setTableHeader(null);
        tblAvai.setRowHeight(80);
        tblAvai.setRowSelectionAllowed(false);
        tblAvai.setShowGrid(false);
        scrollPane0.setBorder(new EmptyBorder(0, 0, 0, 0));
        /*scrollPane0.setLayout(new ScrollPaneLayout() {
            @Override
            public void layoutContainer(Container parent) {
              JScrollPane scrollPane = (JScrollPane)parent;

              Rectangle availR = scrollPane.getBounds();
              availR.x = availR.y = 0;

              Insets insets = parent.getInsets();
              availR.x = insets.left;
              availR.y = insets.top;
              availR.width  -= insets.left + insets.right;
              availR.height -= insets.top  + insets.bottom;

              Rectangle vsbR = new Rectangle();
              vsbR.width  = 12;
              vsbR.height = availR.height;
              vsbR.x = availR.x + availR.width - vsbR.width;
              vsbR.y = availR.y;

              if(viewport != null) {
                viewport.setBounds(availR);
              }
              if(vsb != null) {
                vsb.setVisible(true);
                vsb.setBounds(vsbR);
              }
            }
          });
          scrollPane0.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            private final Dimension d = new Dimension();
            @Override protected JButton createDecreaseButton(int orientation) {
              return new JButton() {
                @Override public Dimension getPreferredSize() {
                  return d;
                }
              };
            }
            @Override protected JButton createIncreaseButton(int orientation) {
              return new JButton() {
                @Override public Dimension getPreferredSize() {
                  return d;
                }
              };
            }
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {}
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
              Graphics2D g2 = (Graphics2D)g.create();
              g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                  RenderingHints.VALUE_ANTIALIAS_ON);
              Color color = null;
              JScrollBar sb = (JScrollBar)c;
              if(!sb.isEnabled() || r.width>r.height) {
                return;
              }else if(isDragging) {
                color = new Color(200,200,100,200);
              }else if(isThumbRollover()) {
                color = new Color(255,255,100,200);
              }else {
                color = new Color(220,220,200,200);
              }
              g2.setPaint(color);
              g2.fillRoundRect(r.x,r.y,r.width,r.height,10,10);
              g2.setPaint(Color.WHITE);
              g2.drawRoundRect(r.x,r.y,r.width,r.height,10,10);
              g2.dispose();
            }
            @Override
            protected void setThumbBounds(int x, int y, int width, int height) {
              super.setThumbBounds(x, y, width, height);
              scrollbar.repaint();
            }
          });*/
		
		//按下已选课程后的界面显示
		final JPanel pal_Choosed = new JPanel();
		pal_Choosed.setBackground(Color.WHITE);
		CardCourse.add(pal_Choosed, "2");
		pal_Choosed.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 620, 340);
		pal_Choosed.add(scrollPane);
		
		JLabel lblTip2 = new JLabel("左击以选中欲取消选择的课程，右击以确认");
        lblTip2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        lblTip2.setBounds(100, 335, 300, 40);
        pal_Choosed.add(lblTip2);
		
		tblChosen = new JTable();
		scrollPane.setViewportView(tblChosen);
		/*tblChosen.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    cancelCourse();
                }
            }
        });*/
		
		//所有按钮
		/*JPanel pal_btn = new JPanel();
		pal_btn.setBackground(Color.WHITE);
		pal_btn.setBounds(0, 31, 106, 407);
		add(pal_btn);
		pal_btn.setLayout(null);
		
		final JButton btn_ChooseCourse = new JButton("可选课程");
		btn_ChooseCourse.setContentAreaFilled(false);
		btn_ChooseCourse.setBorderPainted(true);
		btn_ChooseCourse.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_ChooseCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(CardCourse, "1");
			}
		});
					
		btn_ChooseCourse.setBounds(5, 83, 95, 33);
		pal_btn.add(btn_ChooseCourse);
			
		final JButton btn_Choosed = new JButton("已选课程");		
		btn_Choosed.setContentAreaFilled(false);
		btn_Choosed.setBorderPainted(true);
		btn_Choosed.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        btn_Choosed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(CardCourse, "2");
			}
		});
		
		btn_Choosed.setBounds(5, 230, 95, 33);
		pal_btn.add(btn_Choosed);*/
		
		updateCourses();
		// updateSelectedCourse();
	}
	
	/**
     * 刷新可选与已选课程
     */
    public void updateCourses() {
    	int nAvaiCourse = 0, nChosenCourse = 0;
        CourseInfoContainer[] cinfo = CourseAdminModel.viewAvaiCourseForMe();
        CourseSelectionInfoContainer[] csinfo = CourseSelectionModel.viewCourse();
        if (cinfo != null)
        	nAvaiCourse = cinfo.length;
        if (csinfo != null)
            nChosenCourse = csinfo.length;
        CourseInfoWithSelectionInfo[] ccsinfo = new CourseInfoWithSelectionInfo[nAvaiCourse + nChosenCourse];
        for (int i = 0 ; i < nAvaiCourse; ++i) {
        	ccsinfo[i] = new CourseInfoWithSelectionInfo();
        	ccsinfo[i].courseInfo = cinfo[i];
        }
        for (int i = 0 ; i < nChosenCourse; ++i) {
        	CourseInfoContainer cinfo0 = CourseAdminModel.viewCourse(csinfo[i].nTeacherID);
        	ccsinfo[i + nAvaiCourse] = new CourseInfoWithSelectionInfo();
        	ccsinfo[i + nAvaiCourse].courseInfo = cinfo0;
        	ccsinfo[i + nAvaiCourse].csInfo = csinfo[i];
        }
        /*Object[] name = { "课程名称", "任课教师", "上课学期", "已选人数", "最大可选人数", "学分", "课程类型" };
        Object[][] data = new Object[cinfo.length][name.length];
        for (int i = 0; i < cinfo.length; ++i) {
            data[i][0] = cinfo[i].strName;
            data[i][1] = cinfo[i].strIntro;
            data[i][2] = cinfo[i].nSemester;
            data[i][3] = cinfo[i].nStuNum;
            data[i][4] = cinfo[i].nMaxStuNum;
            data[i][5] = cinfo[i].fCredit;
            data[i][6] = courseSubj[cinfo[i].nTeacherID];
        }
        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(data, name);
        tblAvai.setModel(model);*/
        tblAvai.setModel(new CustomizableTableModel<CourseInfoWithSelectionInfo>(ccsinfo, CourseInfoWithSelectionInfo.class));
        tblAvai.setDefaultRenderer(CourseInfoWithSelectionInfo.class, new CourseCellRenderer(this));
        tblAvai.setDefaultEditor(CourseInfoWithSelectionInfo.class, new CourseCellRenderer(this));
    }
    
    /**
     * 刷新已选课程
     */
    /*public void updateSelectedCourse() {
        csinfo = CourseSelectionModel.viewCourse();
        if (csinfo == null)
            return;
        Object[] name = { "课程名称", "任课教师", "上课学期", "学分", "课程类型", "选课类型", "选课时间" };
        Object[][] data = new Object[csinfo.length][name.length];
        for (int i = 0; i < csinfo.length; ++i) {
            CourseInfoContainer cinfo = CourseAdminModel.viewCourse(csinfo[i].nTeacherID);
            data[i][0] = cinfo.strName;
            data[i][1] = cinfo.strIntro;
            data[i][2] = cinfo.nSemester;
            data[i][3] = cinfo.fCredit;
            data[i][4] = courseSubj[cinfo.nTeacherID];
            data[i][5] = csSubj[csinfo[i].nType];
            data[i][6] = csinfo[i].strDate;
        }
        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(data, name);
        tblChosen.setModel(model);
        tblChosen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
    }*/
    
    /**
     * 选择课程
     */
    /*private void chooseCourse() {
        int n = tblAvai.getSelectedRowCount();
        if (n != 1)
            return;
        MessageContainer ret = CourseSelectionModel.addCourse(cinfo[tblAvai.getSelectedRow()].nCourseID);
        if (ret.strCommand.equals(CommandProtocol.SUCCESS)) {
            updateAvailableCourse();
            // updateSelectedCourse();
        } else {
            if (ret.strParameters == null || ret.strParameters.length == 0)
                JOptionPane.showMessageDialog(null, "选课失败", "错误提示", JOptionPane.ERROR_MESSAGE);
            else {
                updateAvailableCourse();
                // updateSelectedCourse();
                switch (ret.strParameters[0]) {
                case CommandProtocol.CS_EXCEED:
                    JOptionPane.showMessageDialog(null, "名额已满！", "错误提示", JOptionPane.ERROR_MESSAGE);
                    break;
                case CommandProtocol.CS_UNAVAI:
                    JOptionPane.showMessageDialog(null, "无选此课程的资格！", "错误提示", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        }
    }*/
    
    /**
     * 取消选择课程
     */
    /*private void cancelCourse() {
        int n = tblChosen.getSelectedRowCount();
        if (n != 1)
            return;
        MessageContainer ret = CourseSelectionModel.delCourse(csinfo[tblChosen.getSelectedRow()].nCsID);
        if (ret.strCommand.equals(CommandProtocol.SUCCESS)) {
            updateAvailableCourse();
            // updateSelectedCourse();
        } else {
            if (ret.strParameters == null || ret.strParameters.length == 0)
                JOptionPane.showMessageDialog(null, "取消选课失败", "错误提示", JOptionPane.ERROR_MESSAGE);
            else {
                updateAvailableCourse();
                // updateSelectedCourse();
                switch (ret.strParameters[0]) {
                case CommandProtocol.CS_MANDOTARY:
                    JOptionPane.showMessageDialog(null, "无法取消选择必修课！", "错误提示", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        }
    }*/
}