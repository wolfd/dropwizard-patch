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

package io.progix.dropwizard.patch.operations.contextual.json;

import com.fasterxml.jackson.databind.JsonNode;
import io.progix.dropwizard.patch.JsonPatchValue;
import io.progix.dropwizard.patch.JsonPath;
import io.progix.dropwizard.patch.operations.contextual.ContextualTestOperation;
import io.progix.jackson.JsonPatchOperation;
import io.progix.jackson.JsonPatchOperationType;
import io.progix.jackson.operations.TestOperation;

public class JsonTestOperation implements ContextualTestOperation<JsonNode> {

    @Override
    public boolean test(JsonNode context, JsonPath path, JsonPatchValue value) {
        JsonPatchOperation instruction = new JsonPatchOperation(JsonPatchOperationType.TEST,
                path.getJsonPointer(), value.getNode());

        TestOperation.apply(instruction, context);

        return true;
    }
}