package jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJackson {
    static String strUser = "{'name':'user','age':25,'sex':'man','id':1001.0,'type':null,'type2':null}";

    static String strPerson = "{'name':'person1','sex':'lady','age':12.0,'listUser':[{'name':'user','age':25,'sex':'man','type':null,'id':1001.0},{'name':'user','age':25,'sex':'man','type':null,'id':1001.0}]}";

    static String strListUser = "[{'name':'user','age':25,'sex':'man','type':null,'id':1001.0},{'name':'user','age':25,'sex':'man','type':null,'id':1001.0}]";

    static String strMapUser = "{'user2':{'name':'user','age':25,'sex':'man','type':null,'id':1001.0},'user1':{'name':'user','age':25,'sex':'man','type':null,'id':1001.0}}";

    static User user1 = new User(1001.0, "user", 25, "man");

    static User user2 = new User(1001.0, "user", 25, "man");

    static List<User> listUser = new ArrayList<User>();

    static Person pp = new Person();

    static HashMap<String, User> mapUser = new HashMap<String, User>();
    static {

        listUser.add(user1);
        listUser.add(user2);

        pp.setName("person1");
        pp.setAge(12.0);
        pp.setSex("lady");
        pp.setListUser(listUser);

        mapUser.put("user1", user1);
        mapUser.put("user2", user2);
    }

    /**
     * @param args
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
        // Json <-> Bean
        transformJavaBean();

    }

    /*
     * Json <-> Bean
     */
    private static void transformJavaBean() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Bean-JSON
        // mapper.writeValue(new File("user-modified.json"), user);
        System.out.println("user1---->" + mapper.writeValueAsString(user1));
        System.out.println("pp---->" + mapper.writeValueAsString(pp));
        System.out.println("mapUser---->" + mapper.writeValueAsString(mapUser));

        // JSON-Bean
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);// 允许json串为单引号
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//多余属性过滤type2=null

        User user3 = mapper.readValue(strUser, User.class);// 普通Bean
        System.out.println("user3-->" + user3.toString());

        HashMap<String, Object> map3 = (HashMap<String, Object>) mapper.readValue(strUser, Map.class);// 普通Map
        System.out.println("map3-->" + map3.toString());
        if (map3.get("type") == null) {
            System.out.println("map3-->type is null!");
        }

        Person person3 = mapper.readValue(strPerson, Person.class);// 嵌套Bean
        System.out.println("person3-->" + person3.toString());

        // JavaType listUserType =
        // mapper.getTypeFactory().constructParametricType(ArrayList.class,
        // User.class);
        TypeReference<List<User>> listUserType = new TypeReference<List<User>>() {};
        List<User> listUser3 = (List<User>) mapper.readValue(strListUser, listUserType);// List<Bean>
        System.out.println("listUser3-->" + listUser3.toString());

        // JavaType hashMapType =
        // mapper.getTypeFactory().constructParametricType(HashMap.class,
        // String.class, User.class);
        TypeReference<HashMap<String, User>> hashMapType = new TypeReference<HashMap<String, User>>() {};
        HashMap<String, User> mapUser3 = (HashMap<String, User>) mapper.readValue(strMapUser, hashMapType);// HashMap<string,Bean>
        System.out.println("mapUser3-->" + mapUser3.toString());
    }
}
