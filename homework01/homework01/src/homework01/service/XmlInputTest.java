package homework01.service;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

import homework01.po.ClassInfo;

public class XmlInputTest {

	public static void main(String[] args) {
		// 1-定义XML字符串
		String str = "<object-stream>\r\n" + "  <homework01.po.ClassInfo>\r\n" + "    <classId>21</classId>\r\n"
				+ "    <className>java.lang.String</className>\r\n" + "  </homework01.po.ClassInfo>\r\n"
				+ "</object-stream>\r\n";
		// 2-创建Stream对象
		XStream xStream = new XStream(new DomDriver("utf-8"));
		// 3-XML——》Java Object
		try {
			byte[] buffer = str.getBytes("UTF-8");
			ObjectInputStream input = xStream.createObjectInputStream(new ByteArrayInputStream(buffer));
			ClassInfo classInfo = (ClassInfo) input.readObject();
			input.close();
			System.out.println(classInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
