package com.sdmetrics.metrics;

/** Cache for metric calculation procedures. */
class MetricProcedureCache extends ProcedureCache<MetricProcedure> {

	/**
	 * Creates a new cache and registers the standard procedures that come with
	 * SDMetrics.
	 */
	MetricProcedureCache() {
		super("metric procedure");
		addProcedureClass("projection", MetricProcedureProjection.class);
		addProcedureClass("compoundmetric", MetricProcedureCompound.class);
		addProcedureClass("nesting", MetricProcedureNesting.class);
		addProcedureClass("attributevalue", MetricProcedureAttributeValue.class);
		addProcedureClass("count", MetricProcedureCount.class);
		addProcedureClass("subelements", MetricProcedureSubelements.class);
		addProcedureClass("signature", MetricProcedureSignature.class);
		addProcedureClass("connectedcomponents",
				MetricProcedureConnectedComponents.class);
		addProcedureClass("compare", MetricProcedureCompare.class);
		addProcedureClass("filtervalue", MetricProcedureFilterValue.class);
		addProcedureClass("valuesetcount",
				MetricProcedureValuesetOperation.class);
		addProcedureClass("setoperation", MetricProcedureSetOperation.class);
		addProcedureClass("substring", MetricProcedureSubString.class);
	}

	@Override
	protected Class<? extends MetricProcedure> loadClass(String className)
			throws ClassNotFoundException {
		return Class.forName(className).asSubclass(MetricProcedure.class);
	}
}