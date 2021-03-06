/*
 * Copyright 2014 Tariq Bugrara
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.progix.dropwizard.patch.implicit;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;
import io.dropwizard.jackson.Jackson;
import io.progix.dropwizard.patch.explicit.PatchRequest;

import java.io.IOException;

public class PatchedProvider implements InjectableProvider<Patched, Parameter> {

    public static class PatchedInjectable<T> extends AbstractHttpContextInjectable<T> {

        private final Class<T> classType;

        public PatchedInjectable(Class<T> classType) {
            this.classType = classType;
        }

        @Override
        public T getValue(HttpContext c) {
            String patchRequest = c.getRequest().getEntity(String.class);
            try {
                PatchRequest pr = Jackson.newObjectMapper().readValue(patchRequest, PatchRequest.class);
            } catch (IOException e) {
                //TODO use useful exception
                throw new RuntimeException("failed", e);
            }

            return null;
        }
    }
    @Override
    public ComponentScope getScope() {
        return ComponentScope.PerRequest;
    }

    @Override
    public Injectable getInjectable(ComponentContext ic, Patched patched, Parameter parameter) {
        return new PatchedInjectable<>(parameter.getParameterClass());
    }
}
