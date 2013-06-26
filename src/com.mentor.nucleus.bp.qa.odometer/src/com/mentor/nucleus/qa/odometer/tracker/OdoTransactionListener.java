package com.mentor.nucleus.qa.odometer.tracker;

import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.qa.odometer.timer.OdoTimer;
import com.mentor.nucleus.qa.odometer.views.OdoView;

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
