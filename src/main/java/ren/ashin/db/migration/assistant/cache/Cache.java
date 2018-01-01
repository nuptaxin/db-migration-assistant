package ren.ashin.db.migration.assistant.cache;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.joda.time.format.DateTimeFormat;

import ren.ashin.db.migration.assistant.bean.ConnectionProperty;

import com.google.common.collect.Lists;

/**
 * @ClassName: Cache
 * @Description: TODO
 * @author renzx
 * @date Jan 1, 2018
 */
public class Cache {
    private static Cache cache = new Cache();
    private static List<ConnectionProperty> ConnectionPropertyList = null;

    private Cache() {
    }

    public void init() {
        try {
            ConnectionPropertyList = parseXml();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Cache getInstance() {
        return cache;
    }
    public List<ConnectionProperty> getConnectionPropertyList(){
        return ConnectionPropertyList;
    }
    private List<ConnectionProperty> parseXml() throws DocumentException {
        File xmlFile =
                FileUtils.getFile(System.getProperty("user.dir"), "conf", "connectionProperty.xml");
        // 创建SAXReader对象
        SAXReader reader = new SAXReader();
        // 读取文件 转换成Document
        Document document = reader.read(xmlFile);
        // 获取根节点元素对象
        Element root = document.getRootElement();

        return parseFile(root);
    }
    @SuppressWarnings("unchecked")
    private List<ConnectionProperty> parseFile(Element node) {
        List<ConnectionProperty> connectionPropertyList = Lists.newLinkedList();
        Iterator<Element> iterator = node.elementIterator();
        while (iterator.hasNext()) {
            Element e = iterator.next();
            if ("connection".equals(e.getName())) {
                ConnectionProperty connectionProperty = new ConnectionProperty();
                connectionProperty.setIp(e.elementText("ip"));
                connectionProperty.setPort(Integer.valueOf(e.elementText("port")));
                connectionProperty.setInstance(e.elementText("instance"));
                connectionProperty.setName(e.elementText("name"));
                connectionProperty.setPassword(e.elementText("password"));
                connectionProperty.setLastLoginTime(DateTimeFormat
                        .forPattern("yyyy-MM-dd HH:mm:ss")
                        .parseDateTime(e.elementText("lastLoginTime")).toDate());
                connectionProperty.setIsDefault(Boolean.valueOf(e.elementText("isDefault")));
                connectionPropertyList.add(connectionProperty);
            }
        }

        return connectionPropertyList;
    }
}
