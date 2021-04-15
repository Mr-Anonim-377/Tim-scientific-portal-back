//package com.tim.scientific.portal.back.config;
//
//import io.micrometer.core.instrument.ImmutableTag;
//import io.micrometer.core.instrument.Tag;
//import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsContributor;
//import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.HandlerMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Configuration
//public class MetricConfig {
//
//    @Bean
//    public WebMvcTagsProvider webMvcTagsProvider() {
//        return new WebMvcTagsProvider() {
//            @Override
//            public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable exception) {
//                return ((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
//                        .entrySet()
//                        .stream()
//                        .map(entry -> new ImmutableTag(entry.getKey(), entry.getValue()))
//                        .collect(Collectors.toList());
//            }
//
//            @Override
//            public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
//                return new ArrayList<>();
//            }
//        };
//    }
////    @Bean
////    public WebMvcTagsContributor webMvcTagsContributor() {
////        return new WebMvcTagsContributor() {
////            @Override
////            public Iterable<Tag> getTags(
////                    HttpServletRequest request, HttpServletResponse response, Object handler, Throwable exception
////            ) {
////                Map<String, String> pathVariables = ((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
////                return pathVariables == null
////                        ? null
////                        : pathVariables
////                        .entrySet()
////                        .stream()
////                        .map(entry -> Tag.of(entry.getKey(), entry.getValue()))
////                        .collect(Collectors.toList());
////            }
////
////            @Override
////            public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
////                return null;
////            }
////        };
////    }
//
//}
