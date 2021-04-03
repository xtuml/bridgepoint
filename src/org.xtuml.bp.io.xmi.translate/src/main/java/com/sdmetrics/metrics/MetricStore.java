/*
 * SDMetrics Open Core for UML design measurement
 * Copyright (c) Juergen Wuest
 * To contact the author, see <http://www.sdmetrics.com/Contact.html>.
 * 
 * This file is part of the SDMetrics Open Core.
 * 
 * SDMetrics Open Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.

 * SDMetrics Open Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with SDMetrics Open Core.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.sdmetrics.metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.math.ExpressionParser;
import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.util.SAXHandler;

/**
 * Reads a metric definition file and provides access to metrics, sets, and
 * rules defined therein.
 * <p>
 * The class also maintains the matrices, literature references, glossary
 * entries, and word lists defined in the file, as well as custom defined
 * metric, set, and rule procedures, and custom scalar, boolean, or set
 * operations.
 */
public class MetricStore {

	/** The name of the top level XML element in the metric definition file. */
	public static final String TL_ELEMENT = "sdmetrics";

	/** Metamodel on which the metric, set, and rule definitions are based. */
	private final MetaModel metaModel;

	/** The standard and custom defined metric calculation procedures. */
	private final MetricProcedureCache metricProcedures;
	/** The standard and custom defined set calculation procedures. */
	private final SetProcedureCache setProcedures;
	/** The standard and custom defined rule calculation procedures. */
	private final RuleProcedureCache ruleProcedures;
	/** The standard and custom defined expression operations. */
	private final ExpressionOperations exprOps;

	/**
	 * Container for the metrics. Metrics are stored by model element type. For
	 * each type there is one mapping of metric name (key) to the associated
	 * metric.
	 */
	private final HashMap<MetaModelElement, LinkedHashMap<String, Metric>> metrics;

	/** Container for the sets. Same organization as for metrics. */
	private final HashMap<MetaModelElement, LinkedHashMap<String, Set>> sets;

	/** Container for the sets. Same organization as for metrics. */
	private final HashMap<MetaModelElement, LinkedHashMap<String, Rule>> rules;

	/** The relation matrices. */
	private final ArrayList<Matrix> matrices;
	/** The literature references. Key is the reference tag (such as "CK94") */
	private final LinkedHashMap<String, Reference> references;
	/** The glossary. Key is the glossary term. */
	private final LinkedHashMap<String, Glossary> glossary;
	/** The word lists. Key is the name of the list. */
	private final HashMap<String, WordList> wordLists;

	/** The element type with information about rule exemptions. */
	private MetaModelElement ruleExemptionType;
	/**
	 * Name of the attribute or metric that contains the rule exemption
	 * information.
	 */
	private String ruleExemptionTag;

	/** Indicates if metrics/sets/rule should be inheritable by default. */
	private boolean defaultInheritability;

	/**
	 * Creates a new metric store.
	 * 
	 * @param metaModel Metamodel on which the metrics are based.
	 */
	public MetricStore(MetaModel metaModel) {
		this.metaModel = metaModel;

		metricProcedures = new MetricProcedureCache();
		setProcedures = new SetProcedureCache();
		ruleProcedures = new RuleProcedureCache();
		exprOps = new ExpressionOperations();

		metrics = new HashMap<>();
		sets = new HashMap<>();
		rules = new HashMap<>();
		for (MetaModelElement type : metaModel) {
			metrics.put(type, new LinkedHashMap<String, Metric>());
			sets.put(type, new LinkedHashMap<String, Set>());
			rules.put(type, new LinkedHashMap<String, Rule>());
		}

		matrices = new ArrayList<>();
		references = new LinkedHashMap<>();
		glossary = new LinkedHashMap<>();
		wordLists = new HashMap<>();
	}

	/**
	 * Gets a SAX handler to parse a metric definition file and store its
	 * contents with this object.
	 * 
	 * @return SAX handler to parse metrics definition file
	 */
	public DefaultHandler getSAXParserHandler() {
		return new MetricParser();
	}

	/**
	 * Gets the metamodel on which the metrics are based.
	 * 
	 * @return the metamodel for this metric store
	 */
	public MetaModel getMetaModel() {
		return metaModel;
	}

