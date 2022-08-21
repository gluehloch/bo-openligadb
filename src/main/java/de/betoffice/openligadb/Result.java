/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2022 by Andre Winkler. All
 * rights reserved.
 * ============================================================================
 * GNU GENERAL PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND
 * MODIFICATION
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package de.betoffice.openligadb;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * {@link "https://8thlight.com/blog/brian-gerstle/2019/01/22/fnl-exceptions-in-java.html"}
 *
 * @param <V> the result type 
 * @param <E> an exception
 */
public class Result<V, E extends Throwable> {

    private final V value;
    private final E error;

    private Result(V value, E error) {
        this.value = value;
        this.error = error;
    }

    private static <V, E extends Throwable> Result<V, E> failure(E error) {
        return new Result<>(null, Objects.requireNonNull(error));
    }

    private static <V, E extends Throwable> Result<V, E> success(V value) {
        return new Result<>(Objects.requireNonNull(value), null);
    }

    // ----

    public static <V, E extends Throwable> Result<V, E> attempt(CheckedSupplier<? extends V, ? extends E> p) {
        try {
            return Result.success(p.get());
        } catch (Throwable e) {
            @SuppressWarnings("unchecked")
            E err = (E) e;
            return Result.failure(err);
        }
    }

    @FunctionalInterface
    public interface CheckedSupplier<V, E extends Throwable> {
        V get() throws E;
    }

    public <T> Result<T, E> map(Function<? super V, ? extends T> mapper) {
        return Optional.ofNullable(error)
                .map(e -> Result.<T, E>failure(e))
                .orElseGet(() -> Result.success(mapper.apply(value)));
    }

    public V orElseThrow() throws E {
        return Optional.ofNullable(value).orElseThrow(() -> error);
    }

}
