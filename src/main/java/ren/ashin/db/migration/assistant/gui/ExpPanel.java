package ren.ashin.db.migration.assistant.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ren.ashin.db.migration.assistant.bean.ConnectionProperty;
import ren.ashin.db.migration.assistant.cache.Cache;
import ren.ashin.db.migration.assistant.render.ButtonRendererListener;
import ren.ashin.db.migration.assistant.render.CellRenderer;
import ren.ashin.db.migration.assistant.render.CellToolTip;

import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.google.common.collect.Lists;

/**
 * @ClassName: ExpPanel
 * @Description: TODO
 * @author renzx
 * @date Jan 1, 2018
 */
public class ExpPanel extends WebPanel {
    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = 1269701430494805781L;
    private WebLabel dbConnLabel = new WebLabel("数据库连接：");
    private WebLabel expTypeLabel = new WebLabel("导出方式：");
    private WebLabel expParamLabel = new WebLabel("导出参数：");
    private WebLabel expCatalogLabel = new WebLabel("导出目录：");
    private WebComboBox dbConnComboBox = new WebComboBox();
    private ButtonGroup expTypeButtonGroup = new ButtonGroup();
    private WebRadioButton timeRadioButton = new WebRadioButton("时段");
    private WebRadioButton userRadioButton = new WebRadioButton("用户");
    private WebRadioButton tableRadioButton = new WebRadioButton("表");
    private WebRadioButton allRadioButton = new WebRadioButton("全部");
    private WebTextField expParamTextField = new WebTextField();
    private WebTextField expCatalogTextField = new WebTextField();

    private WebButton testConnButton = new WebButton("测试数据库连接");
    private WebButton createExpBatButton = new WebButton("生成导出脚本");
    private WebButton deleteAllButton = new WebButton("删除导出脚本，数据与日志");

    private static WebTable webTable = new WebTable();
    private static String[] tableHeadStr = {"脚本路径", "数据源大小", "操作"};
    private static ExpPanel expPanel = null;



    private ExpPanel() {
        initComponents();
        initTable(Cache.getInstance().getConnectionPropertyList());
    }

    public synchronized static ExpPanel getInstance() {
        if (expPanel == null) {
            expPanel = new ExpPanel();
            expPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        }
        return expPanel;
    }

    private void initComponents() {
        List<ConnectionProperty> connectionPropertyList =
                Cache.getInstance().getConnectionPropertyList();
        for (ConnectionProperty connectionProperty : connectionPropertyList) {
            dbConnComboBox.addItem(connectionProperty);
            if (connectionProperty.getIsDefault()) {
                dbConnComboBox.setSelectedItem(connectionProperty);
            }
        }
        expTypeButtonGroup.add(timeRadioButton);
        expTypeButtonGroup.add(userRadioButton);
        expTypeButtonGroup.add(tableRadioButton);
        expTypeButtonGroup.add(allRadioButton);
        timeRadioButton.setSelected(true);
        WebPanel northPanel = new WebPanel(new GridLayout(4, 2));
        northPanel.setBorder(BorderFactory.createEmptyBorder(0, 210, 0, 210));
        northPanel.add(dbConnLabel);
        northPanel.add(dbConnComboBox);
        northPanel.add(expTypeLabel);
        northPanel.add(new WebPanel(new FlowLayout()).add(Lists.newArrayList(timeRadioButton, userRadioButton,
                tableRadioButton, allRadioButton)));
        northPanel.add(expParamLabel);
        northPanel.add(expParamTextField);
        northPanel.add(expCatalogLabel);
        northPanel.add(expCatalogTextField);
        add(northPanel, BorderLayout.NORTH);

        WebPanel centerPanel = new WebPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        centerPanel.setBorder(new EmptyBorder(20, 210, 20, 0));
        centerPanel.add(testConnButton);
        centerPanel.add(createExpBatButton);
        centerPanel.add(deleteAllButton);
        add(centerPanel, BorderLayout.CENTER);

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setDataVector(null, tableHeadStr);
        webTable = new WebTable(dtm) {
            public JToolTip createToolTip() {
                return new CellToolTip();
            }
        };
        webTable.setRowHeight(30);
        webTable.setEnabled(false);
        WebScrollPane southPanel = new WebScrollPane(webTable);
        southPanel.setViewportView(webTable);
        add(southPanel, BorderLayout.SOUTH);
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
                    webTable.getColumn(tableHeadStr[2]).setCellRenderer(renderer);
                    webTable.revalidate();
                }
            });
        }
    }
}
