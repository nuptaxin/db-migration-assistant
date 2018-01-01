package ren.ashin.db.migration.assistant;

import ren.ashin.db.migration.assistant.cache.Cache;
import ren.ashin.db.migration.assistant.gui.MainFrame;
import ren.ashin.db.migration.assistant.render.DisplayJFrame;

/**
 * @ClassName: MigrationAssistantNew
 * @Description: 新的程序入口（以后将取代就的程序入口）
 * @author renzx
 * @date Jan 1, 2018
 */
public class MigrationAssistantNew {
    public static void main(String[] args) {
        // 先读取配置文件
        Cache.getInstance().init();
        // 调用程序主界面展示方法
        DisplayJFrame.runJFrame(MainFrame.getInstance(), "数据库迁移助手", 800, 600);
    }
}
