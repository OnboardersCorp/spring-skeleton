package com.tastygear.core.config

import feign.codec.Decoder
import feign.jackson.JacksonDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {

    @Bean
    fun feignDecoder(): Decoder {
        return JacksonDecoder()
    }
}