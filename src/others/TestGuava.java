package others;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class TestGuava {
	public static void testMutimap() {
		Multimap<String, String> Role_Conf_Relation = ArrayListMultimap.create();

		Role_Conf_Relation.put("NameNode", "HDFS/core-site.xml");
		Role_Conf_Relation.put("NameNode", "HDFS/hdfs-site.xml");
		Role_Conf_Relation.put("NameNode", "HDFS/exlude");
		Role_Conf_Relation.put("SecondaryNameNode", "HDFS/core-site.xml");
		Role_Conf_Relation.put("SecondaryNameNode", "HDFS/hdfs-site.xml");
		Role_Conf_Relation.put("SecondaryNameNode", "HDFS/exlude");

		Collection<String> listNN = Role_Conf_Relation.get("NameNode");
		Collection<String> listSNN = Role_Conf_Relation.get("SecondaryNameNode");
		Set<String> ss = Sets.newHashSet();
		ss.addAll(listNN);
		ss.addAll(listSNN);
		System.out.println(ss.toString());
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		testMutimap();

		List<String> tmp = Lists.newArrayList();
		tmp.add("node84");
		tmp.add(" node84");
		tmp.add(" node84");
		tmp.add(" ");
		System.out.println(tmp.toString());
		System.out.println(Joiner.on(",").join(tmp));

	}
}
