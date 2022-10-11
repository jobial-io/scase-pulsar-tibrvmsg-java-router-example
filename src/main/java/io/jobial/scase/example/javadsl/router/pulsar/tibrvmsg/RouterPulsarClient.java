/*
 * Copyright (c) 2020 Jobial OÜ. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with
 * the License. A copy of the License is located at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package io.jobial.scase.example.javadsl.router.pulsar.tibrvmsg;

import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvMsg;

import java.util.concurrent.ExecutionException;

import static io.jobial.scase.pulsar.javadsl.PulsarServiceConfiguration.source;

public class RouterPulsarClient implements RouterServicePulsarConfig {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TibrvException {
        var client = serviceConfig.client();
        var request = new TibrvMsg();
        request.add("target_topic", "mytopic");
        client.send(request).get();

        // Receiving the response sent out by the server:
        source("mytopic", tibrvMarshalling).client().receive().whenComplete((response, error) ->
                System.out.println(response)
        ).get();
    }
}