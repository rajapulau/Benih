/*
 * Copyright (c) 2015 Zetra.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package id.zelory.benih.network;

import id.zelory.benih.util.Bson;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by zetbaitsu on 7/9/15.
 */
public class ServiceGenerator
{
    public static <S> S createService(Class<S> serviceClass, String baseUrl)
    {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setConverter(new GsonConverter(Bson.pluck().getParser()))
                .setEndpoint(baseUrl);

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}