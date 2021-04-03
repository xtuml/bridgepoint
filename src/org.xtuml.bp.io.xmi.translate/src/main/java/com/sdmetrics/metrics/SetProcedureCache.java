package com.sdmetrics.metrics;

/** Cache for set calculation procedures. */
class SetProcedureCache extends ProcedureCache<SetProcedure> {

	/**
	 * Creates a new cache and registers the standard procedures that come with
	 * SDMetrics.
	 */
	SetProcedureCache() {
		super("set procedure");
		addProcedureClass("projection", SetProcedureProjection.class);
		addProcedureClass("compare", SetProcedureCompare.class);
		addProcedureClass("subelements", SetProcedureSubelements.class);
		addProcedureClass("compoundset", SetProcedureCompound.class);
	}

	@Override
	protected Class<? extends SetProcedure> loadClass(String className)
			throws ClassNotFoundException {
		return Class.forName(className).asSubclass(SetProcedure.class);
	}
}