package com.linkedin.com.user_profile.client;


import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

//@FeignClient(name = "uploader-service", path = "/uploads")
//public interface UploaderClient {
//
////     Define methods to interact with the Uploader service here
////    For example:
//    @PostMapping(value = "/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public String uploadImage(@RequestParam("image") MultipartFile file);
//
//
//}

@FeignClient(name = "uploader-service", configuration = UploaderClient.MultipartSupportConfig.class)
public interface UploaderClient {

    @PostMapping(value = "/uploads/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadImage(@RequestPart("file") MultipartFile file);

    class MultipartSupportConfig {
        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder(new SpringEncoder(new ObjectFactory<>() {
                @Override
                public HttpMessageConverters getObject() {
                    return new HttpMessageConverters(new RestTemplate().getMessageConverters());
                }
            }));
        }
    }
}