	/**
	 * Gets the cache holding the metric procedure definitions for this metric
	 * store.
	 * 
	 * @return the metric procedure definitions for this store.
	 */
	MetricProcedureCache getMetricProcedures() {
		return metricProcedures;
	}

	/**
	 * Gets the cache holding the set procedure definitions for this metric
	 * store.
	 * 
	 * @return the set procedure definitions for this store.
	 */
	SetProcedureCache getSetProcedures() {
		return setProcedures;
	}

	/**
	 * Gets the cache holding the rule procedure definitions for this metric
	 * store.
	 * 
	 * @return the rule procedure definitions for this store.
	 */
	RuleProcedureCache getRuleProcedures() {
		return ruleProcedures;
	}

	/**
	 * Gets the cache holding the scalar, boolean, and set operations for the
	 * metric expressions in this metric store.
	 * 
	 * @return metric expressions in this store.
	 */
	ExpressionOperations getExpressionOperations() {
		return exprOps;
	}

	/**
	 * Gets the metric for a given element type by its name.
	 * 
	 * @param type Element type for which the metric is defined.
	 * @param name Name of the metric.
	 * @return the specified metric, or <code>null</code> if no such metric was
	 *         found.
	 */
	public Metric getMetric(MetaModelElement type, String name) {
		return metrics.get(type).get(name);
	}

	/**
	 * Gets the set for a given element type by its name.
	 * 
	 * @param type Element type for which the set is defined.
	 * @param name Name of the set.
	 * @return the specified set, or <code>null</code> if no such set was found.
	 */
	public Set getSet(MetaModelElement type, String name) {
		return sets.get(type).get(name);
	}

	/**
	 * Gets the rule for a given element type by its name.
	 * 
	 * @param type Element type for which the rule is defined.
	 * @param name Name of the rule.
	 * @return the specified rule, or <code>null</code> if no such rule was
	 *         found.
	 * @since 2.3
	 */
	public Rule getRule(MetaModelElement type, String name) {
		return rules.get(type).get(name);
	}

	/**
	 * Gets the list of all metrics for a given element type.
	 * 
	 * @param type Element type for which to return the metrics.
	 * @return The metrics for the type. Iteration over this collection
	 *         preserves the order in which the metrics are defined in the
	 *         metric definition file.
	 */
	public Collection<Metric> getMetrics(MetaModelElement type) {
		return metrics.get(type).values();
	}

	/**
	 * Gets the list of all sets for a given element type.
	 * 
	 * @param type Element type for which to return the sets.
	 * @return The sets for the type.
	 */
	public Collection<Set> getSets(MetaModelElement type) {
		return sets.get(type).values();
	}

	/**
	 * Gets the list of all rules for a given element type.
	 * 
	 * @param type Element type for which to return the rules.
	 * @return The rules for the type. Iteration over this collection preserves
	 *         the order in which the rules are defined in the metric definition
	 *         file.
	 */
	public Collection<Rule> getRules(MetaModelElement type) {
		return rules.get(type).values();
	}

	/**
	 * Gets the list of all relationship matrices defined in the metric
	 * definition file.
	 * 
	 * @return Random access list containing the matrices in the order they
	 *         appear in the metric definition file.
	 */
	public List<Matrix> getMatrices() {
		return matrices;
	}

	/**
	 * Gets the list of all literature references defined in the metric
	 * definition file.
	 * 
	 * @return The literature references. Iteration over this collection
	 *         preserves the order in which the references are defined in the
	 *         metric definition file.
	 */
	public Collection<Reference> getLiteratureReferences() {
		return references.values();
	}

	/**
	 * Gets the list of all glossary terms defined in the metric definition
	 * file.
	 * 
	 * @return The glossary terms. Iteration over this collection preserves the
	 *         order in which the glossary terms are defined in the metric
	 *         definition file.
	 */
	public Collection<Glossary> getGlossary() {
		return glossary.values();
	}

	/**
	 * Returns the element type that contains information about rule exemptions.
	 * This type is defined by the "ruleexemption" attribute of the top level
	 * XML element in the metric definition file.
	 * 
	 * @return element type with rule exemption information, or
	 *         <code>null</code> if no such type was specified.
	 */
	MetaModelElement getRuleExemptionType() {
		return ruleExemptionType;
	}

