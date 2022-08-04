define(["ace/lib/oop", "ace/mode/text", "ace/mode/text_highlight_rules"], function(oop, mText, mTextHighlightRules) {
	var HighlightRules = function() {
		var keywords = "accept_event_action|accept_time_event_action|access_path|activity|activity_edge|activity_final_node|actor|additional|anchors|association|associative_link|asynchronous_message|background_color|bounds|class|class_instance_participant|class_participant|communication|communication_asynchronous_message|communication_link|communication_path|communication_return_message|communication_synchronous_message|component|component_container|component_diagram|component_participant|component_reference|connector|connectors|constant_specification|container|creation_transition|data_type|decision_merge_node|delegation|deployment|derived_access_path|derived_communication_path|diagram|ee|end|end_fixed|enumeration_data_type|exception|extend|external_entity_participant|fill_color|floating|flow_final_node|fork_join|generalization|generic_action|height|imported_class|imported_provided_interface|imported_required_interface|include|initial_node|interface|layer|layers|lifeline|line_color|middle|none|object_node|package|package_participant|partition|point|polyline|properties|provided_interface|render|required_interface|return_message|satisfaction|segment|send_signal_action|sequence|shape|shapes|start|start_fixed|state|state_machine|structured_data_type|styles|subsystem|subtype|supertype|synchronous_message|system_model|text|texts|time_span|timing_mark|transition|use_case|use_case_binary_association|use_case_diagram|user_data_type|viewport|where|width|x|y|zoom";
		this.$rules = {
			"start": [
				{token: "comment", regex: "\\/\\/.*$"},
				{token: "comment", regex: "\\/\\*", next : "comment"},
				{token: "string", regex: '["](?:(?:\\\\.)|(?:[^"\\\\]))*?["]'},
				{token: "string", regex: "['](?:(?:\\\\.)|(?:[^'\\\\]))*?[']"},
				{token: "constant.numeric", regex: "[+-]?\\d+(?:(?:\\.\\d*)?(?:[eE][+-]?\\d+)?)?\\b"},
				{token: "lparen", regex: "[\\[]"},
				{token: "rparen", regex: "[\\]]"},
				{token: "keyword", regex: "\\b(?:" + keywords + ")\\b"}
			],
			"comment": [
				{token: "comment", regex: ".*?\\*\\/", next : "start"},
				{token: "comment", regex: ".+"}
			]
		};
	};
	oop.inherits(HighlightRules, mTextHighlightRules.TextHighlightRules);
	
	var Mode = function() {
		this.HighlightRules = HighlightRules;
	};
	oop.inherits(Mode, mText.Mode);
	Mode.prototype.$id = "xtext/xtumlg";
	Mode.prototype.getCompletions = function(state, session, pos, prefix) {
		return [];
	}
	
	return {
		Mode: Mode
	};
});
