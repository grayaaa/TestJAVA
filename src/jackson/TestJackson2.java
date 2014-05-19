package jackson;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJackson2 {

	public static class Foo {
		public int foo;
	}

	public static void main(String[] args) throws JsonProcessingException, IOException {

		String json = "{\"someArray\":[{\"foo\":5},{\"foo\":6},{\"foo\":7}]}";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(json);
		node = node.get("someArray");
		TypeReference<List<Foo>> typeRef = new TypeReference<List<Foo>>() {
		};
		List<Foo> list = mapper.readValue(node.traverse(), typeRef);
		for (Foo f : list) {
			System.out.println(f.foo);
		}
	}

}
