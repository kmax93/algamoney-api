public class Teste {

	public static void main(String args[]) {
		String json = "{\"term\":\"sdoijfiosdjdif\",\"status\":[\"CREATED\",\"DESIGNATED\",\"CONCLUDED\",\"EXPIRED\",\"CANCELED\",\"OUT_OF_DATE\",\"CONCLUDED_OUT_OF_DATE\"],\"localeIds\":null,\"categoryTypeIds\":null}";

		int indexUltimaChave = json.lastIndexOf("}");

		StringBuilder builder = new StringBuilder(json);
		builder.insert(169, ",\"task\":\"true\"");

		System.out.println(indexUltimaChave);

		System.out.println(builder);
	}

}
