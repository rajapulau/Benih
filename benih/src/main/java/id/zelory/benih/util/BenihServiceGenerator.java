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

package id.zelory.benih.util;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;

/**
 * Created on : December 09, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class BenihServiceGenerator {
    public static <S> S createService(Class<S> serviceClass, String baseUrl, RestAdapter.LogLevel logLevel) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setLogLevel(logLevel)
                .setConverter(new GsonConverter(Bson.pluck().getParser()))
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError cause) {
                        if (cause.getKind().equals(RetrofitError.Kind.HTTP)) {
                            String json = new String(((TypedByteArray) cause.getResponse().getBody()).getBytes());
                            try {
                                JSONObject object = new JSONObject(json);
                                return new Throwable(object.getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (cause.getKind().equals(RetrofitError.Kind.NETWORK)) {
                            return new Throwable("Please check your internet connection!");
                        }
                        return cause;
                    }
                })
                .setEndpoint(baseUrl);

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}
