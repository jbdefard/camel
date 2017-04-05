/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.jms.issues;

import org.apache.camel.FailedToCreateRouteException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

/**
 * See https://issues.apache.org/jira/browse/CAMEL-11109
 */
public class JmsExclusiveConsumerTest {

    /**
     * @throws Exception as long as the issue is present the route creation will fail
     */
    @Test(expected=FailedToCreateRouteException.class)
    public void testExclusiveConsumer() throws Exception {
        DefaultCamelContext context = new DefaultCamelContext();
        context.addComponent("activemq", JmsComponent.jmsComponent());
        context.addRoutes(new RouteBuilder() {
            public void configure() throws Exception {
                from("activemq:queue:in?destination.consumer.exclusive=true").to("mock:result");
            }
        });
        context.start();
    }

}