	/**
	 * Returns the name of the attribute or metric that yields the rule
	 * exemption information. This name is defined by the "ruleexemption"
	 * attribute of the top level XML element in the metric definition file.
	 * 
	 * @return Name of the rule exemption attribute or metric, or
	 *         <code>null</code> if none was specified.
	 */
	String getRuleExemptionTag() {
		return ruleExemptionTag;
	}

	/**
	 * Checks if a specified word is on a word list.
	 * 
	 * @param word The word to search for.
	 * @param listName The name of the list to search.
	 * @return <code>true</code> if the specified list contains the word.
	 * @throws SDMetricsException The specified list does not exist.
	 */
	boolean isWordOnList(String word, String listName)
			throws SDMetricsException {
		WordList list = wordLists.get(listName);
		if (list == null) {
			throw new SDMetricsException(null, null, listName
					+ ": No such word list.");
		}
		return list.hasWord(word);
	}

	/** Represents a word list in the metric definition file. */
	static class WordList {
		/** Indicates if string comparisons should be case-sensitive. */
		private final boolean caseSensitive;
		/** The set of words on the list. */
		private final HashSet<String> words;

		/**
		 * Construct a new word list.
		 * 
		 * @param isCaseSensitive Indicates if string comparisons should be case
		 *        sensitive or not.
		 */
		WordList(boolean isCaseSensitive) {
			caseSensitive = isCaseSensitive;
			words = new HashSet<>();
		}

		/**
		 * Adds a word to the word list.
		 * 
		 * @param word The word to add.
		 */
		void addWord(String word) {
			if (caseSensitive) {
				words.add(word);
			} else {
				words.add(word.toUpperCase(Locale.ENGLISH));
			}
		}

		/**
		 * Tests if a word is on the word list.
		 * 
		 * @param word The word to test.
		 * @return <code>true</code> if the word is on the list
		 */
		boolean hasWord(String word) {
			if (caseSensitive) {
				return words.contains(word);
			}
			return words.contains(word.toUpperCase(Locale.ENGLISH));
		}
	}

	/**
	 * SAX handler to parse a metric definition file.
	 */
	private class MetricParser extends SAXHandler {

		// XML element and attribute names in the metric definition file
		private static final String METRICPROCEDURE = "metricprocedure";
		private static final String SETPROCEDURE = "setprocedure";
		private static final String RULEPROCEDURE = "ruleprocedure";
		private static final String SCALAROPERATION = "scalaroperationdefinition";
		private static final String SETOPERATION = "setoperationdefinition";
		private static final String BOOLEANOPERATION = "booleanoperationdefinition";

		private static final String METRICENTRY = "metric";
		private static final String SETENTRY = "set";
		private static final String MATRIXENTRY = "matrix";
		private static final String RULEENTRY = "rule";
		private static final String WORDLIST = "wordlist";
		private static final String WORDLISTENTRY = "entry";
		private static final String REFERENCEENTRY = "reference";
		private static final String GLOSSARYENTRY = "term";
		private static final String DESCRIPTION = "description";
		private static final String INHERITABLE = "inheritable";

		private MetricEntry currentEntry = null;
		private String currentEntryType = null;
		private String currentEntryName = null;
		private WordList currentWordList;
		private boolean inDescription = false;
		private ExpressionParser ep = exprOps.getExpressionParser();

