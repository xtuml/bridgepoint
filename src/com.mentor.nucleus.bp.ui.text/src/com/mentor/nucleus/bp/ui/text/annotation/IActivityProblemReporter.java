package com.mentor.nucleus.bp.ui.text.annotation;
//====================================================================
//
// File:      $RCSfile: IActivityProblemReporter.java,v $
// Version:   $Revision: 1.9 $
// Modified:  $Date: 2013/01/10 23:20:50 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
public interface IActivityProblemReporter
{

  /**
   * Notification of a Action problem.
   *
   * @param problem IProblem - The discovered Action problem.
   */
  void acceptProblem(ActivityProblem problem);

  /**
   * Notification sent before starting the problem detection process.
   * Typically, this would tell a problem collector to clear previously recorded problems.
   */
  void beginReporting();

  /**
   * Notification sent after having completed problem detection process.
   * Typically, this would tell a problem collector that no more problems should be expected in this
   * iteration.
   */
  void endReporting();

}

