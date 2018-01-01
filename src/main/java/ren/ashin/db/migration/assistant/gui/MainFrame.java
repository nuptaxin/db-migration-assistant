package ren.ashin.db.migration.assistant.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.joda.time.format.DateTimeFormat;

import ren.ashin.db.migration.assistant.bean.ConnectionProperty;
import ren.ashin.db.migration.assistant.bean.TmpExpData;
import ren.ashin.db.migration.assistant.cache.Cache;
import ren.ashin.db.migration.assistant.gui.conn.ConnectionDialog;
import ren.ashin.db.migration.assistant.render.DisplayJFrame;
import ren.ashin.db.migration.assistant.util.TransferSizeUtil;

import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.google.common.collect.Lists;

/**
 * @ClassName: MainFrame
 * @Description: TODO
 * @author renzx
 * @date Dec 13, 2017
 */
public class MainFrame extends WebFrame {

    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = -6153787338032825926L;
    private static MainFrame mainFrame = null;
    // 菜单类
    private WebMenuBar menuBar = new WebMenuBar();
    private WebMenu menuFile = new WebMenu("文件");
    private WebMenu menuEdit = new WebMenu("编辑");
    private WebMenu menuView = new WebMenu("视图");
    private WebMenu menuHelp = new WebMenu("帮助");
    private WebMenuItem menuItemNew = new WebMenuItem("新建");
    private WebMenuItem menuItemManage = new WebMenuItem("管理");
    private WebMenuItem menuItemImp = new WebMenuItem("导入配置");
    private WebMenuItem menuItemExp = new WebMenuItem("导出配置");
    private WebMenuItem menuItemExit = new WebMenuItem("退出");
    private WebMenuItem menuItemCut = new WebMenuItem("剪切");
    private WebMenuItem menuItemCopy = new WebMenuItem("复制");
    private WebMenuItem menuItemPaste = new WebMenuItem("粘贴");
    private WebMenuItem menuItemExpTab = new WebMenuItem("导出面板");
    private WebMenuItem menuItemImpTab = new WebMenuItem("导入面板");
    private WebMenuItem menuItemAbout = new WebMenuItem("关于软件");
    private WebMenuItem menuItemContact = new WebMenuItem("联系我们");

    // 选项卡
    private WebTabbedPane tabPane = new WebTabbedPane();
    WebPanel tabExp = ExpPanel.getInstance();
    WebPanel tabImp = new WebPanel();

    private void initMenu() {
        menuItemNew.setEnabled(false);
        menuItemManage.setEnabled(false);
        menuItemImp.setEnabled(false);
        menuItemExp.setEnabled(false);
        menuFile.add(menuItemNew);
        menuFile.add(menuItemManage);
        menuFile.add(menuItemImp);
        menuFile.add(menuItemExp);
        menuFile.add(menuItemExit);
        menuEdit.add(menuItemCut);
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemPaste);
        menuView.add(menuItemExpTab);
        menuView.add(menuItemImpTab);
        menuHelp.add(menuItemAbout);
        menuHelp.add(menuItemContact);
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuView);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);
    }

    private MainFrame() {
        initMenu();

        // tabExp.setLayout(new BoxLayout(tabExp, BoxLayout.Y_AXIS));
        tabImp.setLayout(new BoxLayout(tabImp, BoxLayout.Y_AXIS));
        tabPane.addTab("导出", tabExp);
        tabPane.addTab("导入", tabImp);
        tabPane.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        add(tabPane);
        pack();
    }

    public synchronized static MainFrame getInstance() {
        if (mainFrame == null) {
            mainFrame = new MainFrame();
        }
        return mainFrame;
    }

    public static void main(String[] args) {
        Cache.getInstance().init();
        DisplayJFrame.runJFrame(new MainFrame(), "数据库迁移助手", 800, 600);
    }
}