		/**
		 * Process an XML element in the metric definition file.
		 * 
		 * @throws SAXException An error occurred processing an XML element (bad
		 *         definition file).
		 */
		@Override
		public void startElement(String uri, String local, String raw,
				Attributes attrs) throws SAXException {
			if (MetricStore.TL_ELEMENT.equals(raw)) {
				checkVersion(attrs, null);
				String exemptionTypeString = attrs.getValue("ruleexemption");
				if (exemptionTypeString != null) {
					ruleExemptionType = metaModel.getType(exemptionTypeString);
					if (ruleExemptionType == null) {
						throw buildSAXException("Unknown rule exemption type '" 
								+ exemptionTypeString + "'.");
					}
				}
				ruleExemptionTag = attrs.getValue("exemptiontag");
				defaultInheritability = isAttributeValueTrue(attrs, INHERITABLE);
			} else if (DESCRIPTION.equals(raw)) {
				inDescription = true;
			} else if (METRICPROCEDURE.equals(raw)) {
				handleProcedureDefinition(metricProcedures, raw, attrs);
			} else if (SETPROCEDURE.equals(raw)) {
				handleProcedureDefinition(setProcedures, raw, attrs);
			} else if (RULEPROCEDURE.equals(raw)) {
				handleProcedureDefinition(ruleProcedures, raw, attrs);
			} else if (SCALAROPERATION.equals(raw)) {
				String opName = handleProcedureDefinition(
						exprOps.getScalarOperations(), raw, attrs);
				exprOps.addCustomFunctionName(opName);
			} else if (SETOPERATION.equals(raw)) {
				String opName = handleProcedureDefinition(
						exprOps.getSetOperations(), raw, attrs);
				exprOps.addCustomFunctionName(opName);
			} else if (BOOLEANOPERATION.equals(raw)) {
				String opName = handleProcedureDefinition(
						exprOps.getBooleanOperations(), raw, attrs);
				exprOps.addCustomFunctionName(opName);
			} else if (METRICENTRY.equals(raw)) {
				currentEntryType = raw;
				currentEntry = handleMetricDefinition(attrs);
			} else if (SETENTRY.equals(raw)) {
				currentEntryType = raw;
				currentEntry = handleSetDefinition(attrs);
			} else if (MATRIXENTRY.equals(raw)) {
				currentEntryType = raw;
				currentEntry = handleMatrixDefinition(attrs);
			} else if (RULEENTRY.equals(raw)) {
				currentEntryType = raw;
				currentEntry = handleRuleDefinition(attrs);
			} else if (REFERENCEENTRY.equals(raw)) {
				currentEntryType = raw;
				inDescription = true;
				currentEntry = handleReferenceDefinition(attrs);
			} else if (GLOSSARYENTRY.equals(raw)) {
				currentEntryType = raw;
				inDescription = true;
				currentEntry = handleGlossaryDefinition(attrs);
			} else if (WORDLIST.equals(raw)) {
				currentEntryType = raw;
				currentWordList = handleWordListDefinition(attrs);
			} else if (WORDLISTENTRY.equals(raw)) {
				handleWordListEntry(attrs);
			} else {
				handleComputationDefinition(raw, attrs);
			}
		}

		/**
		 * Adds text to the description of the current entry.
		 */
		@Override
		public void characters(char[] ch, int start, int length) {
			if (inDescription && currentEntry != null && length > 0) {
				currentEntry.addDescription(ch, start, length);
			}
		}

		/**
		 * Performs consistency checks at the end of an XML element.
		 */
		@Override
		public void endElement(String uri, String local, String raw)
				throws SAXException {
			if (raw.equals(DESCRIPTION) || raw.equals(GLOSSARYENTRY)
					|| raw.equals(REFERENCEENTRY)) {
				inDescription = false;
				if (!raw.equals(DESCRIPTION)) {
					currentEntry = null;
				}
			} else if (raw.equals(METRICENTRY) || raw.equals(SETENTRY)
					|| raw.equals(RULEENTRY) || raw.equals(MATRIXENTRY)) {
				if (currentEntry.getAttributes() == null) {
					throw buildSAXException("No calculation procedure specified for " + raw
							+ " '" + currentEntry.getName() + "'.");
				}
				currentEntry = null;
			} else if (raw.equals(WORDLIST)) {
				currentWordList = null;
			}
		}

		/**
		 * Calculates metric/set/rule inheritance after all transformations have
		 * been read.
		 */
		@Override
		public void endDocument() {
			HashSet<MetaModelElement> processedTypes = new HashSet<>();
			for (MetaModelElement type : metaModel) {
				processInheritance(type, processedTypes);
			}
		}

