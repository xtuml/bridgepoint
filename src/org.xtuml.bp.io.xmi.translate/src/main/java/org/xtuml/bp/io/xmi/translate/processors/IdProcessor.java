package org.xtuml.bp.io.xmi.translate.processors;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IdProcessor {

	public static final String NULL_ID = "00000000-0000-0000-0000-000000000000";

	static Map<String, UUID> createdUUIDs = new HashMap<>();
	public static Map<UUID, String> elementIdKeyLetts = new HashMap<>();

	static Map<String, String> globalTypes = Map.ofEntries(
			new AbstractMap.SimpleEntry<String, String>("void", "ba5eda7a-def5-0000-0000-000000000000"),
			new AbstractMap.SimpleEntry<String, String>("boolean", "ba5eda7a-def5-0000-0000-000000000001"),
			new AbstractMap.SimpleEntry<String, String>("integer", "ba5eda7a-def5-0000-0000-000000000002"),
			new AbstractMap.SimpleEntry<String, String>("real", "ba5eda7a-def5-0000-0000-000000000003"),
			new AbstractMap.SimpleEntry<String, String>("string", "ba5eda7a-def5-0000-0000-000000000004"),
			new AbstractMap.SimpleEntry<String, String>("unique_id", "ba5eda7a-def5-0000-0000-000000000005"),
			new AbstractMap.SimpleEntry<String, String>("state<State_Model>", "ba5eda7a-def5-0000-0000-000000000006"),
			new AbstractMap.SimpleEntry<String, String>("same_as<Base_Attribute>",
					"ba5eda7a-def5-0000-0000-000000000007"),
			new AbstractMap.SimpleEntry<String, String>("inst_ref<Object>", "ba5eda7a-def5-0000-0000-000000000008"),
			new AbstractMap.SimpleEntry<String, String>("inst_ref_set<Object>", "ba5eda7a-def5-0000-0000-000000000009"),
			new AbstractMap.SimpleEntry<String, String>("inst<Event>", "ba5eda7a-def5-0000-0000-00000000000a"),
			new AbstractMap.SimpleEntry<String, String>("inst<Mapping>", "ba5eda7a-def5-0000-0000-00000000000b"),
			new AbstractMap.SimpleEntry<String, String>("inst_ref<Mapping>", "ba5eda7a-def5-0000-0000-00000000000c"),
			new AbstractMap.SimpleEntry<String, String>("component_ref", "ba5eda7a-def5-0000-0000-00000000000d"),
			new AbstractMap.SimpleEntry<String, String>("date", "ba5eda7a-def5-0000-0000-00000000000e"),
			new AbstractMap.SimpleEntry<String, String>("inst_ref<Timer>", "ba5eda7a-def5-0000-0000-00000000000f"),
			new AbstractMap.SimpleEntry<String, String>("timestamp", "ba5eda7a-def5-0000-0000-000000000010"));

	static {
		globalTypes.forEach((k, v) -> addId(v));
	}

	public static String getTypeName(String value) {
		if (value.startsWith("EAJava__")) {
			value = value.replaceAll("EAJava__", "");
			value = value.substring(0, value.length() - 1);
			value = value.replaceAll("_", " ");
			value = "{" + value + "}";
		}
		return value;
	}

	public static String process(String id) {
		return process(id, null);
	}

	public static String process(String value, String keyLetters) {
		value = getTypeName(value);
		if (value.startsWith("EAJava_")) {
			value = value.replaceAll("EAJava_", "");
			value = value.replaceAll("__", "");
			String coreType = getIdForType(value);
			if (coreType != null) {
				return coreType;
			}
		}
		UUID uuid = createdUUIDs.get(value);
		if (value.equals(NULL_ID.toString())) {
			return value;
		}
		if (uuid != null) {
			if (keyLetters != null) {
				elementIdKeyLetts.put(uuid, keyLetters);
			}
			return uuid.toString();
		} else {
			uuid = UUID.randomUUID();
			createdUUIDs.put(value, uuid);
			if (keyLetters != null) {
				elementIdKeyLetts.put(uuid, keyLetters);
			}
			return uuid.toString();
		}
	}

	public static void addId(String id) {
		// add for ids we do not want to recreate
		createdUUIDs.put(id, UUID.fromString(id));
	}

	public static String getIdForType(String name) {
		name = convertToXtuml(name);
		return globalTypes.get(name);
	}

	private static String convertToXtuml(String name) {
		switch (name) {
			case "UUID":
				return "unique_id";
			case "int":
				return "integer";
			case "double":
			case "float":
				return "real";
			case "String":
				return "string";
		}
		return name;
	}

	public static String getId(String name) {
		name = getTypeName(name);
		UUID id = createdUUIDs.get(name);
		if (id != null) {
			return id.toString();
		}
		return null;
	}

	public static UUID UUID() {
		UUID random = UUID.randomUUID();
		createdUUIDs.put(random.toString(), random);
		return random;
	}

	public static void addId(String name, String id) {
		createdUUIDs.put(name, UUID.fromString(id));
	}

	public static String getCoreType(String id) {
		return globalTypes.get(id);
	}

}
