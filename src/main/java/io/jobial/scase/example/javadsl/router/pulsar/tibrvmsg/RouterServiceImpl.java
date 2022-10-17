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

import com.tibco.tibrv.TibrvMsg;
import io.jobial.scase.core.javadsl.MessageHandler;

import static io.jobial.scase.pulsar.javadsl.PulsarServiceConfiguration.destination;
import static java.util.concurrent.CompletableFuture.runAsync;

interface RouterServiceImpl extends RouterServicePulsarConfig {

    MessageHandler<TibrvMsg> messageHandler = (request, context) -> runAsync(() -> {
        try {
            var targetTopic = request.get("target_topic").toString();
            var response = new TibrvMsg();
            response.add("greeting", "hello on " + targetTopic);
            
            // Creating client for response, could be cached...
            var client = destination(targetTopic, tibrvMarshalling).client().get();
            System.out.println("sending to " + targetTopic + " message: " + response);
            client.send(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });
}