		/**
		 * Recursively adds the metrics/sets/rules that the type inherits from
		 * its parent type.
		 * 
		 * @param trans The type to process.
		 * @param processedTransformations The set of types already processed
		 */
		private void processInheritance(MetaModelElement type,
				HashSet<MetaModelElement> processedTypes) {
			// make sure each type is processed only once
			if (processedTypes.contains(type)) {
				return;
			}
			processedTypes.add(type);

			// Recursively process the parent type first
			MetaModelElement parentType = type.getParent();
			if (parentType == null) {
				return;
			}
			processInheritance(parentType, processedTypes);

			// Process metric inheritance for the type
			new InheritanceProcessor<Metric>() {
				@Override
				Metric createCopy(Metric original, MetaModelElement newType) {
					return new Metric(original, newType);
				}
			}.processInheritance(metrics, parentType, type);

			// process set inheritance for the type
			new InheritanceProcessor<Set>() {
				@Override
				Set createCopy(Set original, MetaModelElement newType) {
					return new Set(original, newType);
				}
			}.processInheritance(sets, parentType, type);

			// process rule inheritance for the type
			new InheritanceProcessor<Rule>() {
				@Override
				Rule createCopy(Rule original, MetaModelElement newType) {
					return new Rule(original, newType);
				}
			}.processInheritance(rules, parentType, type);
		}

		/**
		 * Helper class to copy inherited metrics/sets/rules from parent to
		 * child type.
		 * 
		 * @param <T> The type of elements to copy
		 */
		private abstract class InheritanceProcessor<T extends MetricEntry> {
			abstract T createCopy(T original, MetaModelElement newType);

			void processInheritance(
					HashMap<MetaModelElement, LinkedHashMap<String, T>> entries,
					MetaModelElement parentType, MetaModelElement type) {

				Map<String, T> parentEntries = entries.get(parentType);
				Map<String, T> childEntries = entries.get(type);
				for (Map.Entry<String, T> entry : parentEntries.entrySet()) {
					T metricEntry = entry.getValue();
					// if entry is inheritable, and this type does not have an
					// entry under that name, copy the entry for this type
					if (metricEntry.isInheritable()
							&& !childEntries.containsKey(entry.getKey())) {
						T copy = createCopy(metricEntry, type);
						addEntry(childEntries, copy);
					}
				}
			}
		}

		/* Process a metric definition in the metric definition file. */
		private Metric handleMetricDefinition(Attributes attrs)
				throws SAXException {
			String name = getEntryName(attrs);
			MetaModelElement type = getTypeName(attrs, "domain");

			if (getMetric(type, name) != null) {
				throw buildSAXException("Duplicate definition of metric '" + name
						+ "' for elements of type '" + type.getName() + "'.");
			}

			String category = getOptionalAttribute(attrs, "category");

			Metric metric = new Metric(name, type, category);
			metric.setInternal(isAttributeValueTrue(attrs, "internal"));
			setLocation(metric);
			setInheritability(metric, attrs);

			// add the metric to the store
			addEntry(metrics.get(type), metric);
			return metric;
		}

		private String getEntryName(Attributes attrs) throws SAXException {
			currentEntryName = attrs.getValue("name");
			if (currentEntryName == null) {
				throw buildSAXException("No name specified for " + currentEntryType + ".");
			}
			return currentEntryName;
		}

		private MetaModelElement getTypeName(Attributes attrs,
				String domAttribute) throws SAXException {
			String typeName = attrs.getValue(domAttribute);
			if (typeName == null) {
				throw buildSAXException("No domain specified for " + currentEntryType
						+ " '" + currentEntryName + "'.");
			}
			MetaModelElement type = metaModel.getType(typeName);
			if (type == null) {
				throw buildSAXException("Unknown domain '" + typeName + "' for "
						+ currentEntryType + " '" + currentEntryName 
						+ "'.");
			}
			return type;
		}

		private String getOptionalAttribute(Attributes attrs, String attrName) {
			String value = attrs.getValue(attrName);
			if (value == null) {
				return "";
			}
			return value;
		}

		private boolean isAttributeValueTrue(Attributes attrs, String attrName) {
			return "true".equals(attrs.getValue(attrName));
		}

		private void setLocation(MetricEntry entry) {
			if (locator != null) {
				entry.setLocation(locator.getLineNumber());
			}
		}

