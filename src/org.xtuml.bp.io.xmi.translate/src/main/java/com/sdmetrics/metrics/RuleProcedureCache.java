package com.sdmetrics.metrics;

/** Cache for rule calculation procedures. */
class RuleProcedureCache extends ProcedureCache<RuleProcedure> {

	/**
	 * Creates a new cache and registers the standard procedures that come with
	 * SDMetrics.
	 */
	RuleProcedureCache() {
		super("rule procedure");
		addProcedureClass("violation", RuleProcedureViolation.class);
		addProcedureClass("cycle", RuleProcedureCycle.class);
		addProcedureClass("projection", RuleProcedureSet.class);
		addProcedureClass("compoundset", RuleProcedureSet.class);
		addProcedureClass("compare", RuleProcedureSet.class);
		addProcedureClass("valueset", RuleProcedureSet.class);
	}

	@Override
	protected Class<? extends RuleProcedure> loadClass(String className)
			throws ClassNotFoundException {
		return Class.forName(className).asSubclass(RuleProcedure.class);
	}
}