//========================================================================
//
// File:      $RCSfile: BasePlugin.java,v $
// Version:   $Revision: 1.10 $
// Modified:  $Date: 2012/01/23 21:28:08 $
//
// (c) Copyright 2004-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// This document contains information proprietary and confidential to
// Mentor Graphics Corp., and is not for external distribution.
//========================================================================

package com.mentor.nucleus.bp.ui.preference;

import java.util.HashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author babar_ali
 */
public abstract class BasePlugin extends AbstractUIPlugin {
    HashMap modelRegistry = new HashMap();
    ISharedTextColors sharedColors = new SharedColorRepository();

    public BasePlugin() {
        super();
    }

    public ISharedTextColors getSharedColors() {
        return sharedColors;
    }


    /* (non-Javadoc)
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception
    {
        sharedColors.dispose();
        super.stop(context);
    }

    /* (non-Javadoc)
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception
    {
        super.start(context);
        initPreferencesModels();
    }

    protected void registerPreferencesStore(IPreferenceModelStore store, boolean loadModelonStartup) throws CoreException {
        ModelInfo modelInfo = new ModelInfo(store);
        if (loadModelonStartup)
            modelInfo.init();
        modelRegistry.put(modelInfo.getModelClass().getName(), modelInfo);
    }

    public IPreferenceModel getPreferenceModel(String modelClassName) {
        ModelInfo modelInfo = (ModelInfo) modelRegistry.get(modelClassName);

        return modelInfo.getModel();
    }

    protected abstract void initPreferencesModels() throws CoreException;

    class ModelInfo {
        IPreferenceModelStore store;
        IPreferenceModel model;

        boolean loaded = false;

        public ModelInfo(IPreferenceModelStore aStore) throws CoreException {
            store = aStore;
        }

        public void init() throws CoreException {
            model = store.loadModel(getPreferenceStore(), BasePlugin.this, null);
            loaded = true;
        }

        public boolean isLoaded() {
            return loaded;
        }

        public IPreferenceModel getModel() {
            if (!loaded) {
                try {
                    init();
                } catch (CoreException e) {
                    throw new Error(e);
                }
            }
            return model;
        }

        public Class getModelClass() {
            return store.getModelClass();
        }

    }
}
