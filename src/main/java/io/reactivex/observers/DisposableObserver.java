/**
 * Copyright (c) 2016-present, RxJava Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package io.reactivex.observers;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.*;

/**
 * An abstract {@link Observer} that allows asynchronous cancellation by implementing Disposable.
 *
 * @param <T> the received value type
 */
public abstract class DisposableObserver<T> implements Observer<T>, Disposable {
    final AtomicReference<Disposable> s = new AtomicReference<Disposable>();

    @Override
    public final void onSubscribe(Disposable s) {
        if (DisposableHelper.setOnce(this.s, s)) {
            onStart();
        }
    }

    /**
     * Called once the single upstream Disposable is set via onSubscribe.
     */
    protected void onStart() {
    }

    @Override
    public final boolean isDisposed() {
        return s.get() == DisposableHelper.DISPOSED;
    }

    @Override
    public final void dispose() {
        DisposableHelper.dispose(s);
    }
}
