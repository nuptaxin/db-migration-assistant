package ren.ashin.db.migration.assistant.gui.conn;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import ren.ashin.db.migration.assistant.bean.ConnectionProperty;
import ren.ashin.db.migration.assistant.cache.Cache;
import ren.ashin.db.migration.assistant.gui.MainFrame;
import ren.ashin.db.migration.assistant.render.ButtonRendererListener;
import ren.ashin.db.migration.assistant.render.CellRenderer;
import ren.ashin.db.migration.assistant.render.CellToolTip;
import ren.ashin.db.migration.assistant.render.DisplayJFrame;

import com.alee.extended.panel.FlowPanel;
import com.alee.laf.button.WebButton;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;

/**
 * @ClassName: ConnectionPanel
 * @Description: TODO
 * @author renzx
 * @date Dec 13, 2017
 */
public class ConnectionDialog extends WebDialog {

    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = 85491324664808563L;
    private static ConnectionDialog connectionDialog = null;

    private WebButton addButton = new WebButton("添加");
    private WebButton saveButton = new WebButton("保存");
    private WebButton backButton = new WebButton("返回");
    private WebScrollPane parentPanel;
    private FlowPanel flowPanel;
    private static WebTable webTable = new WebTable();
    public static String[] tableHeadStr = {"用户名", "密码", "地址", "端口", "实例名", "默认值", "最后修改时间"};



    private void initComponents() {
        setAlwaysOnTop(true);// 是否置于顶层
        // setUndecorated(true);// 是否隐藏窗口边框
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setDataVector(null, tableHeadStr);
        webTable = new WebTable(dtm) {
            public JToolTip createToolTip() {
                return new CellToolTip();
            }
        };
        webTable.setRowHeight(30);
        webTable.setEnabled(false);
        parentPanel = new WebScrollPane(webTable);
        flowPanel = new FlowPanel();
        flowPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        flowPanel.add(addButton);
        flowPanel.add(saveButton);
        flowPanel.add(backButton);
        add(parentPanel, BorderLayout.CENTER);
        add(flowPanel, BorderLayout.SOUTH);
        pack();
    }

    private ConnectionDialog(WebFrame webFrame) {
        super(webFrame, true);
        initComponents();
        setLocationRelativeTo(webFrame);
        initTable(Cache.getInstance().getConnectionPropertyList());
    }


    private void initTable(List<ConnectionProperty> connectionPropertyList) {
        final DefaultTableModel webTablem = (DefaultTableModel) webTable.getModel();
        final ButtonRendererListener renderer = new ButtonRendererListener(webTable);
        final CellRenderer cellRenderer = new CellRenderer(webTable);
        MouseListener[] mlArr = webTable.getMouseListeners();
        if (mlArr.length == 2)
            webTable.addMouseListener(renderer);
        for (final ConnectionProperty connectionProperty : connectionPropertyList) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {

                    webTablem.addRow(new Object[] {connectionProperty.getName(),
                            connectionProperty.getPassword(), connectionProperty.getIp(),
                            connectionProperty.getPort(), connectionProperty.getInstance(),
                            connectionProperty.getIsDefault(),
                            connectionProperty.getLastLoginTime()});
                    webTable.setOpaque(true);
                    // 注入渲染器
                    webTable.getColumn(tableHeadStr[0]).setCellRenderer(cellRenderer);
                    webTable.getColumn(tableHeadStr[1]).setCellRenderer(cellRenderer);
                    webTable.getColumn(tableHeadStr[2]).setCellRenderer(cellRenderer);
                    webTable.getColumn(tableHeadStr[3]).setCellRenderer(cellRenderer);
                    webTable.getColumn(tableHeadStr[4]).setCellRenderer(cellRenderer);
                    webTable.getColumn(tableHeadStr[5]).setCellRenderer(renderer);
                    webTable.getColumn(tableHeadStr[6]).setCellRenderer(cellRenderer);
                    webTable.revalidate();
                    pack();
                }
            });
        }
    }

    public synchronized static ConnectionDialog getInstance(WebFrame webFrame) {
        if (connectionDialog == null) {
            connectionDialog = new ConnectionDialog(webFrame);
        }
        return connectionDialog;
    }

    public static void main(String[] args) {
        MainFrame mainFrame = MainFrame.getInstance();
        DisplayJFrame.runJFrame(mainFrame, "数据库迁移助手", 800, 600);
        Cache.getInstance().init();
        ConnectionDialog.getInstance(mainFrame).setVisible(true);
    }
}
