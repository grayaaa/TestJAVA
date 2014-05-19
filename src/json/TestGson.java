package json;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestGson {

	public static void main(String[] args) {
		String nsBean = "[{'strNodeName':'node1','strManageIP':'','strRackName':'rack1','strConfStatus':null,'listRole':['NameNode','DataNode','JournalNode','ZooKeeperServer','JobTracker','TaskTracker','HMaster','HBaseThrift','Metastore'],'bIsNN':true,'bIsSNN':null,'bIsDN':true,'bIsJN':true,'bIsJT':true,'bIsTT':true,'bIsResourceManager':null,'bIsNodeManager':null,'bIsJobHistoryServer':null,'bIsZK':true,'bIsHMaster':true,'bIsRegionServer':null,'bIsThrift':true,'bIsMetastore':true,'bIsHiveServer':null,'bIsHiveServer2':null},{'strNodeName':'node2','strManageIP':'','strRackName':'rack1','strConfStatus':null,'listRole':['DataNode','JournalNode','ZooKeeperServer','TaskTracker','RegionServer'],'bIsNN':null,'bIsSNN':null,'bIsDN':true,'bIsJN':true,'bIsJT':null,'bIsTT':true,'bIsResourceManager':null,'bIsNodeManager':null,'bIsJobHistoryServer':null,'bIsZK':true,'bIsHMaster':null,'bIsRegionServer':true,'bIsThrift':null,'bIsMetastore':null,'bIsHiveServer':null,'bIsHiveServer2':null}] ";
		List<NodeFormBean> listNode = Lists.newArrayList();
		listNode = GSONUtil.fromJson(nsBean, new TypeToken<List<NodeFormBean>>() {
		});
		System.out.println(("listNode-->" + listNode));

		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

		String jsonstring = "{'OfsPfs_fs_nr':'1','OfsPfs_ds_read_rate':'0.000','OfsPfsState':'service_ok','OfsPfs_capacity':'82797.605','OfsNodeName':'PARASTOR','OfsPfs_free_space':'82626.710','OfsPfs_used_space':'170.895','OfsPfs_dirs_nr':'0','OfsPfs_ds_write_rate':'0.000'}";

		GlobalStatusMetricBeanInfo globalStatusMetricBeanInfo = null;
		globalStatusMetricBeanInfo = GSONUtil.fromJson(jsonstring, GlobalStatusMetricBeanInfo.class);
		System.out.println(globalStatusMetricBeanInfo.getStrSystemStatus());

		// [{"OfsNodeManageIP":"10.0.33.9","OfsNodeName":"PARASTOR_MGR_node9","ParastorNodeState":"ok","OfsNodeDisplayName":"node9","ParastorMgrHAStatus":"{mgsha_state:mgsha_standby}","OfsResourceID":"627302058017855"},{"OfsNodeManageIP":"10.0.33.8","OfsNodeName":"PARASTOR_MGR_node8","ParastorNodeState":"ok","OfsNodeDisplayName":"node8","ParastorMgrHAStatus":"{ha_localip:30.0.33.8,ha_remoteip:30.0.33.9,ha_state:ok};{mgsha_state:mgsha_active}","OfsResourceID":"627302068590704"}]

		String json3 = "{'OfsNodeManageIP':'10.0.33.8','OfsNodeName':'PARASTOR_MGR_node8','ParastorNodeState':'ok','OfsNodeDisplayName':'node8','ParastorMgrHAStatus':'{ha_localip:30.0.33.8,ha_remoteip:30.0.33.9,ha_state:ok};{mgsha_state:mgsha_active}','OfsResourceID':'627302068590704'}";
		MgrMonitorMetricBeanInfo mgrinfo = new MgrMonitorMetricBeanInfo();
		mgrinfo = GSONUtil.fromJson(json3, MgrMonitorMetricBeanInfo.class);
		System.out.println(mgrinfo.getObjHaStatus());
		// Map m = new HashMap();
		// for(Object o : m.keySet()){
		// m.get(o);
		// }
		//
		//
		//
		User user = new User(1001.0, "user", 25, "man");
		Gson gson = new Gson();
		// String ss = gson.toJson(user);
		//
		// System.out.println(ss);
		//
		String ss2 = "[{'user_id_id':'1002.00000','user_name_':'iqn.2012-10.net.vlnb:h-tgt0;iqn','age':'25.00000','sex':'lady','type':true,'ERRORTest0':true,'ERRORTest1':true}]";
		List<User> listUsers = GSONUtil.fromJson(ss2, new TypeToken<List<User>>() {
		});
		System.out.println("##" + listUsers.get(0).getName());

		User user2 = gson.fromJson(ss2, User.class);
		System.out.println(user2.getId());
		System.out.println(user2.getType());
		System.out.println(user2.getName());
		System.out.println(user2.getAge());
		//
		Map map = new HashMap();
		map.put("id", 1006.0);
		map.put("name", "user;3");
		map.put("age", null);
		map.put("sex", "lady");
		map.put("type", "false");
		User user3 = new User();
		System.out.println("----" + map.toString());
		user3 = GSONUtil.fromJson(map, User.class);
		System.out.println(user3.getType());

		List<User> listUser = new ArrayList<User>();
		listUser.add(user);
		listUser.add(user2);
		Type type = new TypeToken<List<User>>() {
		}.getType();
		String sssString = gson.toJson(listUser, type);
		System.out.println("---->" + sssString);

		Person pp = new Person();
		pp.setName("person1");
		pp.setAge(12.0);
		pp.setSex("lady");
		pp.setListUser(listUser);

		// Type type = new TypeToken<List<User>>() {}.getType();
		String listUserJsonString = gson.toJson(pp, Person.class);
		System.out.println("---->" + listUserJsonString);

		Map map2 = new HashMap();
		map2.put("Person_Name", "p1");
		map2.put("Person_Sex", "lady");
		map2.put("Person_Age", 25.00);
		map2.put("Person_ListUser", listUser);
		Person p1 = GSONUtil.fromJson(map2, Person.class);
		System.out.println(p1.getListUser().get(0).getName());

		// Person pp2 = new Person();
		// pp2 = gson.fromJson(listgsonString, Person.class);
		// System.out.println(pp2.getListUser().get(0).getName());
		//
		//
		//
		// // 将HashMap字符串转换为 JSON
		// Map<String, String> testMap = new HashMap<String, String>();
		// testMap.put("id", "id.first");
		// testMap.put("name", "name.second");
		// String mapToJson = gson.toJson(testMap);
		// System.out.println(mapToJson);
		// // prints {"id":"id.first","name":"name.second"}
		// // 将JSON字符串转换为 HashMap
		// Map<String, String> userMap2 = (Map<String, String>) gson.fromJson(
		// mapToJson, new TypeToken<Map<String, String>>() {
		// }.getType());
		// System.out.println(userMap2.get("id"));
		//
		//
		//
		//
		// JSONUtil.toJson(userMap2);
		// Gson gson = new Gson();

		// Person pp2 = new Person();
		// pp2.setName("23");
		//
		// String p2 = GSONUtil.toJson(pp2);
		// System.out.println(p2);

	}
}