		private void setInheritability(MetricEntry entry, Attributes attrs) {
			boolean inheritable = defaultInheritability;
			if (attrs.getValue(INHERITABLE) != null) {
				inheritable = isAttributeValueTrue(attrs, INHERITABLE);
			}
			entry.setInheritable(inheritable);
		}

		private <T extends MetricEntry> void addEntry(Map<String, T> map,
				T entry) {
			entry.setId(map.size());
			map.put(entry.getName(), entry);
		}

		/* Process a set definition in the metric definition file. */
		private Set handleSetDefinition(Attributes attrs) throws SAXException {
			String name = getEntryName(attrs);
			MetaModelElement type = getTypeName(attrs, "domain");

			if (getSet(type, name) != null) {
				throw buildSAXException("Duplicate definition of set '" + name
						+ "' for elements of type '" + type.getName() + "'.");
			}

			Set set = new Set(name, type);
			setLocation(set);
			setInheritability(set, attrs);

			set.setMultiSet(isAttributeValueTrue(attrs, "multiset"));

			// assign metric ID to the metric, and add it to the store
			addEntry(sets.get(type), set);
			return set;
		}

		/* Process a relation matrix definition. */
		private Matrix handleMatrixDefinition(Attributes attrs)
				throws SAXException {
			String name = getEntryName(attrs);
			MetaModelElement rowType = getTypeName(attrs, "from_row_type");
			MetaModelElement columnType = getTypeName(attrs, "to_col_type");

			Matrix matrix = new Matrix(name, rowType, columnType);
			setLocation(matrix);

			matrix.setRowCondition(parseExpression(attrs, "row_condition"));
			matrix.setColumnCondition(parseExpression(attrs, "col_condition"));

			// assign ID to the matrix, add it to the store
			matrix.setId(matrices.size());
			matrices.add(matrix);

			return matrix;
		}

		private ExpressionNode parseExpression(Attributes attrs, String attrName)
				throws SAXException {
			ExpressionNode result = null;
			String expr = attrs.getValue(attrName);
			if (expr != null) {
				result = ep.parseExpression(expr);
				if (result == null) {
					throw buildSAXException("Error parsing " + attrName + "='" + expr
							+ "' for " + currentEntryType + " '"
							+ currentEntryName + "':\n" + ep.getErrorInfo());
				}
			}
			return result;
		}

		/* Process a rule definition in the metric definition file. */
		private Rule handleRuleDefinition(Attributes attrs) throws SAXException {
			String name = getEntryName(attrs);

			MetaModelElement type = getTypeName(attrs, "domain");
			if (getRule(type, name) != null) {
				throw buildSAXException("Duplicate definition of rule '" + name
						+ "' for elements of type '" + type.getName() + "'.");
			}

			String category = getOptionalAttribute(attrs, "category");
			String severity = getOptionalAttribute(attrs, "severity");
			boolean enabled = !isAttributeValueTrue(attrs, "disabled");

			ArrayList<String> applicationList = null;
			String appliesTo = attrs.getValue("applies_to");
			if (appliesTo != null) {
				applicationList = new ArrayList<>();
				StringTokenizer st = new StringTokenizer(appliesTo,
						" ,\t\n\r\f");
				while (st.hasMoreTokens()) {
					applicationList.add(st.nextToken());
				}
				if (applicationList.isEmpty()) {
					applicationList = null;
				}
			}

			Rule rule = new Rule(name, type, category, severity,
					applicationList, enabled);
			setLocation(rule);
			setInheritability(rule, attrs);

			// assign ID to the rule, add it to the store
			addEntry(rules.get(type), rule);
			return rule;
		}

		/** Process a literature reference in the metric definition file. */
		private MetricEntry handleReferenceDefinition(Attributes attrs)
				throws SAXException {
			String tag = attrs.getValue("tag");
			if (tag == null) {
				throw buildSAXException("Reference is missing its tag.");
			}
			if (references.containsKey(tag)) {
				throw buildSAXException("Duplicate definition of reference '" + tag + "'.");
			}
			Reference ref = new Reference(tag);
			references.put(tag, ref);
			return ref;
		}

