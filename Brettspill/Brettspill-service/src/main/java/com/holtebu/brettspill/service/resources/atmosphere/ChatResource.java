/*
 * Copyright 2013 Jeanfrancois Arcand
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.holtebu.brettspill.service.resources.atmosphere;

//import org.atmosphere.annotation.Broadcast;
//import org.atmosphere.annotation.Suspend;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
///**
// * Created with IntelliJ IDEA.
// * User: coder
// * Date: 4/8/13
// * Time: 11:20 PM
// * To change this template use File | Settings | File Templates.
// */
//@Path("/chat")
//public class ChatResource {
//    @Suspend(contentType = MediaType.APPLICATION_JSON)
//    @GET
//    public String suspend() {
//        return "";
//    }
//
//    @Broadcast(writeEntity = false)
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response broadcast(Message message) {
//        return new Response(message.author, message.message);
//    }
//}


//import org.atmosphere.annotation.Broadcast;
//import org.atmosphere.annotation.Suspend;
////import org.atmosphere.config.service.AtmosphereService;
//import org.atmosphere.cpr.AtmosphereResourceEvent;
//import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
////import org.atmosphere.jersey.JerseyBroadcaster;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;



///**
// * Simple chat resource demonstrating the power of Atmosphere. This resource supports transport like WebSocket, Streaming, JSONP and Long-Polling.
// *
// * @author Jeanfrancois Arcand
// */
//@Path("/chat")
//public class ChatResource {
//
//    /**
//     * Suspend the response without writing anything back to the client.
//     *
//     * @return a white space
//     */
//	@Suspend(contentType = MediaType.APPLICATION_JSON)//@Suspend(contentType = "application/json") //, listeners = {OnDisconnect.class})
//    @GET
//    public String suspend() {
//
//        return "";
//    }
//
//    /**
//     * Broadcast the received message object to all suspended response. Do not write back the message to the calling connection.
//     *
//     * @param message a {@link Message}
//     * @return a {@link Response}
//     */
//    @Broadcast(writeEntity = false)
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response broadcast(Message message) {
//        return new Response(message.author, message.message);
//    }
//
//    public static final class OnDisconnect extends AtmosphereResourceEventListenerAdapter {
//        private final Logger logger = LoggerFactory.getLogger(ChatResource.class);
//
//        /**
//         * {@inheritDoc}
//         */
//        @Override
//        public void onDisconnect(AtmosphereResourceEvent event) {
//            if (event.isCancelled()) {
//                logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
//            } else if (event.isClosedByClient()) {
//                logger.info("Browser {} closed the connection", event.getResource().uuid());
//            }
//        }
//    }
//
//}