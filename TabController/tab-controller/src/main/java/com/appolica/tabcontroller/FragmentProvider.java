package com.appolica.tabcontroller;

import android.support.v4.app.Fragment;

/**
 * Used by {@link TabController}. Implementation of this interface should provide a tag and
 * an instance of the fragment that will be shown/hidden by the controller.
 */
public interface FragmentProvider {

    /**
     * Provide the fragment's tag.
     * @return The fragment's tag.
     */
    public String getTag();

    /**
     * Obtain fragment's instance.
     * @return The fragment's instance.
     */
    public Fragment getInstance();
}
