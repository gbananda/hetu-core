/*
 * Copyright (C) 2018-2020. Huawei Technologies Co., Ltd. All rights reserved.
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
package io.hetu.core.statestore.hazelcast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import io.prestosql.spi.metastore.model.DatabaseEntity;

import java.io.IOException;

import static io.hetu.core.statestore.hazelcast.HazelCastSerializationConstants.CONSTANT_TYPE_DATABASEENTITY;

public class HazelcastDatabaseEntitySerializer
        implements StreamSerializer<DatabaseEntity>
{
    private ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module());

    @Override
    public void write(ObjectDataOutput objectDataOutput, DatabaseEntity databaseEntity)
            throws IOException
    {
        objectDataOutput.writeByteArray(mapper.writeValueAsString(databaseEntity).getBytes());
    }

    @Override
    public DatabaseEntity read(ObjectDataInput objectDataInput)
            throws IOException
    {
        return mapper.readValue(objectDataInput.readByteArray(), DatabaseEntity.class);
    }

    @Override
    public int getTypeId()
    {
        return CONSTANT_TYPE_DATABASEENTITY;
    }
}