		/* Process a glossary entry in the metric definition file. */
		private Glossary handleGlossaryDefinition(Attributes attrs)
				throws SAXException {
			String name = getEntryName(attrs);
			if (glossary.containsKey(name)) {
				throw buildSAXException("Duplicate definition of glossary term '" + name
						+ "'.");
			}
			Glossary item = new Glossary(name);
			glossary.put(name, item);
			return item;
		}

		/* Process the definition of a word list in the metric definition file. */
		private WordList handleWordListDefinition(Attributes attrs)
				throws SAXException {
			String listName = getEntryName(attrs);
			if (wordLists.containsKey(listName)) {
				throw buildSAXException("Duplicate definition of word list '" + listName
						+ "'.");
			}
			boolean caseSensitive = !isAttributeValueTrue(attrs, "ignorecase");
			WordList list = new WordList(caseSensitive);
			wordLists.put(listName, list);
			return list;
		}

		/* Process an entry in a word list. */
		private void handleWordListEntry(Attributes attrs) throws SAXException {
			if (currentWordList == null) {
				throw buildSAXException("Wordlist entry outside of a word list.");
			}
			String word = attrs.getValue("word");
			if (word == null) {
				throw buildSAXException("Wordlist entry missing 'word' attribute.");
			}
			currentWordList.addWord(word);
		}

		/**
		 * Handles the computation procedure for the current metric, set,
		 * matrix, or rule. Compiles the expressions of the attributes and adds
		 * them to the computation procedure.
		 * 
		 * @param procedureName Name of the computation procedure
		 * @param attrs The attributes for the computation procedure.
		 * @throws SAXException There was an error parsing an attribute of the
		 *         computation procedure.
		 */
		private void handleComputationDefinition(String procedureName,
				Attributes attrs) throws SAXException {
			if (inDescription) {
				throw buildSAXException("Unexpected XML element '" + procedureName
						+ "' in description.");
			}

			if (currentEntry == null) {
				throw buildSAXException("Unexpected XML element '" + procedureName
						+ "' at this point.");
			}

			if (currentEntry.getAttributes() != null) {
				throw buildSAXException("Unexpected XML element '" + procedureName + "' - "
						+ currentEntryType + " '" + currentEntryName
						+ "' already has a calculation procedure.");
			}
			currentEntry.setProcedureName(procedureName);

			ProcedureAttributes attributes = new ProcedureAttributes();
			int attnum = attrs.getLength();
			for (int i = 0; i < attnum; i++) {
				// Compile expression and store the operator tree.
				String attrName = attrs.getQName(i);
				ExpressionNode root = parseExpression(attrs, attrName);
				// check for old "_exp" suffix from versions 1.2 and earlier,
				// remove it from the attribute name to maintain backwards
				// compatibility
				if (attrName.endsWith("_exp")) {
					attrName = attrName.substring(0, attrName.length() - 4);
				}
				attributes.setExpressionNode(attrName, root);
			}
			currentEntry.setAttributes(attributes);
		}

		/**
		 * Handles the definition of a custom defined metric, set, or rule
		 * procedure. Makes sure the procedure class can be loaded and
		 * instantiated.
		 * 
		 * @param store The applicable procedure store
		 * @param element the name of the XML element defining the procedure
		 * @param attrs the XML attributes of the element
		 * @return Name of the procedure
		 * @throws SAXException Missing attributes or procedure class could not
		 *         be loaded
		 */
		private String handleProcedureDefinition(ProcedureCache<?> store,
				String element, Attributes attrs) throws SAXException {
			String procedureName = attrs.getValue("name");
			if (procedureName == null) {
				throw buildSAXException("The " + element
						+ " definition requires a 'name' attribute.");
			}

			if (store.hasProcedure(procedureName)) {
				throw buildSAXException("The " + element + " '" + procedureName
						+ "' is already defined.");
			}

			String className = attrs.getValue("class");
			if (className == null) {
				throw buildSAXException("No class defined for " + element + " definition '"
						+ procedureName + "'.");
			}

			try {
				store.addProcedureClass(procedureName, className);
			} catch (SDMetricsException ex) {
				throw buildSAXException(ex.getMessage());
			}
			return procedureName;
		}
	}
}
