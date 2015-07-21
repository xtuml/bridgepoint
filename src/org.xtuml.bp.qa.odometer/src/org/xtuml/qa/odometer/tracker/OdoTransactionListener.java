package org.xtuml.qa.odometer.tracker;

import org.eclipse.swt.widgets.Display;

import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.qa.odometer.timer.OdoTimer;
import org.xtuml.qa.odometer.views.OdoView;

public class OdoTransactionListener implements ITransactionListener {
	@Override
	public void transactionCancelled(final Transaction transaction) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				OdoTimer.kick(transaction);
			}
		});
		
	}

	@Override
	public void transactionEnded(final Transaction transaction) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				OdoTimer.kick(transaction);
			}
		});
		
	}

	@Override
	public void transactionStarted(final Transaction transaction) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				OdoTimer.kick(transaction);
			}
		});
		
	}

    static OdoTransactionListener self = null;
	public static ITransactionListener getInstance() {
		if (self == null) {
			self = new OdoTransactionListener();
		}
		return self;
	}

}
