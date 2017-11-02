package com.emis.job2017;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.CallSuper;

/**
 * Basic implementation of an AsyncTask loader
 * Created by j.varontamayo on 21/01/2016.
 */
public abstract class BaseAsyncLoader<T> extends AsyncTaskLoader<T> {

    private T mData;

    public BaseAsyncLoader(Context c){
        super(c);
    }

    /**
     * Called when some data can be released to free resources because it will no longer be used
     * @param dataTobeReleased the data that can be released
     */
    protected abstract void releaseResources(T dataTobeReleased);

    /**
     * Called during while the loader is starting up. At this moment a necessary observer can be
     * registered.
     * <br/>
     * NOTE: Register ONLY if it has not been registered before!!!
     */
    protected abstract void registerDataObserver();

    /**
     * Called when this loader will no longer be valid. The registered observer can now be unregistered.
     */
    protected abstract void unregisterDataObserver();

    /**
     * @return getter method for retrieving the current data item array
     */
    public T getDataArray(){
        return mData;
    }

    /********************************************************/
    /** (2) Deliver the results to the registered listener **/
    /********************************************************/
    @CallSuper
    @Override
    public void deliverResult(T data) {
        if (isReset()) {
            // The Loader has been reset; ignore the result and invalidate the data.
            releaseResources(data);
            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        T oldData = mData;
        mData = data;

        if (isStarted()) {
            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult(data);
        }

        // Invalidate the old data as we don't need it any more.
        if (oldData != null && oldData != data) {
            releaseResources(oldData);
        }
    }

    /*********************************************************/
    /** (3) Implement the Loader’s state-dependent behavior **/
    /*********************************************************/
    @CallSuper
    @Override
    protected void onStartLoading() {
        if (mData != null) {
            // Deliver any previously loaded data immediately.
            deliverResult(mData);
        }

        // Begin monitoring the underlying data source.
        registerDataObserver();

        if (takeContentChanged() || mData == null) {
            // When the observer detects a change, it should call onContentChanged()
            // on the Loader, which will cause the next call to takeContentChanged()
            // to return true. If this is ever the case (or if the current data is
            // null), we force a new load.
            forceLoad();
        }
    }

    @CallSuper
    @Override
    protected void onStopLoading() {
        // The Loader is in a stopped state, so we should attempt to cancel the
        // current load (if there is one).
        cancelLoad();

        // Note that we leave the observer as is. Loaders in a stopped state
        // should still monitor the data source for changes so that the Loader
        // will know to force a new load if it is ever started again.
    }

    @CallSuper
    @Override
    protected void onReset() {
        // Ensure the loader has been stopped.
        onStopLoading();

        // At this point we can release the resources associated with 'mData'.
        if (mData != null) {
            releaseResources(mData);
            mData = null;
        }

        // The Loader is being reset, so we should stop monitoring for changes.
        unregisterDataObserver();
    }

    @CallSuper
    @Override
    public void onCanceled(T data) {
        // Attempt to cancel the current asynchronous load.
        super.onCanceled(data);

        // The load has been canceled, so we should release the resources
        // associated with 'data'.
        releaseResources(data);
    }

}
