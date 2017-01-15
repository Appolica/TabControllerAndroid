package com.appolica.tabcontroller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class TabController {

    private final FragmentManager fragmentManager;
    private final int containerId;

    private OnFragmentChangeListener changeListener;


    public TabController(FragmentManager fragmentManager, int containerId) {
        this.fragmentManager = fragmentManager;
        FragmentManager.enableDebugLogging(false);
        this.containerId = containerId;
    }

    public void switchTo(FragmentProvider fragmentType) {

        List<Notifier> notifiers = new ArrayList<>();


        inTransaction(transaction -> {

            hideVisibleFragment(transaction);

            final Fragment fragmentToShow = fragmentManager.findFragmentByTag(fragmentType.getTag());
//
            if (fragmentToShow != null) {
                if (!isVisible(fragmentToShow)) {
                    //Fragment is active but not visible
                    showFragment(fragmentToShow, transaction);

                    notifiers.add(listener -> listener.onFragmentShown(fragmentType, fragmentToShow));

                } else {
                    //Fragment is visible on the screen
                    notifiers.add(listener -> listener.onFragmentAlreadyVisible(fragmentType, fragmentToShow));
                }
            } else {
                //Fragment does not exist
                Fragment addedFragment = addToFragmentManager(fragmentType, transaction);

                notifiers.add(listener -> listener.onFragmentCreated(fragmentType, addedFragment));
                notifiers.add(listener -> listener.onFragmentShown(fragmentType, addedFragment));
            }

            return notifiers;

        });


    }

    private void hide(FragmentProvider currentFragment) {
        final Fragment visibleFragment = fragmentManager.findFragmentByTag(currentFragment.getTag());

        if (visibleFragment != null) {
            fragmentManager.beginTransaction()
                    .hide(visibleFragment)
                    .commitNow();

        }
    }

    private void showFragment(Fragment fragment, FragmentTransaction transaction) {
        transaction.show(fragment);
    }

    private Fragment addToFragmentManager(FragmentProvider fragmentType, FragmentTransaction transaction) {
        Fragment fragment = fragmentType.getInstance();
        transaction.add(containerId, fragment, fragmentType.getTag());

        return fragment;
    }

    private void hideVisibleFragment(FragmentTransaction fragmentTransaction) {
        final Fragment visibleFragment = getVisibleFragment();

        if (visibleFragment != null) {
            fragmentTransaction.hide(visibleFragment);
        }
    }

    private Fragment getVisibleFragment() {
        final List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {

                if (fragment != null && isVisible(fragment)) {
                    return fragment;
                }
            }
        }

        return null;
    }

    private boolean isVisible(Fragment fragment) {
        return fragment.isVisible();
    }

    public void restore(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            List<Fragment> fragments = fragmentManager.getFragments();

            inTransaction(transaction -> {

                if (fragments != null) {
                    for (Fragment fragment : fragments) {

                        boolean visible = savedInstanceState.getBoolean(fragment.getTag());

                        if (visible) {
                            transaction.show(fragment);
                        } else {
                            transaction.hide(fragment);
                        }
                    }

                }

                return new ArrayList<>();
            });

        }
    }

    public void save(Bundle savedInstanceState) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {

            for (Fragment fragment : fragments) {

                boolean visible = !fragment.isHidden();
                savedInstanceState.putBoolean(fragment.getTag(), visible);

            }
        }
    }

    private void inTransaction(@NonNull TransactionBody body) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        List<Notifier> notifiers = body.runInTransaction(transaction);

        transaction.commitNow();

        if (changeListener != null)
            for (Notifier notifier : notifiers) {
                notifier.notifyListener(changeListener);
            }


    }

    private static interface TransactionBody {
        public
        @NonNull
        List<Notifier> runInTransaction(FragmentTransaction fragmentTransaction);
    }

    private static interface Notifier {
        public void notifyListener(@NonNull OnFragmentChangeListener onFragmentChangeListener);
    }


}
